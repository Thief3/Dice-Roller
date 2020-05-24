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

;(rf/reg-event-db
; :delete-effect
; (fn [db [_ die item-id]]
  ;{:keys [db]} [die item-id]    ;; <--- new: obtain db and item-id directly
;   (update-in db [die] dissoc [:effects item-id])))

(rf/reg-event-db
 :change-effect
 (fn [db [_ die effect]]
   (update-in db [die] assoc [:effects effect])))

;(def effect-interceptors [;check-spec-interceptor    ;; ensure the spec is still valid  (after)
;                        (path :todos)             ;; the 1st param given to handler will be the value from this path within db
;                        ->local-store])
(rf/reg-event-db
  :delete-effect
  (fn [effects [_ die id]]
    (update-in
     db
     [:effects]
     dissoc (update-in
             (get db :effects)
             [die]
             dissoc id))))

;(update-in db [:effects] dissoc (update-in (get db :effects) [die] dissoc :key))
