(ns dice-roller.views
  (:require
   [re-frame.core :as re-frame]
   [dice-roller.subs :as subs]
   ))

;(defn effect []
;  (let [effect (re-frame/subscribe [::subs/effects])]
;    [:p (get @effect 1)]))

(defn main-panel [effect-id]
  (let [effects (re-frame/subscribe [::subs/effects]) name (re-frame/subscribe [::subs/name])]
    [:div
     [:h1 "Hello from " @name]
     [:p  (get @effects effect-id)]
     ]))
