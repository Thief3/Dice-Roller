(ns dice-roller.db)

(def default-db
  {:name "re-frame"
   :effects (list
             {:die 1 :key (str (gensym 1)) :effect "Kill guppy."}
             {:die 1 :key (str (gensym 2)) :effect "Kill Issac."}
             {:die 2 :key (str (gensym 3)) :effect "Cry"}
             {:die 3 :key (str (gensym 4)) :effect "Wooop Woop."})
   :activated-effects ()})
