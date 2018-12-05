theme: Plain Jane, 1
slidenumbers: true
autoscale: false
build-lists: false

# Event Storming!

**Questions?**

_W:_ http://redelastic.com

_E:_ jason.goodwin@redelastic.com

_T:_ @jayrefactoring

---

# Where I come from
![inline](https://images-na.ssl-images-amazon.com/images/I/51v9jqhqpVL._SX260_.jpg)
![inline](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1493230786/Screen_Shot_2017-04-26_at_2.18.30_PM_ydtiy4.png)

---

[.build-lists: true]

# What is Event Storming
- prescriptive (no debate on approach)
- collaborative event
- with technology and business
- to produce a model of the business

^ flat (no hierarchy)

---

# The Model is a Map
![inline](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1496321163/Screen_Shot_2017-06-01_at_8.42.49_AM_sswm3c.png)

---

# Why Event Storming?
- Discovering complexity early on
- Find missing information
- Understanding the business process

---

# The Ubiquitous Language
![inline](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1496320414/Screen_Shot_2017-06-01_at_8.32.59_AM_n0sbht.png)

^ Discover a common language

---

![](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1496321162/Screen_Shot_2017-06-01_at_8.43.15_AM_gj800o.png)
# Room Setup
- Business experts & Technologists
- Giant paper roll in big open room
- Bunch of post-its in key colors
- Legend describing the key colors
- Bunch of markers
- "Picture That Explains Everything"

---

# Let's start consuming post-its!

^ Ensure we have the events provided

---

# Event - ORANGE
- Something (state change) that occured in the past
- Past tense verb: "Contribution Made"

---

# Event - ORANGE
- Something (state change) that occured in the past
- Past tense verb: "Contribution Made"

![inline fit](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1496321165/Screen_Shot_2017-06-01_at_8.43.08_AM_kntkoy.png)

---

# Your Turn
- Write an interesting event
- Place it on the board
- Grab a seat

^ Give stickies and markers
^ have everyone write down one interesting event within otpp

---

# Review

---

# Hidden Complexity
- Bad/incorrect event names
- Rotate things that need attention 45 degrees

![inline fit](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1496321169/Screen_Shot_2017-06-01_at_8.43.26_AM_mfbss1.png)

---

# Issues - PURPLE
- Highlight issues/problems that need further exploring
^ Post it: "Can we be sure money transferred to bank?"

---

# Issues - PURPLE
- Highlight issues/problems that need further exploring
- "May need to revisit in another session"
- "Missing SME in Session"
- "Process is Broken"
- "Too Coarse: This could be its own flow"

![right fit](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1496321175/Screen_Shot_2017-06-01_at_8.43.33_AM_qfv1er.png)

^ Show the "can we be sure money transferred to bank?"
^ If there is one really intense issue, turn it 45 degrees

---

# Command - BLUE
- User intention, action or decision

^ Post it: "Contribute" - ask if appropriate

---

# Command - BLUE
- User intention, action or decision

![inline fit](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1496321168/Screen_Shot_2017-06-01_at_8.43.39_AM_bzjlfo.png)

^ Show a post it with "Make Contribution" next to Contribution made

---

# The relationship between Commands and Events
- A Model of _Cause_ and _Effect_
  - _Commands_ are the user intention to change state (DoSomething)
  - _Events_ are the record of the change of state (SomethingHappened)

![fit right](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1496321178/Screen_Shot_2017-06-01_at_8.43.46_AM_xnhfhv.png)

---

# Actors - YELLOW
- User Category

---

# Actors - YELLOW
- User Category

![inline fit](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1496321181/Screen_Shot_2017-06-01_at_8.44.16_AM_tn254f.png)

^ Show Member Employer Investment Manager
^ Post it: Member (associated with Contribute command)

---

# The relationship between Actors and Commands
- Actors instantiate commands.
- Shifts the focus toward _user interactions_

![inline fit](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1496321184/Screen_Shot_2017-06-01_at_8.44.11_AM_mzi0ye.png)

^ Show Employer Contributing and a Contribution Made Event
^ Note that these start to look like requirements and user stories

---

# Read Models - GREEN
- Data that supports user decisions

---

# Read Models - GREEN
- Data that supports user decisions

![inline fit](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1496321177/Screen_Shot_2017-06-01_at_8.44.26_AM_ssf2wk.png)

^ Redo the images so they are more readable
^ show a user making a contribution based on the risk profile

---

# Policies and Reactions - PURPLE
- "Whenever..."

^ Really hammer on the "whenever x" "then y"
^ The policy says when some trigger, then we generate events or commands

---

# Policies and Reactions - PURPLE
- "Whenever..."

![inline fit](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1496321185/Screen_Shot_2017-06-01_at_8.44.37_AM_gvulqg.png)

^ PostIt: Whenever month ends, then send updated account statement to employers

---

# Policies can be manual or automatic.

---

# External Systems - PINK
- Some other external System or party

---

# External Systems - PINK
- Some other external System or party

![fit inline](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1496321187/Screen_Shot_2017-06-01_at_8.44.42_AM_jiin0w.png)

^ PostIt: Show a Bank and have an Event "InterestEarned"

---

# Where Do Domain Events Come From?

---

# An Action Started by a User

![fit inline](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1496321184/Screen_Shot_2017-06-01_at_8.44.31_AM_nhq1fc.png)

^ Draw an actor and a command

---

# From an External System

![inline fit](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1496321187/Screen_Shot_2017-06-01_at_8.44.42_AM_jiin0w.png)

^ Draw a bank sending interest earned

---

# Time Passing

![inline fit](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1496323558/Screen_Shot_2017-06-01_at_9.25.35_AM_ybbdjf.png)

^ Give example of Payment terms expiring (30 day bills)

---

# Concequences of Another Event

![inline fit](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1496321188/Screen_Shot_2017-06-01_at_8.44.48_AM_gdbihs.png)

^ Interest Earned <- Whenever Then -> AccountIncremented

---
![inline](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1496320414/Screen_Shot_2017-06-01_at_8.32.59_AM_n0sbht.png)
# A Domain Expert's Precision
- When a precise definition arises, write it down!

---

# Don't forget the unhappy path
- What if something fails?
- What if something is contested?
- Ask lots of questions to clarify!

---

# Start by modelling flows over time
- A good model will emerge
- Tomorrow we'll look at restructuring to aggregates

---

# Making Progress Clarifying
- Start at the end and move toward the beginning
- Start at the beginning and move toward the end
- Ask how things can fail
- Find rules that impact outcomes of the command
- It's okay to duplicate

---

# HotSpots
- Might find that an area has a lot of complexity
- It's okay to move post-its to make more room for those hotspots

---

^ Each member has an account

^Whenever a member leaves their employer... ->
^Two years pass, a benefit and contribution pension plan members are vested
^Transfer assets to RRSP after leaving the employer

---

^"When to use commands, when to use events"

^Looky here:
^http://verraes.net/2013/08/facilitating-event-storming/
^Facilitator tips:

^Hang the first sticky yourself (a tip from Alberto, works really well)
^Know when to step back. Don’t do the modelling, guide the modelling
^Ask questions:
^Is there something missing here? Why is there a gap?
^How does this make money?
^How does the business evaluate that this is working? What are the targets, how will we know we’ve reached them?
^For whom is this event of importance (end user, business, tenant,…) ?
^I can’t see this particular role, or type of user, in this model. Should they be on here somewhere?
^Change the direction, e.g. start at the end of the flow, move back in time, then later start at the beginning and move forward.
^Interrupt long discussions. Visualise both opinions, and, very important: ask both parties if they feel their opinion is accurately represented.
^Timebox, using pomodoro’s (25 minutes). After each pomodoro, ask what is going well and what isn’t. It’s a good opportunity to move to the next phase (e.g. from adding events to adding causality, to drawing aggregate boundaries). You may want to move on even if you don’t feel the model is complete.
^Constantly move stickies to create room for hotspots.
^Hang red stickies with exclamation marks, question marks, or other notes, anywhere you feel there’s an issue.
^At the end, make a photo. Then tell them to throw the model away, and to do it over the next day. If possible in the presence of other stakeholders.
^I personally prefer sentence-style event names (“A product was added to a basket” vs “Product added to basket”). I believe this makes the business people feel more comfortable.

^http://verraes.net/2014/01/model-storming-workshop/
^http://ziobrando.blogspot.ca/search/label/EventStorming
