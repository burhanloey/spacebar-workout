(ns workout.core
  (:require [reagent.core :as r]
            [workout.timer :as timer]
            [workout.exercises :as exercises]
            [workout.contents :as contents]
            [workout.utils :refer [in?]]))

(enable-console-print!)

(def keycode {:spacebar 32
              :left     37
              :up       38
              :right    39
              :down     40})

(defn handle-event [code]
  (condp = code
    (:spacebar keycode) (exercises/go-with-the-flow)
    (:left keycode)     (exercises/do-rep :prev)
    (:up keycode)       (exercises/do-exercise :prev)
    (:right keycode)    (exercises/do-rep :next)
    (:down keycode)     (exercises/do-exercise :next)))

(defn onkeydown-listener [e]
  (let [code (or (.-which e)
                 (.-keyCode e))]
    (when (in? code (vals keycode))
      (.preventDefault e)
      (handle-event code))))

(set! (.-onkeydown js/document) onkeydown-listener)

(defn workout-app []
  [:div.container-fluid
   [:div.col-md-3
    [timer/timer-component]
    [exercises/exercises-component]]
   [:div.col-md-9
    [:p "Some content should goes here."]]])

(r/render-component [workout-app]
                    (. js/document (getElementById "app")))

;; Figwheel related
(defn on-js-reload []
  )
