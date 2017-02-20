package models.test

import com.github.GuillaumeReinisch.Naomie.models._
import org.scalatest._

/**
  * Created by guigui on 18/02/17.
  */
class formula extends FlatSpec with Matchers {

  val vars = Map("a" -> 5.0, "b" -> 10.0);

  "The number 5.0 " should " evaluate to 5." in {
    Number(5).evaluate(vars).get should be (5);
  }

  "The formula 5.0 + 5.0 " should " evaluate to 10." in {
    Add(Number(5),Number(5)).evaluate(vars).get should be (10);
  }

  "The formula a + b " should " evaluate to 15." in {
    Add(Variable("a"),Variable("b")).evaluate(vars).get should be (15);
  }

  "The formula a/b " should " evaluate to .5" in {
    Div(Variable("a"),Variable("b")).evaluate(vars).get should be (0.5);
  }

  "The formula a/b " should " evaluate to 50" in {
    Mult(Variable("a"),Variable("b")).evaluate(vars).get should be (50);
  }

  "The parameters required for the formula 2*b " should " be 'b' " in {
    Mult(Number(2),Variable("b")).extractParameters() should be (List("b"));
  }

  "The formula '2*4+5' " should " evaluate to 13 " in {
    formulaParser.parse("2*4+5").evaluate(vars).get should be (13.0);
  }
}
