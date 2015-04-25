(ns us.core
  (:require [dommy.core :refer [listen! unlisten!] :refer-macros [sel1]]
            [its.log :as log]
            [om.core :as om :include-macros true]
            [om-tools.dom :as dom :include-macros true]
            [om-tools.core :refer-macros [defcomponent]]
            [sablono.core :as html :refer-macros [html]]
            [us.browser :as browser]
            [us.schemas :refer [make-our-state]]))

(defonce our-state (make-our-state))

(browser/sync our-state)

(defcomponentk main-view [data owner]
  (render [_] (html [:div#main "us"])))

(defn main []
  (om/root main-view app-state {:target (sel1 :#app)}))
