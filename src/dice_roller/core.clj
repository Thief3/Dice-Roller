(ns dice-roller.core)

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))

(defn -main
  "I don't do a whole lot...yet."
  [& args]
  (println "Hello, World!"))

(defn roll-dice
  "Rolls x dice with number of sides y."
  [x y]
  (repeatedly x #(rand-int y)))

(defn add-conditional-value
  "Adds a new dice value x, with an effect y, as a hashmap to a list of other hashmaps z"
  [x y z]
  (assoc z x (conj (get z x) y)))
  
(defn effects-activated
  "Checks a list of hashmaps x for values in list y, displaying any other values in their list"
  [x y]
  (println "no"))
