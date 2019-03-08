(ns fastmusictube.core
  (:gen-class)
  (:require [clojure.java.shell :as shell]
            [clojure.java.io :as io]
            [clojure.string :as str]))


(def user-musics-file "musics.txt")

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

(shell/sh "pwd")


(defn first-video-id
  "Find first video id from a youtube search html source code `s`"
  [s]
  (let [regex #"(?<=watch\\?).*?(?=yt-uix-tile-link)"  ;; HACK this regex is not perfect, needs post formatting with subs function and can possibly be broken on the future
        re-subs-start 3
        re-subs-end 14]
    (subs (re-find regex s) re-subs-start re-subs-end)))

(defn userfile-xs
  "Given a java file `obj` returns a xs of it's strings split at line breaks and each formatted for propper youtube search query"
  [obj]
  (let [strings-xs (line-seq obj)]
    (map #(str/replace % " " "+") strings-xs)))

(defn search-videos
  "Given a `coll` of strings, returns a xs of strings with youtube search html source codes of every single string from `coll`"
  [coll]
  (let [youtube-search-url "https://www.youtube.com/results?search_query="]
    (map #(slurp (str youtube-search-url %)) coll)))

(defn videos-ids
  "Given a `s` txt file, will return a xs with each line of strings as youtube video id from a search at youtube grabbing the first video on the page."
  [s]
  (->> (userfile-xs (io/reader s))
       (search-videos)
       (map #(first-video-id %))))

(videos-ids "musics.txt")


;; youtube-dl -x --audio-format "mp3" --audio-quality 0 https://www.youtube.com/watch?v=H6wl-EyhXl0

