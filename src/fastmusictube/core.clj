(ns fastmusictube.core
  (:gen-class)
  (:require [clojure.java.shell :as shell]
            [clojure.java.io :as io]
            [clojure.string :as str]))


(def user-musics-file "musics.txt")

;; TODO
;; IMPROVE THE FUNCTION videos-ids SPEED USING MULTITHREADING

;; IDEAS
;; CREATE A FUNCTION TO DEAL WITH ALBUMS AND FULL VIDEO PLAYLISTS AND CUT THE TRACKS ON THE APPROPRIATE TIME, ALSO RENAMING EACH OF THE TRACKS
;; CREATE 2 MODES, MUSIC MODE AND ALBUM MODE, BY CREATING A LIMITATION OF VIDEO LENGTH SIZE ON MUSIC MODE TO DISTINGUISH BETWEEN FULL ALBUMS AND SINGLE MUSICS


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

(defn concatenate-yt-url
  "Given a `xs` of strings of youtube videos id's, concatenate each item into a complete youtube video url"
  [xs]
  (map #(str "https://www.youtube.com/watch?v=" %) xs))


;; youtube-dl -x --audio-format "mp3" --audio-quality 0 https://www.youtube.com/watch?v=H6wl-EyhXl0

;;(shell/sh "youtube-dl -x --audio-format \"mp3\"  --audio-quality 0 https://www.youtube.com/watch?v=H6wl-EyhXl0")


(defn -main ;;TODO create a folder and place the files inside of it.
  "Download videos of youtube as mp3 from `user-musics-file` and give feedback to the user while it downloads"
  []
  (do (println "Searching for the musics on YouTube and gathering the URL's please wait...")
      (let [yt-links (concatenate-yt-url (videos-ids user-musics-file))
            music-names (line-seq (io/reader user-musics-file))]
        (dorun (map (fn [link music]
                      (println (str "Downloading - " music))
                      (shell/sh "youtube-dl" "-x" "--audio-format" "mp3" "--audio-quality" "0" link)) yt-links music-names)))
      (println "Done.")
      (System/exit 0)))

;; (println (map #(shell/sh "youtube-dl" "-x" "--audio-format" "mp3" "--audio-quality" "0"  %) (concatenate-yt-url (videos-ids "musics.txt"))))


