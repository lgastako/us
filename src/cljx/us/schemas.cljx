(ns us.schemas
  (:require [schema.core :as s]))

(def Name
  (s/either
   s/Str
   s/Keyword
   s/Symbol))

(def Url s/Str)

(def DatomicUrl Url)

(def LocalDatabase
  {:datomic-url DatomicUrl})

(def LinkedDatabase
  {:url Url})

(def OurState
  {:dbs {:local {Name LocalDatabase}
         :linked {Name LinkedDatabase}}})

(def our-initial-state {})

(def validate-our-state (partial s/validate OurState))

(def make-our-state
  (let [our-state (atom s/our-initial-state)]
    (set-validator! our-state s/validate-our-state)
    our-state))
