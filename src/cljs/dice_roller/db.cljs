(ns dice-roller.db
  (:require [cljs.spec.alpha :as s]))

(defrecord Effect [die key effect])

(def default-db
  {:name "re-frame"
   :effects (list
             (Effect. 1 (str (gensym 1)) "Kill guppy.")
             (Effect. 1 (str (gensym 2)) "Pick up a penny."))
   :activated-effects ()})

;;(s/def :dice-roller.db/default-db/name string?)
