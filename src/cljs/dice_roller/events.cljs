(ns dice-roller.events
  (:require
   [re-frame.core :as rf]
   [dice-roller.db :as db]
   ))

(rf/reg-event-db
 ::initialize-db
 (fn [_ _]
   db/default-db))

(defn add-conditional-value
  "Adds a new dice value x, with an effect y, as a hashmap to a list of other hashmaps z."
  [x y z]
  (assoc z x (assoc (get z x) (gensym) y)))

(rf/reg-event-db
 ;; usage (rf/dispatch [:add-effect die effect])
 :add-effect
 (fn [db [die effect]]
   (add-conditional-value die effect db)))
