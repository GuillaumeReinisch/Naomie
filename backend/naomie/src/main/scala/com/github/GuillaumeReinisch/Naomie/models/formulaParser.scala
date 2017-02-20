package com.github.GuillaumeReinisch.Naomie.models

/** \brief parse a formula to a FormulaTree
  *
  * Created by guigui on 18/02/17.
  */
object formulaParser {

  def parse(expr: String) : FormulaTree = {

    var i = expr.length -1
    var parensCount = 0
    var opLoc = -1
    while(i>0){
      if(expr(i) ==')') parensCount +=1
      else if(expr(i) =='(') parensCount -=1
      else if(parensCount==0 && (expr(i) == '+' || expr(i) =='-')){
        opLoc=i;
        i = -1
      }
      else if(parensCount==0 && opLoc<0 && (expr(i) == '*' || expr(i) =='/')){

        opLoc=i;
      }
      i -= 1
    }

    if(opLoc<0){
      if(expr.trim()(0) == '(') parse(expr.trim.substring(1,expr.length-1))
      else Number(expr.toDouble)
    }
    else{
      expr(opLoc) match{
        case '+' => Add(parse(expr.substring(0,opLoc)),parse(expr.substring(opLoc+1)))
        case '-' => Diff(parse(expr.substring(0,opLoc)),parse(expr.substring(opLoc+1)))
        case '*' => Mult(parse(expr.substring(0,opLoc)),parse(expr.substring(opLoc+1)))
        case '/' => Div(parse(expr.substring(0,opLoc)),parse(expr.substring(opLoc+1)))
      }
    }
  }
}
