(ns fastmusictube.core
  (:gen-class)
  (:require [clojure.java.shell :as shell]
            [clojure.java.io :as io]
            [clojure.string :as str]
            [progrock.core :as pr])
  (:use [clojure.pprint]))


(def user-musics-file "musics.txt")
(def user-musics-folder "musics")

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
    (->> strings-xs
         (map #(str/replace % " " "+"))
         (map #(str/replace % "&" "%26"))  ;; NOTE Youtube replaces the char & for %26 on search queries, so here we prepare the string for this case.
         (map #(str % "%2C+video")))))     ;; NOTE Here we prepare the search query to ignore playlists and take only full videos.

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

(defn concatenate-yt-url
  "Given a `xs` of strings of youtube videos id's, concatenate each item into a complete youtube video url"
  [xs]
  (map #(str "https://www.youtube.com/watch?v=" %) xs))

(defn -main
  "Download videos of youtube and as mp3  from `user-musics-file` using youtube-dl python app, save them inside `user-musics-folder`  and give feedback to the user while it downloads using `progrock`"
  []
  (do (println "Searching for the musics on YouTube and gathering the URL's please wait...")
      (.mkdir (io/file user-musics-folder))
      (let [yt-links (concatenate-yt-url (videos-ids user-musics-file))            
            music-names (line-seq (io/reader user-musics-file))
            progress-bar (pr/progress-bar (count yt-links))]
        (dorun (map (fn [link music] 
                      (pr/print (pr/tick progress-bar (.indexOf music-names music))) ;; NOTE .indexOf here is used to get the number of the element that is being proccessed at the moment so we can use this on the progress-bar
                      (shell/sh "youtube-dl" "-x" "--audio-format" "mp3" "--audio-quality" "0" link :dir user-musics-folder)) yt-links music-names)))
      (println "\nDone.")
      (System/exit 0)))

;; TODO
;; CREATE A BETTER FEEDBACK FOR THE USER, MAKE THE PROGRESS BAR REFRESH EVERY SECOND WITH SOME ANIMATION

;; IDEAS
;; CREATE A FUNCTION TO DEAL WITH ALBUMS AND FULL VIDEO PLAYLISTS AND CUT THE TRACKS ON THE APPROPRIATE TIME USING FFMPEG, ALSO RENAMING EACH OF THE TRACKS
;; CREATE 2 MODES, MUSIC MODE AND ALBUM/PLAYLIST MODE, BY CREATING A LIMITATION OF VIDEO LENGTH SIZE ON MUSIC MODE TO DISTINGUISH BETWEEN FULL ALBUMS AND SINGLE MUSICS
