package com.github.GuillaumeReinisch.Naomie.models
import com.google.cloud.datastore._

case class Dataset( val name : String, val scenarioUid: String,  val vheaders: Vector[String], val columns: Map[String,Vector[Double]] , val metadata : Map[String,String] = Map[String,String]() ){}

case class Scenario( val uid: String, val name: String , val metadata : Map[String,String]) {
}

object Scenario {

  def apply( entity : Entity) : Scenario = {
    Scenario(entity.getKey().getName(), entity.getString("name"),  Map.empty[String,String] )
  }
}

