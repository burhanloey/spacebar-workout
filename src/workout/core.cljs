(ns workout.core
  (:require [reagent.core :as r]
            [workout.nav :as nav]
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

(r/render-component [workout-app]
                    (. js/document (getElementById "app")))

;; Figwheel related
(defn on-js-reload []
  )
