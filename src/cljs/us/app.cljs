(ns us.app
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [om-tools.core :refer-macros [defcomponentk]]
            [its.log :as log]
            [sablono.core :refer-macros [html]]))

(log/set-level! :debug)
(log/debug :us.app :begin)

(defonce app-state (atom {:text "Hello."}))

(def prevent-default #(.preventDefault %))

(defn only [f]
  (fn [& args]
    (try
      (apply f args)
      (finally
        (try
          (prevent-default (first args))
          (catch js/Error ex))
        nil))))

(defcomponentk main-view [[:data text :as data]]
  (render [_]
          (log/debug :main-view/render)
          (html
           [:div.main
            [:h1
             {:on-click (only #(om/update! data :text "Welcome."))}
             text]])))

(defn init []
  (log/debug :us.app/init)
  (->> "container"
       js/document.getElementById
       (conj [] :target)
       (conj [])
       (into {})
       (om/root main-view app-state)))

(log/debug :us.app :end)
