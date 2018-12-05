theme: Ostrich, 1
build-lists: false
slidenumbers: true

## Zombie Apocalypse Live
#### Front row seats via streaming video
![inline fit](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1498651640/Screen_Shot_2017-06-28_at_8.06.52_AM_yokgc3.png)

^ Welcome guests - thanks for hosting this event
^ Today we're going to talk about streaming.
^ Don't talk too much here

---

# About me
- Worked on ad insertion video platform
- Streamed real start of apocalypse
- Bit by a Google. Now crave brains
- Akka guy (author of Learning Akka)

![fit left](https://images-na.ssl-images-amazon.com/images/I/51v9jqhqpVL._SX260_.jpg)

---

# Streaming
- Lots of hype (fast data)
- Lots of advancements
- Use cases differ from ETL

^ how many people here are data people
^ how many people here feel they really understand streaming
^ Don't have to describe the purpose yet.

---

## Consider, with your current understanding
# of streaming,
### how you might design these systems

---

# streaming is everywhere

^ Once you see an application of streaming it gets a bit easier to reason about what streaming really means in terms of its application
^ It's not easy to understand how streaming application varies from data oriented applications

---

### "One of the key misconceptions we had was that stream processing would be used in a way sort of like a real-time MapReduce layer.

---

### "What we eventually came to realize was that the most compelling applications [...] are [...] a kind of asynchronous microservice rather than being a faster version of a batch analytics job." - Jay Kreps

---

# Streaming
- Receiving a bunch of *discrete* things
- _Infinite_ set
- Building some view with the _cumulation_ of things

![inline fit](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1498651842/Screen_Shot_2017-06-28_at_8.10.20_AM_jy9heg.png)

^ If we aren't doing something with the cumulation, we might as well just update a table in a database
^ things = could be events, data, anything really
^ we just want to build a current view based on these things or some portion of these things
^ Databases often keep logs of events like this, but only show you the most recent

---

# The world is ending
## Zombies everywhere!

![inline fit](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1498651351/Screen_Shot_2017-06-28_at_8.02.06_AM_jpyz18.png)

^ describe the purpose of the talk at this point
^ Help you see real-world application of streaming
^ so when you look at streaming tools you can think of how you would build and model video streaming in context to those tools
^ without a usecase it can be hard to reason about

---

## Hiding out with your phone
## Watching the news streaming over wi-fi
![inline fit](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1498651351/Screen_Shot_2017-06-28_at_8.02.06_AM_jpyz18.png)

^ I've streamed some big events and I'm telling you the ratings would be phenomenal

---

## How does the video get here?
### And why does it never buffer?
^ We're going to look at everything that happens
^ How does the video get here? Why doesn't netflix ever buffer?
^ Walk back from the player
^ again the idea is to help you think about how you might design a real streaming application

---

# Lo-tech "Streaming" (File)
- Discrete "Packets" sent
- Re-assembled to a file
- "skiping" - re-request range w/ header
- *Range: bytes=285343744-673808383*

![fit left](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1498652206/Screen_Shot_2017-06-28_at_8.16.24_AM_ruijdx.png)

^ TCP segments have header information and then some data
^ The header info is discrarded and the data is reconstructed from all of the packets into a view
^ You can see in your browser if you skip ahead in a video it re-requests the file with a header
^ So this does meet our definition of a stream - we have discrete units (tcp packets) and a cumulative view (file)
^ video you can just open right in the middle of a file and we'll look at how it's possible a bit later

---

# Hi-tech (youtube, netflix, etc)
- Split the video itself into many small pieces
- (segments on layer 7)
- Playlist references segments
- HTTP client requests segments

![fit left](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1498652631/Screen_Shot_2017-06-28_at_8.21.56_AM_vrhsje.png)

^ show video segments
^ note they don't ever buffer - we'll look at how the tech prevents buffering later

---

# Let's have a look

![inline fit](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1498652026/Screen_Shot_2017-06-28_at_8.13.18_AM_a3q0hv.png)

---

# Remember Winamp's M3U Playlist?
![fit inline](https://www.groovypost.com/wp-content/uploads/2013/11/Winamp1.png)

---

## Apple's HTTP Live Streaming
### Uses a unicode version (m3u8)

^draw a llama
^youtube and netflix use mpeg dash - it's very similar

---

# A player downloads an M3U8 Playlist
- containing a bunch of 10 second video fragments
- and just plays them in order

![fit inline](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1498652631/Screen_Shot_2017-06-28_at_8.21.56_AM_vrhsje.png)

^ Just like a winamp playlist at this point

---

# It really whips the llama's ass.
^ Just like winamp

---

```
#EXTM3U
#EXT-X-TARGETDURATION:10
#EXT-X-VERSION:3
#EXT-X-MEDIA-SEQUENCE:0
#EXTINF:10.0,
http://example.com/movie1/fileSequenceA.ts
#EXTINF:10.0,
http://example.com/movie1/fileSequenceB.ts
#EXTINF:10.0,
http://example.com/movie1/fileSequenceC.ts
#EXTINF:9.0,
http://example.com/movie1/fileSequenceD.ts
```

---

# WHAT'S IN THE SEGMENTS?
## "BipBop The Movie" - Spoiler alert!
^ Spoiler alert

---

# Segment0 - 00:00-00:10
![fit right](http://playertest.longtailvideo.com/adaptive/bipbop/gear2/fileSequence0.ts)

```

#EXTM3U
#EXT-X-TARGETDURATION:10
#EXT-X-MEDIA-SEQUENCE:0
#EXTINF:10, no desc
gear2/fileSequence0.ts <<<<
#EXTINF:10, no desc
gear2/fileSequence1.ts

```

^ Starts at zero seconds
^ Note how it says gear2 in the video

---

# Segment1 - 00:10-00:20
![fit right](http://playertest.longtailvideo.com/adaptive/bipbop/gear2/fileSequence1.ts)

```

#EXTM3U
#EXT-X-TARGETDURATION:10
#EXT-X-MEDIA-SEQUENCE:0
#EXTINF:10, no desc
gear2/fileSequence0.ts
#EXTINF:10, no desc
gear2/fileSequence1.ts <<<<

```

^ Note how it says gear2 in the video

---

# End of the world, Streaming Live!
- Stream has no beginning or end
- The player keeps requesting the playlist
- "Window" moves as more video is available

![fit inline](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1498651640/Screen_Shot_2017-06-28_at_8.06.52_AM_yokgc3.png)

^ Linear is the industry term
^ We'll look at the encoder later

---

# Playlist 1 - T+0 Seconds
```
#EXTM3U
#EXT-X-TARGETDURATION:10
#EXT-X-MEDIA-SEQUENCE:1
#EXTINF:10,
fileSequence1.ts
#EXTINF:10,
fileSequence2.ts
#EXTINF:10,
fileSequence3.ts
#EXTINF:10,
fileSequence4.ts
#EXTINF:10,
fileSequence5.ts
```

^ Note the first ts file and the last ts file in the file
^ 10s goes by and then the player requests the playlist again
^ Note also that the media sequence tag at the top - it has to count how many files fell out of the window
^ That's because they might be named in any random manner (GUIDS for example)

---

# Playlist 2 - 10 seconds later

```
#EXTM3U
#EXT-X-TARGETDURATION:10
#EXT-X-MEDIA-SEQUENCE:2
#EXTINF:10,
fileSequence2.ts
#EXTINF:10,
fileSequence3.ts
#EXTINF:10,
fileSequence4.ts
#EXTINF:10,
fileSequence5.ts
#EXTINF:10,
fileSequence6.ts
```

^ Now you'll see a file "fell out" of the playlist window
^ and a new one has entered into the window

---

# Playlist 3 - 20 seconds later

```
#EXTM3U
#EXT-X-TARGETDURATION:10
#EXT-X-MEDIA-SEQUENCE:3
#EXTINF:10,
fileSequence3.ts
#EXTINF:10,
fileSequence4.ts
#EXTINF:10,
fileSequence5.ts
#EXTINF:10,
fileSequence6.ts
#EXTINF:10,
fileSequence7.ts
```

^ Same thing happened again.

---

# The current view of the playlist
- a "fold" or aggregating with the collector based on the history of events

^ Just start to think if you made queries against a dataset in spark how you might build the playlist window
^ Or with Kafka streams
^ you might have a series of events and you just want to window them around the most recent, and count how many have fallen out of the window

---

# WHAT'S THAT NOISE?
## SOMETHING IS COMING!

---

![fit](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1498651520/Pres_-_2_kvag0r.png)

^ Run into the elevator
^ Draw a picture of a zombie chasing a person into an elevator

---

# The video quality degrades
## Doesn't buffer

---

# "Manifest" has multiple Playlists

![fit right](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1498654122/Screen_Shot_2017-06-28_at_8.48.19_AM_gjvnyc.png)

^ the playlists are all different resolution and target bitrate. maybe different audio quality.
^ maybe audio only for a cel manifest (we want to avoid buffering at all cost)
^ We call this Adaptive bitrate

---

## "Manifest" with different quality playlists

```
#EXTM3U
#EXT-X-VERSION:6
#EXT-X-STREAM-INF:PROGRAM-ID=1,BANDWIDTH=2855600,CODECS="avc1.4d001f,mp4a.40.2",RESOLUTION=960x540
live/medium.m3u8
#EXT-X-STREAM-INF:PROGRAM-ID=1,BANDWIDTH=5605600,CODECS="avc1.640028,mp4a.40.2",RESOLUTION=1280x720
live/high.m3u8
#EXT-X-STREAM-INF:PROGRAM-ID=1,BANDWIDTH=1755600,CODECS="avc1.42001f,mp4a.40.2",RESOLUTION=640x360
live/low.m3u8
#EXT-X-STREAM-INF:PROGRAM-ID=1,BANDWIDTH=545600,CODECS="avc1.42001e,mp4a.40.2",RESOLUTION=416x234
live/cellular.m3u8
```

^ Gear two gear three

---

# How does the player "adapt"
- If it can't download a segment in time,
- Looks in manifest for another playlists
- Tries downloading lower quality segment

![fit right](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1498654435/Screen_Shot_2017-06-28_at_8.53.30_AM_li7enm.png)

---

# Cameras don't make "Hi, Mid, Low" playlists

---

# Transcoders do
- encoder pushes to transcoder
- finally some UDP
- Transcoder produces the different qualities
- Puts on 'origin' server

![fit left](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1498654629/Screen_Shot_2017-06-28_at_8.55.53_AM_fbho2c.png)

^ Akamai has a lot of solutions for replicating video from the origin server, providing it downstream (for example to google's services)
^ Mention push vs pull

---

# OH NO
## ZOMBIES ARE ATTACKING AN ENCODER!
### (Elemental encoders are very loud and annoying)

![fit left](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1498654897/Screen_Shot_2017-06-28_at_9.01.17_AM_tb5znn.png)

---

# don't worry, we have a backup!

![inline](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1498655012/Screen_Shot_2017-06-28_at_9.03.11_AM_gznu6d.png)

---

```

#EXTM3U
#EXT-X-VERSION:6
#EXT-X-STREAM-INF:PROGRAM-ID=1,BANDWIDTH=5605600,CODECS="avc1.640028,mp4a.40.2",RESOLUTION=1280x720
live/high-a.m3u8
#EXT-X-STREAM-INF:PROGRAM-ID=1,BANDWIDTH=5605600,CODECS="avc1.640028,mp4a.40.2",RESOLUTION=1280x720
live/high-b.m3u8
[...]
```

^^ player is smart enough to know if one is 404ing to try the other

---

# Recap where we are
- Streaming is a bunch of discrete units
- A view or aggregation is built from the history

---

# similar to packets in TCP transmission
- data inside packets reconstructed into files

![fit left](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1498652206/Screen_Shot_2017-06-28_at_8.16.24_AM_ruijdx.png)

---

# similar to live Playlist
- playlist window keeps moving over time

![fit left](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1498653017/Screen_Shot_2017-06-28_at_8.27.37_AM_g0qsru.png)

^ reuse picture of playlists


---

# Similar to TS Files in Playlists
- small pieces reassembled into long video

![fit left](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1498652631/Screen_Shot_2017-06-28_at_8.21.56_AM_vrhsje.png)

^ reuse picture of playlist and TS files

---

# Similar to Samples in audio
- 44khz = 44,000 small "samples" per second

![fit left](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1498655567/Screen_Shot_2017-06-28_at_9.12.26_AM_w4c3bn.png)

---

# And frames in a video

---

# Video is successive photos?

![inline fit](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1498655887/Screen_Shot_2017-06-28_at_9.17.42_AM_obamz0.png)

^ Insert pacman

---

# Not really
## (that would take too much space)

---

## A video is a bunch of
# Groups of Pictures (GOP)

![fit inline](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1498656357/Screen_Shot_2017-06-28_at_9.25.24_AM_w7slvk.png)

^ Smallest unit of a video that can be played on its own
^ You know when you skip around sometimes you hear the audio before you see the video?

---

### Any frame within the group of photos is reliant on the _stream_

^ Don't worry - this will start to come together in a second

---

# GOP starts with a Keyframe
### (Complete jpeg)

![inline fit](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1498656466/Screen_Shot_2017-06-28_at_9.27.29_AM_ds4xr7.png)

^ Show pacman

---

## Subsequent predictive frames

![fit inline](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1498656623/Screen_Shot_2017-06-28_at_9.30.02_AM_z7yuaq.png)

^ show pacman eating dot

---

## What if we don't have the complete GOP?
### Predictive frames move the wrong data
![inline](http://www.benjaminkim.com/wp-content/uploads/2011/09/flvartifacts.png)

^ This is because the required information to display this frame is missing
^ We'll look at how/why now

---

# frames are "streaming" too!
- fold/map/reduce/aggregation based on previous frames

^ Take a minute to consider this
^ If you know streaming, then you know video
^ If you know video, then you can understand streaming data

---

# How do streaming applications differ from big data?
- Use cases differ
- Streaming applications are data-centric _services_
- Data applications do _batch analysis_

---

>  "These stream processing apps were most often software that implemented core functions in the business rather than computing analytics about the business."- Jay Kreps

---

# Approaches

---

# Hipster-Stream Processing
- Subscribe to events
- Roll your own fold/map/reduce

---

# Kafka Streams
- Java library
- Gives a map/reduce abstraction
- Can make code a bit cleaner

---

# Spark Streaming
- Spark's language/API
- For stream processing (eg receiving events from Kafka, ZMQ)

---

# Questions?
# HIT ME UP
- E: jay.michael.goodwin@gmail.com
- T: @jayrefactoring
- P: 647-985-3325
