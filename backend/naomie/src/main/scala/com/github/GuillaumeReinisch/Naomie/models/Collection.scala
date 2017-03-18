package com.github.GuillaumeReinisch.Naomie.models

import com.google.cloud.datastore.Entity

/**
  * Created by lcts on 15/03/17.
  */
case class Collection(name:String, key : String, userName: String, description:String,  creationDate: String ,namespace :String = "") {

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