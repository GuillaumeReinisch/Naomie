package com.github.GuillaumeReinisch.Naomie.models

case class Dataset( val name : String, val scenarioUid: String,  val vheaders: Vector[String], val columns: Map[String,Vector[Double]] , val metadata : Map[String,String] = Map[String,String]() ){}

case class Scenario( val uid: String, val name: String , val metadata : Map[String,String]) {
}
