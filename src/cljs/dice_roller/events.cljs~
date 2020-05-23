(ns dice-roller.events
  (:require
   [re-frame.core :as re-frame]
   [dice-roller.db :as db]
   ))

(re-frame/reg-event-db
 ::initialize-db
 (fn [_ _]
   db/default-db))
