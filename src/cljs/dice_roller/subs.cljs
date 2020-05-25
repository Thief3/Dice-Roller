(ns dice-roller.subs
  (:require
   [re-frame.core :refer [reg-sub reg-sub-raw subscribe]]))

(reg-sub
 ::name
 (fn [db]
   (:name db)))

(reg-sub
 ::effects
 (fn [db]
   (:effects db)))

(reg-sub
 ::activated-effects
 (fn [db]
   (:activated-effects db)))
