(ns workout.exercises
  (:require [reagent.core :as r]
            [clojure.string :as str]
            [workout.timer :as timer]
            [workout.utils :refer [in?]]))

(enable-console-print!)

(def all-exercises {:warmup   {:rep 1
                               :exercises {:stretches ["wall extensions"
                                                       "band dislocates"
                                                       "cat-camels" 
                                                       "scapular shrugs"
                                                       "full body circles"
                                                       "front and side leg swings"
                                                       "wrist mobility"]
                                           :bodyline  ["plank"
                                                       "side plank - right"
                                                       "side plank - left"
                                                       "reverse plank"
                                                       "hollow hold"
                                                       "arch hold"]}}
                    :skill    {:rep 1
                               :exercises ["handstand"
                                           "support"]}
                    :strength {:rep 3
                               :exercises {:first     ["pullup"
                                                       "dipping"]
                                           :second    ["squat"
                                                       "l-sit"]
                                           :third     ["pushup"
                                                       "row"]}}})

(defonce current-exercise (r/atom nil))
(defonce current-rep      (r/atom 0))

(defn get-all-exercises [& keys]
  (flatten (filter vector? (tree-seq map? vals (get-in all-exercises (remove nil? (apply vector keys)))))))

(defn total-rep [name]
  (cond
    (in? name (get-all-exercises :warmup))   (get-in all-exercises [:warmup :rep])
    (in? name (get-all-exercises :skill))    (get-in all-exercises [:skill :rep])
    (in? name (get-all-exercises :strength)) (get-in all-exercises [:strength :rep])
    :else 0))

(defn duration [name]
  (cond
    (in? name (get-all-exercises :warmup :exercises :bodyline)) 60
    (in? name (get-all-exercises :strength)) 60
    (in? name (get-all-exercises :skill)) 300
    :else 0))

(defn do-exercise [target]
  (let [find-target-while (case target
                            :next (comp fnext drop-while)
                            :prev (comp last take-while))
        target-exercise   (if-not (nil? @current-exercise)
                            (find-target-while #(not= % @current-exercise) (get-all-exercises))
                            (case target
                              :next (first (get-all-exercises))
                              :prev (last (get-all-exercises))))]
    (reset! current-exercise target-exercise)
    (timer/set-timer (duration target-exercise))))

(defn listing-names [stage & [type]]
  [:ul
   (for [exercise (get-all-exercises stage :exercises type)]
     ^{:key exercise} [:li (str/capitalize (name exercise))])])

(defn exercises-list [stage & types]
  [:div
   [:h2 (str/capitalize (name stage))]
   (if-not types
     [:p
      [listing-names stage]]
     (for [type types]
       ^{:key type} [:p
                     [:h3 (str/capitalize (name type))]
                     [listing-names stage type]]))])

(defn exercises-component []
  [:div
   [:p "Current exercise: " @current-exercise]
   [:p "Current rep: " @current-rep]
   [exercises-list :warmup :stretches :bodyline]
   [exercises-list :skill]
   [exercises-list :strength :first :second :third]])
