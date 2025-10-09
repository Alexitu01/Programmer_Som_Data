

module Fun

(*
let rec merge x y acc=
    match x,y with
    | xh::xt, yh::yt ->
        if xh > yh then merge x yt acc@[yh] else merge xt y acc@[xh]
    | [] , _ -> acc@y
    | _, [] -> acc@x*)

    let rec merge b=
        match b with
        | xh::xt, yh::yt -> 
            if xh > yh 
            then yh::merge (fst(b), yt) 
            else xh::merge (xt, snd(b))
        | [] , _ -> snd(b)
        | _, [] -> fst(b)
