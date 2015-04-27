(ns us.app
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [om-tools.core :refer-macros [defcomponentk]]
            [its.log :as log]
            [sablono.core :refer-macros [html]]))

(log/set-level! :debug)
(log/debug :us.app :begin)

(defonce app-state (atom {:event-stream [{:event-type :msg
                                          :msg "Hello."
                                          :from "usbot"}
                                         {:event-type :msg
                                          :msg "What's up?"
                                          :from "john"}]}))

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

(defcomponentk msg-event-view [[:data msg from]]
  (render [_] (html [:div.event.msg [:div.from from] msg])))

(defcomponentk missing-event-view [[:data type]]
  (render [_] (html [:div.event.missing
                     (str "Missing view for event type '" type "'.")])))

(defcomponentk event-view [[:data event-type :as event]]
  (render [_] (let [sub-view (case event-type
                               :msg msg-event-view
                               missing-event-view)]
                (html [:li (om/build sub-view event)]))))

(defcomponentk main-view [[:data event-stream :as data]]
  (render [_] (html [:div.main
                     [:h1 "usbot"]
                     [:div.event-stream
                      [:ul (om/build-all event-view event-stream)]]])))

(defn init []
  (->> "container"
       js/document.getElementById
       (conj [] :target)
       (conj [])
       (into {})
       (om/root main-view app-state)))

(log/debug :us.app :end)
