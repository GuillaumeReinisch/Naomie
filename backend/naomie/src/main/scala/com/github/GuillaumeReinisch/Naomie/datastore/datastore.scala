package com.github.GuillaumeReinisch.Naomie.datastore

import com.github.GuillaumeReinisch.Naomie.models.{Dataset, Graphic, Scenario}
import com.google.cloud.datastore.{Datastore, DatastoreOptions, Entity, Key}
import org.json4s._
import org.json4s.jackson.JsonMethods.parse
import org.slf4j.LoggerFactory
/**
  * Created by lcts on 19/02/17.
  */
object DatastoreService {

  val projectId   = "malcom-gedi"

  val logger    =  LoggerFactory.getLogger(getClass)

  def datastore(namespace : String): Datastore = {
    DatastoreOptions.newBuilder().setProjectId(projectId).setNamespace(namespace).build().getService()
  }

  def createKey(name : String, kind : String, namespace : String) : Key = {

    logger.info("create key: "+ namespace +"::"+kind + " "+name)

    val keyFactory  = datastore(namespace).newKeyFactory().setKind(kind);
    keyFactory.newKey(name);
  }

  def saveGraphic(graphic : Graphic, key : Key) : Entity = {

    val entity      = Entity.newBuilder(key)
      .set("name", graphic.name)
      .set("axis", graphic.axis.sorted.reduceLeft{ (acc , elem) => acc+" "+elem });

    graphic.metadata.foldLeft(entity){ case (e,(name,value) ) => e.set(name,value) };

    datastore(key.getNamespace).put(entity.build());
  }

  def saveScenario(scenario : Scenario, key : Key) : Entity = {

    val entity      = Entity.newBuilder(key)
      .set("name", scenario.name);
    scenario.metadata.foldLeft(entity){ case (e,(name,value) ) => e.set(name,value) };
    datastore(key.getNamespace).put(entity.build());
  }

  def saveDataset(dataset : Dataset, key : Key) : Entity = {

    logger.info("saveDataset " + key )

    val entity      = Entity.newBuilder(key)
      .set("name", dataset.name)
      .set("parent", dataset.scenarioUid)
      .set("vheaders", dataset.vheaders.reduceLeft{ (acc , elem ) => acc+" "+elem })
      .set("hheaders", dataset.columns.foldLeft(""){ case (acc , (name, _) ) => acc+" "+name })

    dataset.columns.foldLeft(entity){
      case (entity,(name, values) ) => entity.set(name, values.foldLeft(""){ (acc , elem ) => acc + " " + elem.toString })
    };

    dataset.metadata.foldLeft(entity){ case (e,(name,value) ) => e.set(name,value) };
    val entityBuild = entity.build()
    logger.info(entityBuild.toString)
    datastore(key.getNamespace).put(entityBuild);
  }


  def getScenario(key : Key) : Option[Scenario] = {

    logger.info("getScenario "+ key.getName )

    val transaction = datastore("data").newTransaction();
    val entity      = transaction.get(key);
    if(entity == null){
      logger.error("no scenario found : ")
      None
    }
    logger.info("scenario found :"+ entity.toString )
    implicit val formats = DefaultFormats
    var scenario = parse(entity.toString).extract[Scenario]
    logger.info(scenario.toString )
    Option(scenario)
  }

}
