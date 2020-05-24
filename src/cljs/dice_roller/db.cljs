(ns dice-roller.db)

(def default-db
  {:name "re-frame"
  :effects {1 {(gensym 1) "Kill guppy." (gensym 2) "Kill Issac."}}})
