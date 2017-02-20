package com.github.GuillaumeReinisch.Naomie.datastore

import com.github.GuillaumeReinisch.Naomie.models.Graphic
import com.google.cloud.datastore.{DatastoreOptions, Key, Entity}
/**
  * Created by lcts on 19/02/17.
  */
object Datastore {

  val projectId   = "malcom-gedi"
  val namespace   = "graphics"
  val datastore   = DatastoreOptions.newBuilder().setProjectId(projectId).setNamespace(namespace).build().getService();

  def createKey(name : String, kind : String) : Key = {

    val keyFactory  = datastore.newKeyFactory().setKind(kind);
    keyFactory.newKey(name);
  }

  def saveGraphic(graphic : Graphic, key : Key) : Entity = {

    val entity      = Entity.newBuilder(key)
      .set("name", graphic.name)
      .set("axis", graphic.axis.sorted.reduceLeft{ (acc , elem : String) => acc+" "+elem });

    graphic.metadata.foldLeft(entity){ case (e,(name,value) ) => e.set(name,value) };

    datastore.put(entity.build());
  }

}
