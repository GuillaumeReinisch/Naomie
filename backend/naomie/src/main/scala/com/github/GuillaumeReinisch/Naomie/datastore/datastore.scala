package com.github.GuillaumeReinisch.Naomie.datastore

import com.github.GuillaumeReinisch.Naomie.models._
import com.google.cloud.datastore.Query._
import com.google.cloud.datastore.StructuredQuery.{Filter, PropertyFilter}
import com.google.cloud.datastore._
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

  def saveProject(project : Project, key : Key) : Entity = {

    val entity      = Entity.newBuilder(key)
      .set("name", project.name)
      .set("userName", project.userName)
      .set("creationDate", project.creationDate)
      .set("summary", project.summary)
      .set("scenarios", project.scenarios.foldLeft(""){ (acc , elem) => acc+" "+elem })
      .set("graphics", project.graphics.foldLeft(""){ (acc , elem) => acc+" "+elem });

    datastore(key.getNamespace).put(entity.build());
  }

  def saveCollection(collection: GraphicCollection, key : Key) : Entity ={

    val entity      = Entity.newBuilder(key)
      .set("name", collection.name)
      .set("userName", collection.userName)
      .set("creationDate", collection.creationDate)
      .set("description", collection.description)
      .set("graphics", collection.graphics.foldLeft(""){ (acc , elem) => acc+" "+elem });

    datastore(key.getNamespace).put(entity.build());
  }

  def saveParameter(parameter: Parameter, collection: Option[String], key : Key) : Entity ={

    val entity      = Entity.newBuilder(key)
      .set("formula", parameter.formula)
      .set("userName", parameter.userName)
      .set("creationDate", parameter.creationDate)
      .set("variables", parameter.variables.foldLeft(""){ case (acc , (key,value) ) => acc + " " +key+","+value });

    val build = datastore(key.getNamespace).put(entity.build());
    logger.info("build parameter = " + build.toString)

    collection match {
      case Some(collectionName) =>{
        logger.info("add link to collection "+collectionName)

        val keySummary = datastore("parameters").newKeyFactory()
          .addAncestors(PathElement.of("Collection", collectionName))
          .setKind("ParameterSummary")
          .newKey(parameter.formula);

        val entitySummary  = Entity.newBuilder(keySummary)
          .set("formula", parameter.formula)
          .set("variables", parameter.variables.foldLeft(""){ case (acc , (key,value) ) => acc + " " +key+","+value });

        datastore(keySummary.getNamespace).put(entitySummary.build());
      }
      case None => {logger.info("no link added to collection") }
    }
    build;
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
    val scenario = Scenario(entity)
    logger.info(scenario.toString )
    Option(scenario)
  }

  def query(namespace : String, kind : String ):  QueryResults[Entity] = {

    logger.info("query "+ namespace + "::" + kind )

    val query   = newEntityQueryBuilder().setKind(kind).build();
    // we need this to do  datastore(namespace).run() ... because of ambiguous 'run' method
    val ambig = datastore(namespace)
    val methods = ambig.getClass.getMethods.filter(_.getName == "run")
    var wanted = methods.find(_.getParameterTypes.length == 1).get
    wanted.setAccessible(true);
    wanted.invoke(ambig, query).asInstanceOf[QueryResults[Entity]]
  }

  def createFilter( name : String, operator: String, value : String ): Filter = {

    operator match {
      case "=" =>  PropertyFilter.eq(name, value);
      case ">" =>  PropertyFilter.gt(name, value);
      case "<" =>  PropertyFilter.lt(name, value);
      case ">=" =>  PropertyFilter.ge(name, value);
      case "<=" =>  PropertyFilter.le(name, value);
    }
  }

  def createAncestorFilter( key : Key ): Filter = {
    PropertyFilter.hasAncestor(key)
  }


   def query(namespace : String, kind : String, filter : Option[Filter] ):  QueryResults[Entity] = {

    logger.info("query "+ namespace + "::" + kind );
    val query  = filter match {
      case Some(f) => newEntityQueryBuilder().setKind(kind).setFilter(f).build() ;
      case _       => newEntityQueryBuilder().setKind(kind).build();
    }
    //val query   = newEntityQueryBuilder().setKind(kind).setFilter(filter).build();
    // we need this to do  datastore(namespace).run() ... because of ambiguous 'run' method
    val ambig = datastore(namespace)
    val methods = ambig.getClass.getMethods.filter(_.getName == "run")
    var wanted = methods.find(_.getParameterTypes.length == 1).get
    wanted.setAccessible(true);
    wanted.invoke(ambig, query).asInstanceOf[QueryResults[Entity]]
  }


}
