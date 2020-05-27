(ns dice-roller.events
  (:require
   [re-frame.core :as re-frame]
   [dice-roller.db :as db]
   ))

(re-frame/reg-event-db
 ::initialize-db
 (fn [_ _]
   db/default-db))

(re-frame/reg-event-db
 :add-effect
 (fn [db [_ die effect]]
   (update-in db [:effects]
              concat (list {:die die
                            :key (str (gensym))
                            :effect effect}))))

(re-frame/reg-event-db
  :delete-effect
  (fn [db [_ die id]]
    (assoc-in
     db
     [:effects]
     (filter #(not (= (get % :key) (str id))) (get db :effects)))))

(re-frame/reg-event-db
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
