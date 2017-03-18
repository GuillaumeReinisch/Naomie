package com.github.GuillaumeReinisch.Naomie.models

import com.google.cloud.datastore.Entity
import org.slf4j.LoggerFactory

/**
  * Created by lcts on 12/03/17.
  */
case class Project( name : String,key : String, userName: String, creationDate: String, summary: String, scenarios : List[String] = List.empty[String],  graphics : List[String] = List.empty[String]) {

}


object Project {
  val logger    =  LoggerFactory.getLogger(getClass)
  def apply( entity : Entity) : Project = {

    Project(entity.getString("name"),
      entity.getString("name"),
      entity.getString("userName"),
      entity.getString("creationDate"),
      entity.getString("summary"),
      entity.getString("scenarios").split(" ").toList,
      entity.getString("graphics").split(" ").toList
    )
  }
}
