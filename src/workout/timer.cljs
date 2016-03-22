(ns workout.timer
  (:require [reagent.core :as r]
            [workout.utils :refer [in?]]))

(enable-console-print!)

(def alarm (r/atom (js/Audio. "../audio/alarm2.mp3")))

(defonce time-remaining (r/atom 0))
(defonce updater        (r/atom nil))
(defonce button-text    (r/atom "Get Started"))

(defn has-time []
  (pos? @time-remaining))

(defn play-alarm []
  (.play @alarm))

(defn set-text [text]
  (reset! button-text text))

(defn update-text []
  (let [updated-text (if (has-time)
                       "Start"
                       "Next")]
    (set-text updated-text)))

(defn stop-timer []
  (update-text)
  (js/clearInterval @updater)
  (reset! updater nil))

(defn set-timer [seconds]
  (reset! time-remaining seconds)
  (stop-timer))

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

(defn timer []
  (let [time            @time-remaining
        [minute second] [(quot time 60) (rem time 60)]]
    [:p.lead
     (if (zero? minute)
       (str second "s")
       (str minute "m " second "s"))]))

(defn timer-button [doall-fn]
  (let [current-text @button-text]
    [:p
     [:button.btn.btn-primary
      {:on-click doall-fn}
      [:span.glyphicon
       {:class (case current-text
                 "Next"  "glyphicon-forward"
                 "Start" "glyphicon-play"
                 "Pause" "glyphicon-pause"
                 "")}]
      (when (in? current-text ["Next" "Start" "Pause"]) " ") current-text]]))

(defn timer-component [& {:keys [doall-fn back-fn skip-fn]}]
  [:div.panel.panel-default
   [:div.panel-body.text-center
    [timer]
    [timer-button doall-fn]
    [:ul.pager
     [:li.previous [:a {:on-click back-fn} "← Back"]]
     [:li.next [:a {:on-click skip-fn} "Skip →"] ]]]])
