(ns us.schemas
  (:require [schema.core :as s]))

(def Name
  (s/either s/Str
            s/Keyword
            s/Symbol))

(def Url s/Str)

(def DatomicUrl Url)

(def LocalDatabase
  {:datomic-url DatomicUrl})

(def LinkedDatabase
  {:url Url
   (s/optional-key :url-type) (s/maybe Name)})

(def OurState
  {:dbs {:local {Name LocalDatabase}
         :linked {Name LinkedDatabase}}})

(def our-initial-state {:dbs {:local {}
                              :linked {}}})

(def validate-our-state (partial s/validate OurState))

(def make-our-state
  (let [our-state (atom our-initial-state)]
    (set-validator! our-state validate-our-state)
    our-state))
