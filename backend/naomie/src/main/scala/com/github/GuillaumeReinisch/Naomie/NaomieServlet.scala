package com.github.GuillaumeReinisch.Naomie

import com.github.GuillaumeReinisch.Naomie.datastore.DatastoreService
import com.github.GuillaumeReinisch.Naomie.forms._
import com.github.GuillaumeReinisch.Naomie.models.{Collection, _}
import org.scalatra._
import org.slf4j.LoggerFactory

import scala.collection.JavaConverters._

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
    Graphic("0", "T Summer vs Winter", List("T_July","T_January")),
    Graphic("1", "T/P Summer vs Winter",List("T_July/T_January","P_July/P_January")))
}

//world weather 05d283ea10134f78b0c182351172002
//https://developer.worldweatheronline.com/premium-api-explorer.aspx
class NaomieServlet extends NaomieStack  with JacksonJsonSupport  with CorsSupport {

  protected implicit val jsonFormats: Formats = DefaultFormats
  protected val applicationName: Option[String] = Some("/v2/Store")
  protected val applicationDescription    = "The Naomie API. It exposes operations for managing data w/ datastore."

  val logger    =  LoggerFactory.getLogger(getClass)

  val projectId   = "malcom-gedi"
  val namespace   = "graphics"
  val datastore   = DatastoreOptions.newBuilder().setProjectId(projectId).setNamespace(namespace).build().getService();

  options("/*"){
    response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
  }

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


   post("/users/login") {

    logger.info("login request")

    // needed for Lift-JSON
    implicit val formats = DefaultFormats
    val userForm    = parse(request.body).extract[UserForm]
     logger.info(userForm.toString)
    userForm match {
      case UserForm("reinisch.gui@gmail.com","guillaume") => Ok(User("guigui","reinisch.gui@gmail.com","guillaume")) ;
      case _ => Unauthorized("Authentification failed")
     }
  }

   post("/projects/createProject") {

    logger.info("create project request")

    // needed for Lift-JSON
    implicit val formats = DefaultFormats
    val projectForm = parse(request.body).extract[ProjectForm]
    logger.info(projectForm.toString)

    val key         = DatastoreService.createKey(projectForm.name, "Project","admin")
    val project     = Project(projectForm.name,projectForm.name, projectForm.userName, projectForm.creationDate , projectForm.summary )
    val entity      = DatastoreService.saveProject(project,key)

    logger.info(entity.toString)
  }

   get("/projects/:user") {

     logger.info("get projects request")

     params.get("user") match {
     case Some(user) =>
       logger.info("get projects of " + user);
      // val propertyFilter  = DatastoreService.createFilter("userName","=",user);
       val results         = DatastoreService.query("admin","Project", None /*Some(propertyFilter)*/)
       logger.info("query succeeded" );
       val list            = scala.collection.mutable.ListBuffer.empty[Project];
       while (results.hasNext()) {
         list += Project(results.next())
       }
       logger.info("number of project " + list.size);
       list
     case None => BadRequest("User can not be found")
   }
  }

  post("/createCollection") {

    logger.info("create collection request")

    // needed for Lift-JSON
    implicit val formats = DefaultFormats
    val collectionForm = parse(request.body).extract[CollectionForm]
    logger.info(collectionForm.toString)

    val key         = DatastoreService.createKey(collectionForm.name, "Collection","parameters")
    val collection  = GraphicCollection(collectionForm.name,collectionForm.name, collectionForm.userName, collectionForm.creationDate , collectionForm.description )
    val entity      = DatastoreService.saveCollection(collection,key)

    logger.info(entity.toString)
  }

  get("/collections/:user") {

    logger.info("get collections request")

    params.get("user") match {
      case Some(user) =>
        logger.info("get collection of " + user);
        // val propertyFilter  = DatastoreService.createFilter("userName","=",user);
        val results         = DatastoreService.query("parameters","Collection", None /*Some(propertyFilter)*/)
        logger.info("query succeeded" );
        val list            = scala.collection.mutable.ListBuffer.empty[Collection];
        while (results.hasNext()) {
          list += Collection(results.next())
        }
        logger.info("number of collection " + list.size);
        list
      case None => BadRequest("User can not be found")
    }
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

    get("/data/variables") {

    logger.info("variables request")
    val results   = DatastoreService.query("data","Dataset", None)
    val list      = scala.collection.mutable.ListBuffer.empty[Dataset];

    while (results.hasNext()) {
      list += Dataset(results.next())
    }
    val all = for( dataset <- list; vheader<- dataset.vheaders; column <- dataset.columns )
        yield dataset.name.split("@")(0) +"."+vheader+"."+column._1;
    all.toSet
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
        DatastoreService.getScenario(DatastoreService.createKey(uid, "Scenario", "data")) match {
          case Some(scenario) => scenario;
          case None => NotFound("Sorry, the scenario could not be found");
        };
      case None =>
        logger.info("get all scenarios");
        val result = DatastoreService.query("data", "Scenario").asScala
        val list = scala.collection.mutable.ListBuffer.empty[Scenario]; //Vector.empty[Scenario];
        result.foreach(a => list += Scenario(a))
        logger.info("number of scenario " + list.size);
        list
    }
  }
/*
  get("/datasets") {

      logger.info("datasets request")

      params.get("uid") match {
        case Some(uid) =>
          logger.info("get datasets of " + uid);
          val propertyFilter = DatastoreService.createFilter("parent","=",uid);
          val result         = DatastoreService.query("data","Dataset", propertyFilter).asScala
          val list   = scala.collection.mutable.ListBuffer.empty[Scenario];//Vector.empty[Scenario];
          result.foreach( a => list += Scenario(a))
          list
        case None => NotFound("Sorry, you need to supply the scenario id");
      }
  }*/

   get("/datasets/:uid") {

      logger.info("datasets request")

      params.get("uid") match {
        case Some(uid) =>
          logger.info("get datasets of " + uid);
          val propertyFilter  = DatastoreService.createFilter("parent","=",uid);
          val results         = DatastoreService.query("data","Dataset", Some(propertyFilter))
          val list            = scala.collection.mutable.ListBuffer.empty[Dataset];
          while (results.hasNext()) {
            list += Dataset(results.next())
          }
          list
        case None => NotFound("Sorry, you need to supply the scenario id");
      }
  }

    get("/namespaces") {

      logger.info("namespaces request")
      val results         = DatastoreService.query("data","Dataset", None)
      val set             = scala.collection.mutable.Set.empty[String];
      while (results.hasNext()) {
        val dataset    = Dataset(results.next());
        dataset.columns.foreach{ case (key:String ,_ ) => set += dataset.name.split("@")(0) +"."+ key }
      }

  }


}
