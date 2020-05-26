(ns dice-roller.views
  (:require
   [re-frame.core :as re-frame]
   [dice-roller.subs :as subs]
   [reagent.core :as reagent]
   ))

(defn delete-button 
  [die item-id]
  [:div.delete-button
   {:on-click #(re-frame/dispatch [:delete-effect die item-id])}
   [:i.fas.fa-trash.fa-1x]])

(defn effect-breakdown [effect-list]
  (for [[die vals] effect-list]
    (for [[key effect] vals]
      {:key key :die die :effect effect})))

(defn effects []
  (let [effects (re-frame/subscribe [::subs/effects])]
    [:div#effects
     [:div#effects-header.grid.grid-col-2
      [:h3.effect "Effect"]
      [:h3.die "Die"]]
     (for [effect-list (effect-breakdown @effects)]
       (for [e effect-list]
         [:div#single-effect.grid.grid-cols-3
          {:key (get e :key)}
          [:div.effect (get e :effect)]
          [:div.die (get e :die)]
          [:div (delete-button (get e :die) (get e :key))]]))]))

(defn atom-input [value type id]
  [:input {:type type
           :value @value
           :id id
           :on-change #(reset! value (-> % .-target .-value))}])

(defn add []
  (let [effect (reagent/atom "") die (reagent/atom "")]
    [:div.grid.sm:grid-cols-3.gap-4
     [:div.grid.sm:grid-cols-2
      [:label
       {:for "input-die-for-effect"}
       "Die: "]
      [atom-input die "text" "input-die-for-effect"]]
     [:div.grid.sm:grid-cols-2
      [:label
       {:for "input-effect-to-add"}
       "Effect: "]
      [atom-input effect "text" "input-effect-to-add"]]
     [:button
      {:on-click #(do
                    (re-frame/dispatch [:add-effect @die @effect])
                    (reset! die "")
                    (reset! effect ""))}
      "Add new!"]]))


(defn roll-dice
  "Rolls x dice with number of sides y."
  [x y]
  (map inc (repeatedly x #(rand-int y))))

(defn dice-roll []
  (let [die (reagent/atom 6)
        num-of-rolls (reagent/atom 1)
        dice-rolled (reagent/atom ())
        activated-effects (re-frame/subscribe [::subs/activated-effects])]
    [:div.grid.grid-cols-1
     [:div.grid.sm:grid-cols-3.gap-4
      [:div.grid.sm:grid-cols-2
       [:label
        {:for "input-die-to-roll"}
        "Die: "]
       [atom-input die "number" "input-die-to-roll"]]
      [:div.grid.sm:grid-cols-2
       [:label
        {:for "input-num-die-roll"}
        "Number of rolls: "]
       [atom-input num-of-rolls "number" "input-num-die-roll"]]
      [:button
       {:on-click
        #(do
           (reset! dice-rolled
                   (roll-dice (js/parseInt @num-of-rolls)
                              (js/parseInt @die)))
           (re-frame/dispatch [:get-activated-effects @dice-rolled]))}
       "Roll dice." ]]
     [:p @dice-rolled]
     ]))

(defn activated-effects []
  (let [activated-effects (re-frame/subscribe [::subs/activated-effects])]
    [:div#activated-effects
     (for [[die effects] @activated-effects]
       (for [[k v] effects]
         [:div v]))]))

(defn main-panel [effect-id]
  (let [a-e (re-frame/subscribe [::subs/activated-effects])]
    [:div
     ;; Hiccup complains about classes with a '/' so theres this now.
     {:class "container mx-auto px-4 w-full md:w-1/2 lg:w-1/4"}
     [:h1 "A foursouls idea."]
     (if (not (empty? @a-e))
         [activated-effects])
     [effects]
     [:div
      [add]
      [dice-roll]]]))
