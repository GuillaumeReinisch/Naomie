package com.github.GuillaumeReinisch.Naomie.models

import org.json4s.jackson.Json


object FormulaParser {

  def parse(json: Json) : FormulaTree = {
    Number(5.0)
  }
}
/**
  * Created by lcts on 18/02/17.
  */
class formula( uid: String, name: String , formula : FormulaTree ) {

  def evaluate( params: Map[String,Double]) : Option[Double] = {
    formula.evaluate(params)
  }
}

trait BinaryOperation {

  def evaluate( params: Map[String,Double], left:FormulaTree, right:FormulaTree, binOp: (Double,Double)=> Double) : Option[Double] = {

    (left.evaluate(params),right.evaluate(params)) match {
    case (l : Some[Double], r: Some[Double]) => Some(binOp(l.get , r.get))
    case _ => None
  }
  }
}

abstract class FormulaTree {

  def extractParameters() : List[String] ;

  def evaluate( params: Map[String,Double]) : Option[Double];
}

case class Number(value:Double) extends FormulaTree {

  def extractParameters() : List[String] = {
    Nil
  }

  def evaluate(params: Map[String,Double]) : Option[Double] = {
    Some(value);
  }
}

case class Variable(name:String) extends FormulaTree {

  def extractParameters() : List[String] = {
    List(name);
  }

  def evaluate(params: Map[String,Double]) : Option[Double] = {
    params.get(name);
  }
}

case class Add(left:FormulaTree , right: FormulaTree) extends FormulaTree with BinaryOperation{

  def extractParameters() : List[String] = {
    left.extractParameters() ::: right.extractParameters()
  }

  def evaluate(params: Map[String,Double]) : Option[Double] = {
    evaluate(params,left,right, (a,b)=> a+b )
  }
}

case class Diff(left:FormulaTree , right: FormulaTree) extends FormulaTree with BinaryOperation {

  def extractParameters() : List[String] = {
    left.extractParameters() ::: right.extractParameters()
  }

  def evaluate(params: Map[String,Double]) : Option[Double] = {
    evaluate(params,left,right, (a,b)=> a-b )
  }
}

case class Mult(left:FormulaTree , right: FormulaTree) extends FormulaTree with BinaryOperation {

  def extractParameters() : List[String] = {
    left.extractParameters() ::: right.extractParameters()
  }

  def evaluate(params: Map[String,Double]) : Option[Double] = {
    evaluate(params,left,right, (a,b)=> a*b );
  }
}

case class Div(left:FormulaTree , right: FormulaTree) extends FormulaTree with BinaryOperation {

  def extractParameters() : List[String] = {
    left.extractParameters() ::: right.extractParameters()
  }

  def evaluate(params: Map[String,Double]) : Option[Double] = {
    evaluate(params,left,right, (a,b)=> a/b );
  }
}
