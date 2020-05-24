(ns dice-roller.views
  (:require
   [re-frame.core :as re-frame]
   [dice-roller.subs :as subs]
   ))

(defn delete-button 
  [die item-id]
  [:button
   {:on-click #(re-frame/dispatch [:delete-effect die item-id])}
   die " " item-id])
                                        ;#(re-frame.core/dispatch [:delete-effect die item-id])}])

(defn effect-breakdown [effect-list]
  (for [[die vals] effect-list]
    (for [[key effect] vals]
      {:key key :die die :effect effect})))

(defn effects []
  (let [effects (re-frame/subscribe [::subs/effects])]
    ;;[:p @db]
    [:ul
     (for [effect-list (effect-breakdown @effects)]
       (for [e effect-list]
         [:li
          {:key (get e :key)}
          (str
           "Key: " (get e :key) ", "
           "Die: " (get e :die) ", "
           "Effect: " (get e :effect))
          (delete-button (get e :die) (get e :key))]))]))

(defn main-panel [effect-id]
  (let [ name (re-frame/subscribe [::subs/name])]
    [:div
     [:h1 "Hello from " @name]
     [effects]
     ]))
