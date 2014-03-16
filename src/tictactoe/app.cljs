(ns tictactoe.app
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]))

(enable-console-print!)

;; useful for debugging
(defn app-literal [data owner]
  (om/component
   (dom/pre nil
            (pr-str data))))

(defn slider [cursor owner {:keys [min max key]}]
  (om/component
   (dom/input
    #js {:id (name key)
         :type "range" :value (key cursor) :min min :max max :step 0.1
         :style {:width "100%"}
         :onChange (fn [e] (om/transact! cursor #(new-state % key (js/parseFloat (.. e -target -value)))))})))

(defn app-view [data owner]
  (reify
    om/IDidMount
    (did-mount [_]
      (fn []))
    om/IRender
    (render [_]
      (dom/div nil
               (om/build slider data {:opts {:min 0 :max 10 :key :x}})
               (om/build slider data {:opts {:min 0 :max 10 :key :y}})
               (om/build slider data {:opts {:min 0 :max 10 :key :z}})
               (om/build app-literal data)))))

(om/root
 app-view
 (atom {:x (/ 10 3) :y (/ 10 3) :z (/ 10 3) :sum 10 :max 10})
 {:target (.getElementById js/document "foo")})
