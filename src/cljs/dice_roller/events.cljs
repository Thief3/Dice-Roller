(ns dice-roller.events
  (:require
   [re-frame.core :as re-frame]
   [dice-roller.db :as db]
   [cljs.spec.alpha :as s]
   [expound.alpha :as expound]))

(defn validate
  "Returns nil if the db conforms to the spec, throws an exception otherwise"
  [spec db]
  (when goog.DEBUG
    (when-let [error (s/explain-data spec db)]
      (throw (ex-info 
               (str "DB spec validation failed: " 
                    (expound/expound-str spec db)) 
               error)))))

;; Not sure if this is right.
;(def validate-interceptor (re-frame/after (partial validate db)))

(re-frame/reg-event-db
 ::initialize-db
 ;[validate-interceptor]
 (fn [_ _]
   db/default-db))

(re-frame/reg-event-db
 :add-effect
 ;[validate-interceptor]
 (fn [db [_ die effect]]
   (update-in db [:effects] concat (list (db/Effect. die (str (gensym)) effect)))))

(re-frame/reg-event-db
 :delete-effect
 ;[validate-interceptor]
 (fn [db [_ die id]]
   
   (assoc-in
    db
    [:effects]
    (filter #(not (= (:key %) (str id))) (get db :effects)))))

(re-frame/reg-event-db
 :get-activated-effects
 ;[validate-interceptor]
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
                            (if (= (:die effect) die)
                              effect))))))))))))

(re-frame/reg-event-db
 :last-dice-rolled
 ;;[validate-interceptor]
 (fn [db [_ dice-rolled]]
   (assoc-in
    db
    [:last-dice-rolled]
    dice-rolled)))
