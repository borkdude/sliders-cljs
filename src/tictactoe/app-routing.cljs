(ns example
  (:require [secretary.core :as secretary :include-macros true :refer [defroute]]
            [goog.events :as events])
  (:import goog.History
           goog.history.EventType))

(enable-console-print!)

(def application
  (js/document.getElementById "app"))

(defn set-html! [el content]
  (aset el "innerHTML" content))

(secretary/set-config! :prefix "#")

;; /#/
(defroute home-path "/" []
  (set-html! application "<h1>OMG! YOU'RE HOME!</h1><div id='foo'>"))

;; /#/users
(defroute users-path "/users" []
  (set-html! application "<h1>USERS!</h1>"))

;; /#/users/:id
(defroute user-path "/users/:id" [id]
  (let [message (str "<h1>HELLO USER <small>" id "</small>!</h1>")]
    (set-html! application message)))

;; /#/777
(defroute jackpot-path "/777" []
  (set-html! application "<h1>YOU HIT THE JACKPOT!</h1>"))

;; Catch all
(defroute "*" []
  (set-html! application "<h1>LOL! YOU LOST!</h1>"))

;; Quick and dirty history configuration.
(let [h (History.)]
  (goog.events/listen h EventType/NAVIGATE #(secretary/dispatch! (.-token %)))
  (doto h (.setEnabled true)))

(secretary/dispatch! "/")
