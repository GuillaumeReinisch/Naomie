package com.github.GuillaumeReinisch.Naomie


import org.scalatra.ScalatraServlet
import org.scalatra.swagger.{ApiInfo, JacksonSwaggerBase, Swagger}

class ResourcesApp(implicit val swagger: Swagger) extends ScalatraServlet with JacksonSwaggerBase {

}

class NaomieSwagger extends Swagger("1.0", "1",ApiInfo("","","","","",""))
