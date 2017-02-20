package com.github.GuillaumeReinisch.Naomie.models

/**
  * Created by Guillaume on 17/02/17.
  */
case class Graphic( uid: String, name: String , axis: List[String], metadata : Map[String,String] = Map.empty[String,String] , overlays: List[String] = List.empty[String]) {

}
