(ns spacebar-workout.core
  (:require [reagent.core :as r]
            [spacebar-workout.nav :as nav]
            [spacebar-workout.timer :as timer]
            [spacebar-workout.exercises :as exercises]
            [spacebar-workout.contents :as contents]
            [spacebar-workout.utils :refer [in?]]))

(enable-console-print!)

(def keycode {:enter    13
              :spacebar 32
              :left     37
              :up       38
              :right    39
              :down     40})

(defn handle-event [code]
  (condp = code
    (:enter keycode)    (contents/play-video)
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

(defn spacebar-workout-app []
  [:div
   [nav/navbar]
   [:div.container-fluid
    [:div.col-md-3
     {:role "navigation"}
     [timer/timer-component
      :doall-fn #(exercises/go-with-the-flow)
      :back-fn  #(exercises/do-exercise :prev)
      :skip-fn  #(exercises/do-exercise :next)]
     [exercises/exercises-component]]
    [:div.col-md-9
     [contents/content-component]]]
   [nav/footer]])

(r/render-component [spacebar-workout-app]
                    (. js/document (getElementById "app")))

;; Figwheel related
(defn on-js-reload []
  )
