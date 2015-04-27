(ns asp.browser
  (:require [dommy.core :refer [listen! unlisten!] :refer-macros [sel1]]
            [its.log :as log]))

(def ^:dynamic *storage* js/localStorage)

(def spit str)

(defn get-item [k]
  (log/debug :get-item {:k k})
  (.getItem *storage* (spit k)))

(defn set-item! [k v]
  (.setItem *storage* (spit k) (spit v))
  [k v])

(defn remove-item! [k]
  (.removeItem *storage* (spit k)))

(defn clear []
  (.clear *storage*))

;; (defn restore []
;;   (-> :saved-game
;;       get-item
;;       #(when %
;;          (->> %
;;               spit
;;               (reset! *storage*)))))

(defn auto-save [state-atom]
  (add-watch state-atom
             :saved-game
             (fn [_ _ o n]
               (when-not (= o n)
                 (set-item! :saved-game (str n))))))

;; (defn auto-load [state-atom]
;;   (listen! js/window :storage
;;            (fn [e]
;;              (-> e
;;                  .-newValue
;;                  cljs.reader/read-string
;;                  #(reset! state-atom %)))))

(defn sync [state-atom]
  (restore state-atom)
  (auto-save state-atom)
  (auto-load state-atom))
