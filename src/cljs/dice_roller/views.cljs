(ns dice-roller.views
  (:require
   [re-frame.core :as re-frame]
   [dice-roller.subs :as subs]
   [reagent.core :as reagent]
   ))

(defn delete-button 
  [die item-id]
  [:button
   {:on-click #(re-frame/dispatch [:delete-effect die item-id])}
   die " " item-id])

(defn effect-breakdown [effect-list]
  (for [[die vals] effect-list]
    (for [[key effect] vals]
      {:key key :die die :effect effect})))

(defn effects []
  (let [effects (re-frame/subscribe [::subs/effects])]
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

(defn atom-input [value]
  [:input {:type "text"
           :value @value
           :on-change #(reset! value (-> % .-target .-value))}])

(defn add []
  (let [effect (reagent/atom "") die (reagent/atom "")]
    (fn [_]
      [:div
       [:label "Die: "]
       [atom-input die]
       [:label "Effect: "]
       [atom-input effect]
       [:button
        {:on-click #(do
                     (re-frame/dispatch [:add-effect @die @effect])
                     (reset! die "")
                     (reset! effect ""))}
        "Add new!"]])))
  
(defn main-panel [effect-id]
  (let [ name (re-frame/subscribe [::subs/name])]
    [:div
     [:h1 "Hello from " @name]
     [effects]
     [add]
     ]))
