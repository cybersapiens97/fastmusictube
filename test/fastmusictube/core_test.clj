(ns fastmusictube.core-test
  (:require [clojure.test :refer :all]
            [fastmusictube.core :refer :all]))


(def youtube-search-url "https://www.youtube.com/results?search_query=")

(deftest video-search-id
  (testing "testing if the function gets the first video from search results" ;;HACK technically, youtube can change it's first video result to another one anytime, so the tests will fail but the function might still work...
    (is (= (first-video-id (slurp (str youtube-search-url "oasis+wonderwall"))) "bx1Bh8ZvH84"))
    (is (= (first-video-id (slurp (str youtube-search-url "queen+bohemian+rhapsody"))) "fJ9rUzIMcZQ"))))

(deftest search-videos-ids
  (testing "testing if the fn videos-ids get the correct ids from the user file, in this case here, using a custom file for testing purposes")
  (spit "testfile.txt" "oasis champagne supernova\nled zeppelin stairway to heaven\nrolling stones sympathy for the devil\n")
  (is (= (videos-ids "testfile.txt") (seq ["tI-5uv4wryI" "D9ioyEvdggk" "GgnClrx8N2k"]))))



