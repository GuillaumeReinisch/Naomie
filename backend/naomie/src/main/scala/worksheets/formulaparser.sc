

import com.github.GuillaumeReinisch.Naomie.models._

println("hello Json!")


object formulaParser{

  def eval(expr: String) : FormulaTree = {
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
      if(expr.trim()(0) == '(') eval(expr.trim.substring(1,expr.length-1))
      else Number(expr.toDouble)
    }
    else{
      expr(opLoc) match{
        case '+' => Add(eval(expr.substring(0,opLoc)),eval(expr.substring(opLoc+1)))
        case '-' => Diff(eval(expr.substring(0,opLoc)),eval(expr.substring(opLoc+1)))
        case '*' => Mult(eval(expr.substring(0,opLoc)),eval(expr.substring(opLoc+1)))
        case '/' => Div(eval(expr.substring(0,opLoc)),eval(expr.substring(opLoc+1)))
      }
    }
  }
}

println(formulaParser.eval("5+(4*2)"))

