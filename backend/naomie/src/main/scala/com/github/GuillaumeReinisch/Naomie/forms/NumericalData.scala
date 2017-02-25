package com.github.GuillaumeReinisch.Naomie.forms


case class ScenarioForm( val name : String, val metadata: Map[String,String] = Map.empty[String,String]) {

}

case class DatasetForm( val name : String, val vheaders : Vector[String], val columns : Map[String,Vector[Double]]) {

}
