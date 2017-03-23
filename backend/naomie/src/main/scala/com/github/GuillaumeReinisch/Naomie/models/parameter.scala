package com.github.GuillaumeReinisch.Naomie.models

import com.google.cloud.datastore.Entity
import org.slf4j.LoggerFactory

/**
  * Created by lcts on 20/03/17.
  */
case class Parameter( formula: String, key : String, variables: Map[String,String], userName: String, creationDate:String) {

}



object Parameter {

  val logger    =  LoggerFactory.getLogger(getClass)

  def apply( entity : Entity) : Parameter = {

    logger.info("construct parameter")
    val parameters =  entity.getString("variables").split(" ").toList.drop(1)
      .map( key_val => (key_val.split(",")(0) , key_val.split(",")(1)) )
      .toMap;

    logger.info("variables: ", parameters.toString )
    Parameter(entity.getString("formula"),
      entity.getString("formula"),
      parameters,
      entity.getString("userName"),
      entity.getString("creationDate")
    )
  }
}
