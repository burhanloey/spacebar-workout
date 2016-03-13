(ns workout.core
  (:require [reagent.core :as r]
            [workout.timer :as timer]
            [workout.exercises :as exercises]
            [workout.contents :as contents]
            [workout.utils :refer [in?]]))

(enable-console-print!)

(defonce dingdong (r/atom "Hello Dingdong!"))

(defn workout-app []
  [:div
   [:p @dingdong]
   [timer/timer-component]
   [exercises/exercises-component]])

(r/render-component [workout-app]
                    (. js/document (getElementById "app")))

(defn handle-event [code]
  (case code
    32 (do
         (reset! dingdong "Spacebar")
         (timer/handle-click))
    37 (reset! dingdong "Left")
    38 (do
         (reset! dingdong "Up")
         (exercises/do-exercise :prev))
    39 (reset! dingdong "Right")
    40 (do
         (reset! dingdong "Down")
         (exercises/do-exercise :next))))

(defn test-event [e]
  (let [code (or (.-which e)
                 (.-keyCode e))]
    (when (in? code [32 37 38 39 40])
      (.preventDefault e)
      (handle-event code))))

(set! (.-onkeydown js/document) test-event)

;; Figwheel related
(defn on-js-reload []
  )
