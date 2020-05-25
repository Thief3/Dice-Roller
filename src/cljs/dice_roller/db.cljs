(ns dice-roller.db)

(def default-db
  {:name "re-frame"
   :effects {1 {(str (gensym 1)) "Kill guppy." (str (gensym 2)) "Kill Issac."}
             2 {(str (gensym 3)) "Cry"}
             3 {(str (gensym 4)) "Wooop Woop."}}
   :activated-effects ()})
