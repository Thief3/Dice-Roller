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
   (update-in db [:effects]
              concat (list {:die die
                            :key (str (gensym))
                            :effect effect}))))

(rf/reg-event-db
 :change-effect
 (fn [db [_ die effect]]
   (update-in db [die] assoc [:effects effect])))

(rf/reg-event-db
  :delete-effect
  (fn [db [_ die id]]
    (assoc-in
     db
     [:effects]
     (filter #(not (= (get % :key) (str id))) (get db :effects)))))

(rf/reg-event-db
 :get-activated-effects
 (fn [db [_ dice-rolled]]
   (assoc-in
    db
    [:activated-effects]
    (distinct
     (flatten
      (remove nil?
              (flatten
               (list
                (for [effect (get db :effects)]
                  (list (for [die dice-rolled]
                          (if (= (get effect :die) die)
                            effect))))))))))))
