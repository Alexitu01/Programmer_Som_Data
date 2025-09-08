(* Programming language concepts for software developers, 2010-08-28 *)

(* Evaluating simple expressions with variables *)

module Intro2

(* Association lists map object language variables to their values *)

let env = [("a", 3); ("c", 78); ("baf", 666); ("b", 111)];;

let emptyenv = []; (* the empty environment *)

let rec lookup env x =
    match env with 
    | []        -> failwith (x + " not found")
    | (y, v)::r -> if x=y then v else lookup r x;;

let cvalue = lookup env "c";;


(* Object language expressions with variables *)


type expr = 
  | CstI of int
  | Var of string
  | Prim of string * expr * expr
  //1.1 iv: conditional expression extention
  | If of expr * expr * expr;;

let e1 = CstI 17;;

let e2 = Prim("+", CstI 3, Var "a");;

let e3 = Prim("+", Prim("*", Var "b", CstI 9), Var "a");;


(* Evaluation within an environment *)

let rec eval e (env : (string * int) list) : int =
    match e with  
    | CstI i            -> i
    | Var x             -> lookup env x 
    | Prim("*", e1, e2) -> eval e1 env * eval e2 env
    | Prim("-", e1, e2) -> eval e1 env - eval e2 env
    // 1.1 ii: new prim bindings for max, min, and '=='
    | Prim("max", e1, e2) -> max (eval e1 env) (eval e2 env)
    | Prim("min", e1, e2) -> min (eval e1 env) (eval e2 env)
    | Prim("==", e1, e2) -> if eval e1 env = eval e2 env then 1 else 0
    // 1.1 iii: alternative binding for eval, branching out
    | Prim(ope, e1, e2) -> 
      let i1 = eval e1 env
      let i2 = eval e2 env
      match ope with
      |"+" -> i1 + i2
      |_ -> failwith "unknown primative"
    // 1.1 iv: conditional matching
    | If(e1, e2, e3) -> if eval e1 env <> 0 then eval e2 env else eval e3 env;;

let e1v  = eval e1 env;;
let e2v1 = eval e2 env;;
let e2v2 = eval e2 [("a", 314)];;
let e3v  = eval e3 env;;

//example expressions 1.1 ii
let e4 =  CstI 15;;
let e5 = eval (Prim("==", e4, CstI 15)) env;;
let e6 = eval (Prim("max", e1, e4)) env;;
let e7 = eval(Prim("min", e1 ,e4)) env;;

//1.2 i new datatype
type aexpr =
  | CstI of int
  | Var of string
  | Add of aexpr * aexpr
  | Mul of aexpr * aexpr
  | Sub of aexpr * aexpr 

//1.2 ii expression using new datatype
let expr1 = Sub(Var "v", Add(Var "w", Var "z"));;
let expr2 = Mul(CstI 2, Sub(Var "v", Add(Var "w", Var "z")));;

let expr3 = Add(Var "x", Add(Var "y", Add(Var "z", Var "v")));; //should be the other way around but with addition it doesn't matter

//1.2 iii fmt function
let rec fmt (e : aexpr) : string =
  match e with
  | CstI i -> string i
  | Var x -> x
  | Add(e1, e2) -> "(" + fmt e1 + " + " + fmt e2 + ")"
  | Mul(e1, e2) -> "(" + fmt e1 + " * " + fmt e2 + ")"
  | Sub(e1, e2) -> "(" + fmt e1 + " - " + fmt e2 + ")"

// 1.2 iv simplify function
let rec simplify (e : aexpr) : aexpr =
  match e with
  | CstI i -> CstI i
  | Var x -> Var x
  | Add(e1, e2) ->
    let s1 = simplify e1
    let s2 = simplify e2
    match s1, s2 with
    | CstI 0, e2 -> e2
    | e1, CstI 0 -> e1
    |_ -> Add(s1, s2)
  | Mul(e1, e2) ->
    let s1 = simplify e1
    let s2 = simplify e2
    match s1, s2 with
    | CstI 0, _ -> CstI 0
    | _, CstI 0 -> CstI 0
    | CstI 1, e2 -> e2
    | e1, CstI 1 -> e1
    |_ -> Mul(s1, s2)
  | Sub(e1, e2) ->
    let s1 = simplify e1
    let s2 = simplify e2
    match s1, s2 with   
    |e1, CstI 0 -> e1
    |e1, e2 when e1 = e2 -> CstI 0
    |_ -> Sub(s1, s2)


//1.2 V symbolic differentiation of simple arithmetic expressions
let rec skillDiff (e1 : aexpr ) (x : string) : aexpr =
  match e1 with
  | CstI _ -> CstI 0
  | Var y -> if y = x then CstI 1 else CstI 0
  | Add (e1, e2) ->   Add (skillDiff e1 x, skillDiff e2 x)
  | Sub (e1, e2) ->   Sub (skillDiff e1 x, skillDiff e2 x)
  | Mul (e1, e2) ->   Add(Mul(skillDiff e1 x, e2), Mul(e1, skillDiff e2 x))