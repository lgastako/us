(ns us.styles
  (:require [garden.def :refer [defrule defstyles]]
            [garden.stylesheet :refer [rule]]))

(defstyles screen
  [:body {:font-family "Helvetica Neue"
          :font-size   "16px"
          :line-height 1.5}]

  [:.event-stream [:li {:list-style-type "none"}]]

  [:.event.msg [:.from {:font-weight "bold"
                        :margin-right "1em"
                        :float "left"
                        :text-align "right"
                        :width "10em"}]])
