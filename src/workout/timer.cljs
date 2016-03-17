(ns workout.timer
  (:require [reagent.core :as r]))

(enable-console-print!)

(def alarm (r/atom (js/Audio. "../audio/alarm2.mp3")))

(defonce time-remaining (r/atom 0))
(defonce updater        (r/atom nil))
(defonce button-text    (r/atom "Start"))

(defn play-alarm []
  (.play @alarm))

(defn stop-timer []
  (reset! button-text "Start")
  (js/clearInterval @updater)
  (reset! updater nil))

(defn set-timer [seconds]
  (stop-timer)
  (reset! time-remaining seconds))

(defn has-time []
  (pos? @time-remaining))

(defn start-timer [& {on-finished :on-finished}]
  (when (pos? @time-remaining)
    (reset! button-text "Pause")
    (reset! updater (js/setInterval
                     (fn []
                       (swap! time-remaining dec)
                       (when-not (pos? @time-remaining)
                         (stop-timer)
                         (play-alarm)
                         (on-finished)))
                     1000))))

(defn handle-click [& {on-finished :on-finished}]
  (if @updater ; is ticking
    (stop-timer)
    (start-timer :on-finished on-finished)))

(defn timer-button []
  (let [current-text @button-text]
    [:button.btn.btn-primary
     [:span.glyphicon
      {:class (if (= current-text "Start")
                "glyphicon-play"
                "glyphicon-pause")}]
     " " current-text]))

(defn timer-component []
  [:div.panel.panel-default
   [:div.panel-body.text-center
    [:p @time-remaining]
    [:p [timer-button]]]])
