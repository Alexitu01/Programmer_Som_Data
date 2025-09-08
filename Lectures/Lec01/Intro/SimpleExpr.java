// File Intro/SimpleExpr.java
// Java representation of expressions as in lecture 1
// sestoft@itu.dk * 2010-08-29

import java.util.Map;
import java.util.HashMap;

abstract class Expr { 
  abstract public int eval(Map<String,Integer> env);
  abstract public String toString();
  abstract public String fmt(Map<String,Integer> env);
  abstract public Expr simplify();
}

//Binop class to define binary operations (i)
abstract class Binop extends Expr{
    
}

//Add class that extends Binop (i)
class Add extends Binop{
  public Expr ex1,ex2;

  public Add(Expr ex1, Expr ex2){
    this.ex1 = ex1;
    this.ex2 = ex2;
  }

  //(iii)
  public int eval(Map<String,Integer> env){ 
    return ex1.eval(env) + ex2.eval(env);
  }

  public String toString(){
    return "(" + ex1.toString() + " + "  + ex2.toString() + ")";
  }
    //used to be "fmt2"
  public String fmt(Map<String,Integer> env){
    return "(" + ex1.fmt(env) + " + " + ex2.fmt(env) + ")";
  }

   //Function to simplify (iv)
  public Expr simplify(){
    Expr t1 = ex1.simplify();
    Expr t2 = ex2.simplify();

    if(t1 instanceof CstI && ((CstI)t1).i == 0){
      return t2;

    } else if (t2 instanceof CstI && ((CstI)t2).i == 0){
      return t1;

    } else{
      return new Add(t1, t2);
    }
  } 

}

//Multiplier class that extends Binop (i)
class Mul extends Binop{
   public Expr ex1,ex2;

  public Mul(Expr ex1, Expr ex2){
    this.ex1 = ex1;
    this.ex2 = ex2;
  }

  //(iii)
  public int eval(Map<String,Integer> env){
    return ex1.eval(env) * ex2.eval(env);
  }

  public String toString(){
    return "(" + ex1.toString() + " * " + ex2.toString() + ")";
  }
  //used to be "fmt2"
  public String fmt(Map<String,Integer> env){
    return "(" + ex1.fmt(env) + " * " + ex2.fmt(env) + ")";
  }

  //Function to simplify (iv)
  public Expr simplify(){
    Expr t1 = ex1.simplify();
    Expr t2 = ex2.simplify();

    if(t1 instanceof CstI){
      int t1Value = ((CstI) t1).i;
      
      if(t1Value == 0){
        return new CstI(0);

      } else if (t1Value == 1){
        return t2;
      }
    } else if(t2 instanceof CstI){
      int t2Value = ((CstI) t2).i;
      
      if(t2Value == 0){
        return new CstI(0);

      } else if(t2Value == 1){
        return t1;
      }
    } 
    return new Mul(t1,t2);
  }
} 

//Subtraction class that extends Binop (i)
class Sub extends Binop{
  Expr ex1, ex2;

  public Sub (Expr ex1, Expr ex2){
    this.ex1 = ex1;
    this.ex2 = ex2;
  }

  //(iii)
  public int eval(Map<String,Integer> env){
    return ex1.eval(env) - ex2.eval(env);
  }

  public String toString(){
    return "(" + ex1.toString() + " - " + ex2.toString() + ")";
  }

  //used to be "fmt2"
  public String fmt(Map<String,Integer> env){
    return "(" + ex1.fmt(env) + " - " + ex2.fmt(env) + ")";
  }

  //Function to simplify (iv)
  public Expr simplify(){
    Expr t1 = ex1.simplify();
    Expr t2 = ex2.simplify();

    if(t2 instanceof CstI){
      if(((CstI) t2).i == 0){
        return t1;

      } else if(t1 instanceof CstI && ((CstI) t2).i == ((CstI) t1).i){
        return new CstI(0);
        
      }
    }

      return new Sub(t1,t2);
    }
  }


class CstI extends Expr { 
  protected final int i;

  public CstI(int i) { 
    this.i = i; 
  }

  public int eval(Map<String,Integer> env) {
    return i;
  } 

  public String toString() {
    return ""+i;
  }
    //used to be "fmt2"
  public String fmt(Map<String,Integer> env){
    return ""+i;
  }

  //Function to simplify (iv)
  public Expr simplify(){
    return this;
  }

}

class Var extends Expr { 
  protected final String name;

  public Var(String name) { 
    this.name = name; 
  }

  public int eval(Map<String,Integer> env) {
    return env.get(name);
  } 
  //toString to return the class value -- NOTICE: used to be "fmt" (i)
  public String toString() {
    return name;
  } 
  //used to be "fmt2"
  public String fmt(Map<String,Integer> env){
    return ""+env.get(name);
  }

  //Function to simplify (iv)
  public Expr simplify(){
    return this;
  }
}

//Changes made to SimpleExpr:
/*
  1. e1,e2,e3 changed to use the hierarchy.
  
  2. "fmt" changed to "toString" and "fmt2" changed to "fmt"

 */
public class SimpleExpr {
  public static void main(String[] args) {
    Expr e1 = new CstI(17);
    Expr e2 = new Add(new CstI(3), new Var("a"));
    Expr e3 = new Add(new Mul(new Var("b"), new CstI(9)), 
		            new Var("a"));

    Map<String,Integer> env0 = new HashMap<String,Integer>();
    env0.put("a", 3);
    env0.put("c", 78);
    env0.put("baf", 666);
    env0.put("b", 111);

    System.out.println("Env: " + env0.toString());

    // (ii)
    System.out.println(e1.toString() + " = " + e1.fmt(env0) + " = " + e1.eval(env0));
    System.out.println(e2.toString() + " = " + e2.fmt(env0) + " = " + e2.eval(env0));
    System.out.println(e3.toString() + " = " + e3.fmt(env0) + " = " + e3.eval(env0));
  }
}