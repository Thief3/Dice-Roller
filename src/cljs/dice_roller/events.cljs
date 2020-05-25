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
  (assoc z x (assoc (get z x) (str (gensym)) y)))

(rf/reg-event-db
 :add-effect
 (fn [db [_ die effect]]
   (js/console.log die)
   (if (not (contains? db die))
     (assoc-in db [:effects die] nil))
   (assoc-in db [:effects die (str (gensym))] effect)))

(rf/reg-event-db
 :change-effect
 (fn [db [_ die effect]]
   (update-in db [die] assoc [:effects effect])))

(rf/reg-event-db
  :delete-effect
  (fn [db [_ die id]]
    (update-in
     db
     [:effects die]
     dissoc
     (str id))))

(rf/reg-event-db
 :get-activated-effects
 (fn [db [_ dice-rolled]]
   (assoc-in db [:activated-effects] (select-keys (get db :effects) (vec dice-rolled)))))
