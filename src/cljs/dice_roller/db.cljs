(ns dice-roller.db)

(def default-db
  {:name "re-frame"
   :effects {1 {(str (gensym 1)) "Kill guppy." (str (gensym 2)) "Kill Issac."}}})
