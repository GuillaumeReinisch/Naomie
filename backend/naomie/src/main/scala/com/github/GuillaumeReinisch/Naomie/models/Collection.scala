package com.github.GuillaumeReinisch.Naomie.models

import com.google.cloud.datastore.Entity
import org.slf4j.LoggerFactory


case class ParameterSummary(keyParam: String, formula: String, parameters :Map[String,String] ){}


object ParameterSummary {

  val logger    =  LoggerFactory.getLogger(getClass)

  def apply( entity : Entity) : ParameterSummary = {

    val parameters =  entity.getString("variables").split(" ").toList.drop(1)
      .map( key_val => (key_val.split(",")(0) , key_val.split(",")(1)) )
      .toMap;

    logger.info("variables: ", parameters.toString )
    ParameterSummary(entity.getString("formula"), entity.getString("formula"), parameters )
  }
}


case class GraphicSummary(keyGraphic: String, name: String){}

/**
  * Created by lcts on 15/03/17.
  */
case class Collection(name:String, key : String, userName: String,
                      description:String,  creationDate: String ) {
}


object Collection {

  def apply( entity : Entity) : Collection = {

     Collection(entity.getString("name"),
      entity.getKey().getName(),
      entity.getString("userName"),
      entity.getString("description"),
      entity.getString("creationDate")
    )
  }
}