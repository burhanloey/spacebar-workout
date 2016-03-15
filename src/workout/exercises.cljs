(ns workout.exercises
  (:require [reagent.core :as r]
            [clojure.string :as str]
            [workout.timer :as timer]
            [workout.utils :refer [in?]]))

(enable-console-print!)

(def all-exercises {:warmup   {:stretches ["wall extensions"
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
                                           "arch hold"]}
                    :skill    ["handstand"
                               "support"]
                    :strength {:first     ["pullup"
                                           "dipping"]
                               :second    ["squat"
                                           "l-sit"]
                               :third     ["pushup"
                                           "row"]}})

(defonce current-exercise (r/atom nil))
(defonce current-rep      (r/atom 1))

(defn get-all-exercises [& keys]
  (flatten (filter vector? (tree-seq map? vals (get-in all-exercises (remove nil? (apply vector keys)))))))

(defn get-exercises-by-group []
  (filter vector? (tree-seq map? vals all-exercises)))

(defn last-exercise? [name]
  (in? name (map last (get-exercises-by-group))))

(defn first-exercise-from [name]
  (apply first (filter #(in? name %) (get-exercises-by-group))))

(defn total-rep [name]
  (if (in? name (get-all-exercises :strength))
    3
    1))

(defn duration [name]
  (condp #(in? %2 %1) name
    ["l-sit"]                              90 ; 30s L-sit + 60s rest
    (get-all-exercises :warmup :bodyline)  60
    (get-all-exercises :strength)          60
    (get-all-exercises :skill)            300
    0))

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

(defn do-rep [target]
  (case target
    :next (reset! current-rep (inc (mod @current-rep (total-rep @current-exercise))))
    :prev (reset! current-rep (inc (mod (- @current-rep 2) (total-rep @current-exercise))))))

(defn go-with-the-flow []
  (let [next-group (fn []
                     (do-exercise :next)
                     (reset! current-rep 1))
        next-rep   (fn []
                     (let [target-exercise (first-exercise-from @current-exercise)]
                       (reset! current-exercise target-exercise)
                       (do-rep :next)
                       (timer/set-timer (duration target-exercise))))]
    (cond
      (pos? @timer/time-remaining) (timer/handle-click)

      (and (last-exercise? @current-exercise)
           (= @current-rep (total-rep @current-exercise))) (next-group)
      
      (last-exercise? @current-exercise) (next-rep)
      
      :else (do-exercise :next))))

(defn listing-names [stage & [type]]
  [:ul
   (for [exercise (get-all-exercises stage type)]
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
