(ns workout.nav
  (:require [reagent.core :as r]))

(defn navbar []
  [:nav.navbar.navbar-default
   [:div.container-fluid
    [:div.navbar-header
     [:a.navbar-brand "burhanloey's workout"]]
    [:ul.nav.navbar-nav.navbar-right
     [:li [:a [:span.glyphicon.glyphicon-home] " Home"]]
     [:li [:a [:span.glyphicon.glyphicon-list] " Progressions"]]
     [:li [:a [:span.glyphicon.glyphicon-book] " Resources"]]
     [:li [:a [:span.glyphicon.glyphicon-info-sign] " About"]]]]])
