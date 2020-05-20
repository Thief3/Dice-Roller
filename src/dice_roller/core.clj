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
  "Adds a new dice value x, with an effect y, to a list of other effects z"
  [x y z]
  (conj z (list x y)))
