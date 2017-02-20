package com.github.GuillaumeReinisch.Naomie

import com.github.GuillaumeReinisch.Naomie.datastore.Datastore
import com.github.GuillaumeReinisch.Naomie.forms.GraphicFrom
import org.slf4j.LoggerFactory

// JSON-related libraries
import org.json4s.{DefaultFormats, Formats}
// JSON handling support from Scalatra

import com.github.GuillaumeReinisch.Naomie.models.Graphic
import com.google.cloud.datastore.DatastoreOptions
import org.scalatra.json._

object GraphicsData {

  /**
    * Some fake data so we can simulate retrievals.
    */
  var all = List(
    Graphic("0", "temperature", List("oslo","paris")),
    Graphic("1", "pressure",List("oslo","paris")),
    Graphic("2", "wind",List("oslo","paris")))
}


class NaomieServlet extends NaomieStack with JacksonJsonSupport /*with SwaggerSupport*/ {

  protected implicit val jsonFormats: Formats = DefaultFormats

  protected val applicationName           = Some("Naomie")
  protected val applicationDescription    = "The Naomie API. It exposes operations for managing data w/ datastore."

  val logger    =  LoggerFactory.getLogger(getClass)

  val projectId   = "malcom-gedi"
  val namespace   = "graphics"
  val datastore   = DatastoreOptions.newBuilder().setProjectId(projectId).setNamespace(namespace).build().getService();

  before() {
    contentType = formats("json")
  }


  get("/") {
    logger.info("get(\"/\") request")
    contentType = formats("html")
    <html>
      <body>
        <h1>Hello, world!</h1>
        Say <a href="hello-scalate">hello to Naomie</a>.
      </body>
    </html>
  }

  post("/addGraphic") {

    logger.info("addGraphic request")

    // needed for Lift-JSON
    implicit val formats = DefaultFormats

    val graphicForm = parse(request.body).extract[GraphicFrom]
    val key         = Datastore.createKey(graphicForm.name, "Graphic")
    val graphic     = Graphic(key.getName,graphicForm.name,graphicForm.axis, graphicForm.metadata)
    val entity      = Datastore.saveGraphic(graphic,key)

    logger.info(entity.toString)
    response.addHeader("ACK", "GOT IT")
  }

  get("/graphics") {

    logger.info("graphics request")

    val keyFactory  = datastore.newKeyFactory().setKind("Graphic");
    val key         = keyFactory.newKey("graph1");
    val transaction = datastore.newTransaction();
    val entity      = transaction.get(key);

    logger.info(entity.toString)

    params.get("uid") match {
      case Some(uid) => entity;
      case None => GraphicsData.all;
    }
  }

}
