# FastMusicTube

FastMusicTube is an app to fast download musics from youtube, without having to provide any videos URL.
You know when you're going offline, and you want to take some musics along with you? But you don't want to have the trouble of
searching every music and taking it's links to just download the video/mp3 file from another website, having to see a lot of
ad's, well here is a solution!

FastMusicTube uses Youtube-DL, a python app to download the videos and fast convert to MP3, with the best quality as possible.
The way it works: FMT takes the musics titles from the musics.txt file, search on YouTube and collect the first video URL from the search page result
then it starts downloading as MP3.

*FastMusicTube can also download albums/full video playlists!

## Installation
In order to use FastMusicTube, you need some things installed first:
- Follow the instructions and install Youtube-DL https://github.com/ytdl-org/youtube-dl (as you will see, you need to install python too!)
- You also need to install FFmpeg https://www.ffmpeg.org/download.html
- Be Sure to have Java 8+ installed too.

Linux Debian users can use those commands to fast install everything:
$ sudo apt-get install python-pip python-dev build-essential 
$ sudo pip install --upgrade pip 
$ sudo pip install --upgrade virtualenv 
$ sudo -H pip install --upgrade youtube-dl
$ sudo apt-get install ffmpeg

After installing everything/making sure you have those dependencies, download FastMusicTube from this link:
Download from http://example.com/FIXME.

## Usage

Inside the folder fastmusic tube, open the musics.txt file.
Add your musics or albums, one per line, example:

oasis - wonderwall
led zeppelin - stairway to heaven

and so on.

After you finished specifying your musics/albums, open the terminal and execute this command:

    $ java -jar fastmusictube-0.1.0-standalone.jar

Wait until the progress bar get's to 100%.

After you're done, to use again, just delete the musics from musics.txt and replace with the new ones.

WARN: DON'T CANCEL THE DOWNLOAD IF THE PROGRESS BAR SEEMS STUCKED, I'M WORKING TO IMPROVE THE PROGRESS BAR, NEVERTHLESS, THE PROGRAM KEEPS RUNNING EVEN WHEN THE PROG BAR SEEMS STUCKED.
## Options

...

## Examples

...

### Bugs

- The progress bar seems to be stucked while the app downloads the videos.

## License

Copyright © 2019

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
