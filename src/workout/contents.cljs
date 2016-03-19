(ns workout.contents
  (:require [reagent.core :as r]
            [workout.exercises :as exercises]))

(def content-data {"Wall extensions"           {:youtube "d6V2Exzb324"}
                   "Band dislocates"           {:youtube "WyW5jGGxoZk"}
                   "Cat-camels"                {:youtube "K9bK0BwKFjs"}
                   "Scapular shrugs"           {:youtube "akgQbxhrhOc"}
                   "Full body circles"         {:youtube "yRigKuEou7k"}
                   "Front and side leg swings" {:youtube "4aoUZEZFJF8"}
                   "Wrist mobility"            {:youtube "mSZWSQSSEjE"}
                   "Plank"                     {:desc "60s hold"
                                                :youtube "44ScXWFaVBs"}
                   "Side plank - right"        {:desc "60s hold"
                                                :youtube "44ScXWFaVBs"}
                   "Side plank - left"         {:desc "60s hold"
                                                :youtube "44ScXWFaVBs"}
                   "Reverse plank"             {:desc "60s hold"
                                                :youtube "44ScXWFaVBs"}
                   "Hollow hold"               {:desc "60s hold"
                                                :youtube "44ScXWFaVBs"}
                   "Arch hold"                 {:desc "60s hold"
                                                :youtube "44ScXWFaVBs"}
                   "Handstand"                 {:desc "5 minutes including rest"
                                                :youtube "N3K9SMNKL7Y"}
                   "Support"                   {:desc "3 minutes including rest"
                                                :youtube "8gmyhBScTLk"}
                   "Rest from skill work"      {:desc "60s"}
                   "Pullup"                    {:desc "3x5-8 pullup + 60s rest"
                                                :youtube "RsnbDcsZbpk"}
                   "Dipping"                   {:desc "3x5-8 dip + 60s rest"
                                                :youtube "ddEeZY_ii2c"}
                   "Squat"                     {:desc "3x5-8 squat + 60s rest"
                                                :youtube "487aR3A7HvM"}
                   "L-sit"                     {:desc "60s hold"
                                                :youtube "IUZJoSP66HI"}
                   "Rest from L-sit"           {:desc "60s"}
                   "Pushup"                    {:desc "3x5-8 pushup + 60s rest"
                                                :youtube "4dF1DOWzf20"}
                   "Row"                       {:desc "3x5-8 row + 60s rest"
                                                :youtube "dvkIaarnf0g"}})

(defn youtube [now]
  (let [id (get-in content-data [now :youtube])]
    (when-not (nil? id)
      [:div.col-md-offset-1.col-md-10
       [:div.embed-responsive.embed-responsive-16by9
        [:iframe.embed-responsive-item
         {:src (str "https://www.youtube.com/embed/" id)}]]])))

(defn contents [now]
  [:div.text-center
     [:h1 now]
     [:h2 (get-in content-data [now :desc])]
     [youtube now]])

(defn instructions []
  [:div.jumbotron
   [:h1.text-center "Instructions"]
   [:p.text-center
    "This workout routine is meant to be done 3 times a week, with at least one rest day in between workout days. If you are new to bodyweight fitness, please read the progressions before attempting any of the exercise."]
   [:h2.text-center "Controls:"]
   [:p.text-center
    [:button.btn.btn-primary "Spacebar"]
    " Pretty much do everything"]
   [:p.col-md-offset-4
    [:button.btn.btn-primary [:span.glyphicon.glyphicon-arrow-up]]
    " Go to previous exercise"]
   [:p.col-md-offset-4
    [:button.btn.btn-primary [:span.glyphicon.glyphicon-arrow-down]]
    " Go to next exercise"]
   [:p.col-md-offset-4
    [:button.btn.btn-primary [:span.glyphicon.glyphicon-arrow-left]]
    " Go to previous rep"]
   [:p.col-md-offset-4
    [:button.btn.btn-primary [:span.glyphicon.glyphicon-arrow-right]]
    " Go to next rep"]])

(defn content-component []
  (let [now @exercises/current-exercise]
    (if (nil? now)
      [instructions]
      [contents now])))
