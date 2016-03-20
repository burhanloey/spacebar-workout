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
                   "Pullup"                    {:desc "3x5-8 pullups + 60s rest"
                                                :youtube "RsnbDcsZbpk"}
                   "Dipping"                   {:desc "3x5-8 dips + 60s rest"
                                                :youtube "ddEeZY_ii2c"}
                   "Squat"                     {:desc "3x5-8 squats + 60s rest"
                                                :youtube "487aR3A7HvM"}
                   "L-sit"                     {:desc "60s hold"
                                                :youtube "IUZJoSP66HI"}
                   "Rest from L-sit"           {:desc "60s"}
                   "Pushup"                    {:desc "3x5-8 pushups + 60s rest"
                                                :youtube "4dF1DOWzf20"}
                   "Row"                       {:desc "3x5-8 rows + 60s rest"
                                                :youtube "dvkIaarnf0g"}})

(defonce current-content (r/atom :contents))

(defn instructions []
  (let [control (fn [icon desc]
                  [:p.col-md-offset-4
                   [:button.btn.btn-primary [:span.glyphicon {:class icon}]]
                   " " desc])]
    [:div.jumbotron
     [:h1.text-center "How to use this website?"]
     [:p.text-center
      "Just hit spacebar to go through the routine. The spacebar will also start the timer. Use the arrow keys if you need to skip some exercise."]
     [:h2.text-center "Controls:"]
     [:p.text-center [:button.btn.btn-primary "Spacebar"] " Pretty much do everything"]
     [control "glyphicon-arrow-up"    "Go to previous exercise"]
     [control "glyphicon-arrow-down"  "Go to next exercise"]
     [control "glyphicon-arrow-left"  "Go to previous rep"]
     [control "glyphicon-arrow-right" "Go to next rep"]]))

(defn progressions []
  (let [url "https://www.reddit.com/r/bodyweightfitness/wiki/exercises/"]
    [:div.jumbotron.text-center
     [:h1 "Progressions"]
     [:p [:a {:href (str url "handstand")} "Handstand progression"]]
     [:p [:a {:href (str url "support")}   "Support practice progression"]]
     [:p [:a {:href (str url "pullup")}    "Pullup progression"]]
     [:p [:a {:href (str url "dip")}       "Dipping progression"]]
     [:p [:a {:href (str url "squat")}     "Squat progression"]]
     [:p [:a {:href (str url "l-sit")}     "L-sit progression"]]
     [:p [:a {:href (str url "pushup")}    "Pushup progression"]]
     [:p [:a {:href (str url "row")}       "Row progression"]]]))

(defn resources []
  [:div.jumbotron.text-center
   [:h1 "Resources"]
   [:p
    [:a {:href "https://www.reddit.com/r/bodyweightfitness/wiki/kb/recommended_routine"} "Recommended Routine"]
    " - Reddit's r/bodyweightfitness recommended routine"]
   [:h2 "Alternatives"]
   [:p [:a {:href "https://fitloop.co/"} "Fitloop"]
    " - Website with similar goals"]
   [:p [:a {:href "http://www.timer-tab.com/"} "Timer Tab"]
    " - Online timer"]])

(defn about []
  [:div.jumbotron
   [:h1.text-center "About"]
   [:p
    "This is a website, duh!"]])

(defn privacy []
  [:h1.text-center "Privacy Policy"])

(defn youtube [title]
  (let [id (get-in content-data [title :youtube])]
    (when-not (nil? id)
      [:div.col-md-offset-1.col-md-10
       [:div.embed-responsive.embed-responsive-16by9
        [:iframe.embed-responsive-item
         {:src (str "https://www.youtube.com/embed/" id)}]]])))

(defn exercise-info [title]
  [:div.text-center
     [:h1 title " " [exercises/rep]]
     [:h2 (get-in content-data [title :desc])]
     [youtube title]])

(defn contents [title]
  (if (nil? title)
    [instructions]
    [exercise-info title]))

(defn set-content [page]
  (reset! current-content page))

(defn content-component []
  (case @current-content
    :progressions [progressions]
    :resources    [resources]
    :about        [about]
    :privacy      [privacy]
    :contents     [contents @exercises/current-exercise]))
