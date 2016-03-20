(ns workout.nav
  (:require [reagent.core :as r]
            [workout.contents :as contents]))

(defn navbar []
  [:nav.navbar.navbar-default
   [:div.container-fluid
    [:div.navbar-header
     [:a.navbar-brand "burhanloey's workout"]]
    [:ul.nav.navbar-nav.navbar-right
     [:li [:a
           {:on-click #(contents/set-content :contents)}
           [:span.glyphicon.glyphicon-home] " Home"]]
     [:li [:a
           {:on-click #(contents/set-content :progressions)}
           [:span.glyphicon.glyphicon-list] " Progressions"]]
     [:li [:a
           {:on-click #(contents/set-content :resources)}
           [:span.glyphicon.glyphicon-book] " Resources"]]
     [:li [:a
           {:on-click #(contents/set-content :about)}
           [:span.glyphicon.glyphicon-info-sign] " About"]]]]])
