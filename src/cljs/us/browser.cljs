(ns asp.browser
  (:require [dommy.core :refer [listen! unlisten!] :refer-macros [sel1]]))

(def ^:dynamic *storage* js/localStorage)

(def get-item [k]
  (.getItem *storage* k))

(def set-item! [k v]
  (.setItem *storage* k v)
  [k v])

(def remove-item! [k]
  (.removeItem *storage* k v))

(def clear []
  (.clear *storage*))

(defn restore [state-atom]
  (-> :saved-game
      get-item
      #(when %
         (reset! *storage* %))))

(defn auto-save [state-atom]
  (add-watch state-atom
             (fn [_ _ o n]
               (when-not (= o n)
                 (set-item! :saved-game (str n))))))

(defn auto-load [state-atom]
  (listen! js/window :storage
           (fn [e]
             (-> e
                 .-newValue
                 cljs.reader/read-string
                 #(reset! state-atom %)))))

(defn sync [state-atom]
  (restore state-atom)
  (auto-save state-atom)
  (auto-load state-atom))
