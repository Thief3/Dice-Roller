(ns dice-roller.subs
  (:require
   ;[re-frame.core :as re-frame]))
   [re-frame.core :refer [reg-sub reg-sub-raw subscribe]]))

;(re-frame/reg-sub
; ::name
; (fn [db]
;   (:name db)))

(reg-sub
 ::name
 (fn [db]
   (:name db)))

(reg-sub
 ::effects
 (fn [db]
   (:effects db)))
