package com.github.GuillaumeReinisch.Naomie

import org.slf4j.LoggerFactory

// JSON-related libraries
import org.json4s.{DefaultFormats, Formats}

// JSON handling support from Scalatra
import org.scalatra.json._

case class Graphic( id: String, name: String , axis: List[String]){}

object GraphicsData {

  /**
    * Some fake data so we can simulate retrievals.
    */
  var all = List(
    Graphic("0", "temperature", List("oslo","paris")),
    Graphic("1", "pressure",List("oslo","paris")),
    Graphic("2", "wind",List("oslo","paris")))
}


class NaomieServlet extends NaomieStack with JacksonJsonSupport {

  protected implicit val jsonFormats: Formats = DefaultFormats

  val logger =  LoggerFactory.getLogger(getClass)

  before() {
    contentType = formats("json")
  }


  get("/") {
    contentType = formats("html")
    <html>
      <body>
        <h1>Hello, world!</h1>
        Say <a href="hello-scalate">hello to Naomie</a>.
      </body>
    </html>
  }

  get("/graphics") {
    GraphicsData.all;
  }
}
