package com.github.GuillaumeReinisch.Naomie

import scala.collection.JavaConverters._

import com.github.GuillaumeReinisch.Naomie.datastore.DatastoreService
import com.github.GuillaumeReinisch.Naomie.forms.{DatasetForm, GraphicFrom, ScenarioForm}
import com.github.GuillaumeReinisch.Naomie.models.{Dataset, Scenario}
import org.scalatra.{Created, NotFound}
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

//world weather 05d283ea10134f78b0c182351172002
//https://developer.worldweatheronline.com/premium-api-explorer.aspx
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
    val key         = DatastoreService.createKey(graphicForm.name, "Graphic","graphic")
    val graphic     = Graphic(key.getName,graphicForm.name,graphicForm.axis, graphicForm.metadata)
    val entity      = DatastoreService.saveGraphic(graphic,key)

    logger.info(entity.toString)
    response.addHeader("ACK", "GOT IT")
  }

  post("/data/createScenario") {

    logger.info("create Scenario request")

    implicit val formats = DefaultFormats

    val form     = parse(request.body).extract[ScenarioForm]
    val key      = DatastoreService.createKey(form.name, "Scenario","data")
    val scenario = Scenario(key.getName,form.name, form.metadata)

    val entity   = DatastoreService.saveScenario(scenario,key)

    logger.info(entity.toString)
    Created( request.body, Map("Key" -> key.getName))
  }

  post("/data/addDataset/:scenario") {

    logger.info("add dataset request")
    logger.info("target scenario: " + params.get("scenario"))

    params.get("scenario") match {
      case Some(scenario) =>
        val form     = parse(request.body).extract[DatasetForm]
        val dataset  = Dataset(form.name,scenario, form.vheaders, form.columns)
        val entity   = DatastoreService.saveDataset(dataset, DatastoreService.createKey( form.name + "@" +  params("scenario") , "Dataset","data"));
      case None => NotFound("Sorry, the scenario could not be found"); logger.info("all graphs");
    }

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
      case None => {logger.info("all graphs"); GraphicsData.all;}
    }
  }

  get("/scenarios") {

    logger.info("scenarios request")

    params.get("uid") match {
      case Some(uid) =>
        logger.info("get scenario " + uid);
        DatastoreService.getScenario(DatastoreService.createKey(uid,"Scenario","data")) match {
          case Some(scenario) => scenario;
          case None => NotFound("Sorry, the scenario could not be found");
        };
      case None => {
        logger.info("get all scenarios");
        val result = DatastoreService.query("data","Scenario").asScala
        val list   = scala.collection.mutable.ListBuffer.empty[Scenario];//Vector.empty[Scenario];
        result.foreach( a => list += Scenario(a))
        list
      }
    }


    //var list = List.empty[Scenario];
    //result.foreach( entity => { println(Scenario(entity)); list :+ Scenario(entity);})
    //println(list);
    /*
    var q = new Query("Person")

    val keyFactory  = datastore.newKeyFactory().setKind("Graphic");
    val key         = keyFactory.newKey("graph1");
    val transaction = datastore.newTransaction();
    val entity      = transaction.get(key);

    logger.info(entity.toString)

    params.get("uid") match {
      case Some(uid) => entity;
      case None => {logger.info("all graphs"); GraphicsData.all;}
    }
    */
  }

}
