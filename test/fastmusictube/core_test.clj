(ns fastmusictube.core-test
  (:require [clojure.test :refer :all]
            [fastmusictube.core :refer :all]))


(def youtube-search-url "https://www.youtube.com/results?search_query=")

(deftest video-search-id
  (testing "testing if the function gets the first video from search results" ;;HACK technically, youtube can change it's first video result to another one anytime, so the tests will fail but the function might still work...
    (is (= (first-video-id (slurp (str youtube-search-url "oasis+wonderwall"))) "bx1Bh8ZvH84"))
    (is (= (first-video-id (slurp (str youtube-search-url "queen+bohemian+rhapsody"))) "fJ9rUzIMcZQ"))))


