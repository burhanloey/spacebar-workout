(ns workout.timer
  (:require [reagent.core :as r]))

(enable-console-print!)

(defonce time-remaining (r/atom 0))
(defonce updater        (r/atom nil))
(defonce button-text    (r/atom "Start"))

(defn stop-timer []
  (js/clearInterval @updater)
  (reset! button-text "Start")
  (reset! updater nil))

(defn set-timer [seconds]
  (stop-timer)
  (reset! time-remaining seconds))

(defn start-timer []
  (when (pos? @time-remaining)
    (reset! button-text "Pause")
    (reset! updater (js/setInterval
                     (fn []
                       (swap! time-remaining dec)
                       (when-not (pos? @time-remaining)
                         (stop-timer)))
                     1000))))

(defn handle-click []
  (if @updater ; is ticking
    (stop-timer)
    (start-timer)))

(defn timer-button []
  [:button @button-text])

(defn timer-component []
  [:div
   [:p "Timer: " @time-remaining]
   [:p [timer-button]]])
