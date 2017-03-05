package com.github.GuillaumeReinisch.Naomie.models
import com.google.cloud.datastore._
import org.slf4j.LoggerFactory

case class Dataset( val name : String, val scenarioUid: String,  val vheaders: Vector[String], val columns: Map[String,Vector[Double]] , val metadata : Map[String,String] = Map[String,String]() ){}

object Dataset {
  val logger    =  LoggerFactory.getLogger(getClass)
  def apply( entity : Entity) : Dataset = {

    val headers = entity.getString("hheaders").split(" ").toList.drop(1);
    logger.info("dataset's headers:" + headers.toString);

    val columns = for { hHeader <- headers }
      yield (hHeader, entity.getString(hHeader).split(" ").toList.drop(1).map( _.toDouble ).toVector)

    Dataset(entity.getKey().getName(),
            entity.getString("parent"),
            entity.getString("vheaders").split(" ").toVector,
            columns.toMap )
  }
}


case class Scenario( val uid: String, val name: String , val metadata : Map[String,String]) {
}

object Scenario {

  def apply( entity : Entity) : Scenario = {
    Scenario(entity.getKey().getName(), entity.getString("name"),  Map.empty[String,String] )
  }
}

