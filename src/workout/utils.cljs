(ns workout.utils
  (:require [reagent.core :as r]))

(defn in? [val coll]
  (some #(= val %) coll))
