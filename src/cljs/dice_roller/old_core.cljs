(ns dice-roller.core
  ;(:gen-class :main true)
  (:require
   [reagent.core :as reagent]
   [re-frame.db :as db]
   [re-frame.core :as rf]))

(use '[clojure.string :only (join)])

(rf/reg-event-db
 :initialize
 (fn [_ _]
   {:effects {}})) 

(defn roll-dice
  "Rolls x dice with number of sides y."
  [x y]
  (map inc (repeatedly x #(rand-int y))))

(defn add-conditional-value
  "Adds a new dice value x, with an effect y, as a hashmap to a list of other hashmaps z"
  [x y z]
  (assoc z x (conj (get z x) y)))
  
(defn effects-activated
  "Checks a list of hashmaps x for values in list y, displaying any other values in their list"
  [x y]
  (flatten
   (vals
    (select-keys x y))))
<<<<<<< HEAD:src/cljs/dice_roller/old_core.cljs


=======
>>>>>>> parent of bceb109... Changed so we can remove individual effects.:src/dice_roller/core.clj

(defn user-input
  "The main loop for the app."
  [effects-map]
  (println "Press 1 to add an effect and 2 to roll a new set of dice.")
  (let [choice (str (read-line))]
    (case choice
        "1" (do
            (println "Which dice value triggers the effect?")
            (let [val (Integer/parseInt (read-line))]
              (println "And whats the effect?")
              (let [effect (str (read-line))]
                (println "Thanks for that. We've updated your effects list.")
                (user-input (add-conditional-value val effect effects-map)))))
        "2" (do
            (println "What dice do you want to roll?")
            (let [die (Integer/parseInt (read-line))]
              (println "And how many?")
              (let [num (Integer/parseInt (read-line))]
                (let [rolls (roll-dice num die)]
                  (println (str "You have rolled: " (join ", " rolls)))
                  (if (empty? (effects-activated effects-map rolls))
                    ; Is empty
                    (do
                      (println "No effects were triggered.")
                      (user-input effects-map))
                    ; Has elements
                    (do
                      (println "The effects triggered are:")
                      (apply println (effects-activated effects-map rolls))
                      (user-input effects-map)))))))
        (println "Thank you and goodbye.")
        )))


(defn -main
  "I don't do a whole lot...yet."
  [& args]
  (user-input {}))
