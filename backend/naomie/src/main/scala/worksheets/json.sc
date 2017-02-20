import org.json4s._
import org.json4s.jackson.JsonMethods._

println("hello Json!")

case class Operator(typeOp:String, left: Operator, right:Operator ){}
val formula = parse(
  """{
    |"type":"add",
    |"left" : { "type": "number" ,"value":"5.0"},
    |"right" : {"type": "number" ,"value":"5.0"}
    |}""".stripMargin)

pretty(render(formula))

println(formula \\ "type")

formula match{
  case JObject(s) => println(s)
  case _ => println("not object")
}
/*
* case add  => Add(parse(left),parse(right))
* case mult => Mult(parse(left),parse(right))
* case div  => Div(parse(left),parse(right))
* */
//println(formula.extract[Operator])