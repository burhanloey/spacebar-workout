(ns workout.contents
  (:require [reagent.core :as r]
            [workout.exercises :as exercises]))

(def content-data {"Wall extensions"           {:link "d6V2Exzb324"}
                   "Band dislocates"           {:link "WyW5jGGxoZk"}
                   "Cat-camels"                {:link "K9bK0BwKFjs"}
                   "Scapular shrugs"           {:link "akgQbxhrhOc"}
                   "Full body circles"         {:link "yRigKuEou7k"}
                   "Front and side leg swings" {:link "4aoUZEZFJF8"}
                   "Wrist mobility"            {:link "mSZWSQSSEjE"}
                   "Plank"                     {:desc "60s hold"
                                                :link "44ScXWFaVBs"}
                   "Side plank - right"        {:desc "60s hold"
                                                :link "44ScXWFaVBs"}
                   "Side plank - left"         {:desc "60s hold"
                                                :link "44ScXWFaVBs"}
                   "Reverse plank"             {:desc "60s hold"
                                                :link "44ScXWFaVBs"}
                   "Hollow hold"               {:desc "60s hold"
                                                :link "44ScXWFaVBs"}
                   "Arch hold"                 {:desc "60s hold"
                                                :link "44ScXWFaVBs"}
                   "Handstand"                 {:desc "5 minutes including rest"
                                                :link "N3K9SMNKL7Y"}
                   "Support"                   {:desc "3 minutes including rest"
                                                :link "8gmyhBScTLk"}
                   "Rest from skill work"      {:desc "60s"}
                   "Pullup"                    {:desc "3x5-8 pullup + 60s rest"
                                                :link "RsnbDcsZbpk"}
                   "Dipping"                   {:desc "3x5-8 dip + 60s rest"
                                                :link "ddEeZY_ii2c"}
                   "Squat"                     {:desc "3x5-8 squat + 60s rest"
                                                :link "487aR3A7HvM"}
                   "L-sit"                     {:desc "60s hold"
                                                :link "IUZJoSP66HI"}
                   "Rest from L-sit"           {:desc "60s"}
                   "Pushup"                    {:desc "3x5-8 pushup + 60s rest"
                                                :link "4dF1DOWzf20"}
                   "Row"                       {:desc "3x5-8 row + 60s rest"
                                                :link "dvkIaarnf0g"}})

(defn youtube [now]
  (let [id (get-in content-data [now :link])]
    (when-not (nil? id)
      [:div.embed-responsive.embed-responsive-16by9
       [:iframe.embed-responsive-item
        {:src (str "https://www.youtube.com/embed/" id)}]])))

(defn content-component []
  (let [now @exercises/current-exercise]
    [:div.text-center
     [:h1 now]
     [:h2 (get-in content-data [now :desc])]
     [youtube now]]))
