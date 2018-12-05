build-lists: false
footer: An Introduction to Events and Event-Driven Systems © 2017 RedElastic Inc.
slidenumbers: true

# An Introduction to Events and Event-Driven Systems

Exploring the power of events as the lingua franca of business and technology.

- RedElastic Inc.
- [redelastic.com](redelastic.com)

![right](https://dl.dropboxusercontent.com/u/14279899/Deckset/reactive-to-legacy-java/small-redelastic-logo.png)

---

[.build-lists: true]

# Why do we need a new approach to enterprise architecture?

- Increased user expectations
- Advances in technology & infrastructure
- Disruption to traditional business models

^
- If you're not in the business of automation, you're being automated
- Systems must embrace radical agility

---

[.build-lists: true]

# Complexity

- Systems are growing more complex
- Exploring new opportunities in code is expensive
- We need fast, easy, and cheap approaches to exploratory design

---

[.build-lists: true]

# Perspective

- Every company is a now a technology company
- Our systems must adapt to new opportunities and competitive threats on radically-short timelines
- We need to be responsive to our lines of business _and_ our customers

^
- A good example in Toronto is WealthSimple, started in 2014
  + 10,000 clients and $400-million in client assets
  + $2b by 2020
  + Acquired ShareOwner, started in 1987, a broker for 28 years

---

![inline](https://dl.dropboxusercontent.com/u/14279899/Deckset/microservices_wp/the-hertiage-blues-2/Page_02.jpg)

---

![inline](https://dl.dropboxusercontent.com/u/14279899/Deckset/microservices_wp/the-hertiage-blues-2/Page_03.jpg)

---

![inline](https://dl.dropboxusercontent.com/u/14279899/Deckset/microservices_wp/the-hertiage-blues-2/Page_04.jpg)

---

![inline](https://dl.dropboxusercontent.com/u/14279899/Deckset/microservices_wp/the-hertiage-blues-2/Page_05.jpg)

---

[.build-lists: true]

# Root cause?

- Business speaks in _requirements_, _stories_
- Technology speaks in _syntax_, _servers_, _databases_

---

Mismatch between requirements and implementation creates slow moving progress.

Developers  | Business
------------|----------
UML         | BPMN
Classes     | Flows     
Schemas     | Stories
SQL         | Wireframes

---

[.build-lists: true]

# Business goals

- Improved accuracy of requirements
- Improved speed of requirements gathering
- Improved quality of deliverables
- Ability to change after launch

^
- build it right the first time
- speaking the same language
- building what was designed
- creating flexible systems that respond to metrics, data, etc

---

[.build-lists: true]

# Technical goals

- Rapidly respond to business requests
- Rapidly respond to customer feedback
- Quality of architecture (scalable and fail-proof)

---

[.build-lists: true]

# Events bring business and technology closer

- Events closely model business processes
- Events are directly mappable into functioning code
- Events perfectly capture _flow_
- Events are easily _structured_

---

# An introduction to events

---

> Things happen. Not all of them are interesting, some may be worth recording.
> 
> -- Martin Fowler

---

[.build-lists: false]

# Events are 

- _Interesting things_, that have
- _happened in the past_.

![inline](https://dl.dropboxusercontent.com/u/14279899/Deckset/redelastic-training/Learn%20Paper%20-%2016.png)

---

![inline](https://dl.dropboxusercontent.com/u/14279899/Deckset/redelastic-training/Learn%20Paper%20-%2032.png)

---

[.build-lists: false]

# Events enable flexibility

- When publishers _publish interesting events by default_ ...
- All systems within an organization can work with those events _without coordination costs_.

![right fit](https://dl.dropboxusercontent.com/u/14279899/Deckset/redelastic-training/Learn%20Paper%20-%2014%20%281%29.png)

^
- Building a catalog of interesting events in the organization brings tremendous flexibility about crafting new systems
- Just as powerful as well documented and published APIs

---

# Systems have been event driven for centuries!

![inline fit](https://dl.dropboxusercontent.com/u/14279899/Deckset/redelastic-training/Learn%20Paper%20-%2028.png)

---

> The most interesting events cause a reaction. Often you need to know why a system reacts in the way it did.
> 
> -- Martin Fowler

---

# Complex systems are always made up of events

- Important events, 
- cause reactions elsewhere in the system.
- It’s often important to understand,
- why those reactions occurred.

---

# Event Storming brings structure to the process of designing and building event-oriented systems 

---

# Without event storming...

![inline fit](https://dl.dropboxusercontent.com/u/14279899/Deckset/redelastic-training/Learn%20Paper%20-%2031.png)

---

# With event storming...

![inline fit](https://dl.dropboxusercontent.com/u/14279899/Deckset/redelastic-training/Learn%20Paper%20-%2012.png)

---

# What is Event Storming?

---

[.build-lists: true]

# What is Event Storming?

- Prescriptive and collaborative ideation session
- Between technology and business
- To produce a model of the business

^ flat (no hierarchy)

---

# The Model is a Map

![inline](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1496321163/Screen_Shot_2017-06-01_at_8.42.49_AM_sswm3c.png)

---

[.build-lists: true]

# Why Event Storming?

- Discovering complexity early on
- Find missing information
- Understanding the business process

---

# The Ubiquitous Language

![inline](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1496320414/Screen_Shot_2017-06-01_at_8.32.59_AM_n0sbht.png)

^ Discover a common language

---

![right fit](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1496321162/Screen_Shot_2017-06-01_at_8.43.15_AM_gj800o.png)

# Room Setup

- Giant paper roll
- Big open room
- Post-its in key colors
- Legend

^
- Business experts & technologists
- Bunch of markers
- “Picture That Explains Everything”

---

# Let’s start using post-its!

^ Ensure we have the events provided

---

[.build-lists: true]

# Event _(Orange)_

- Something interesting that occurred in the past
- Past tense verb: _“Contribution Made”_
- Interesting = change of state
- Now a fact _(cannot be modified or deleted)_

![right fit](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1496321165/Screen_Shot_2017-06-01_at_8.43.08_AM_kntkoy.png)

---

# Your Turn

- Write an interesting event
- Place it on the board
- Grab a seat

^ Give stickies and markers
^ have everyone write down one interesting event within OTPP

---

# Review

---

# 10 Minute Break

---

# Hidden Complexity

- Bad/incorrect event names
- Rotate events that need attention 45 degrees

![inline fit](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1496321169/Screen_Shot_2017-06-01_at_8.43.26_AM_mfbss1.png)

---

[.build-lists: true]

# Issues _(Purple)_

- Highlight issues/problems that need further exploring
- “May need to revisit in another session”
- “Missing SME in Session”
- “Process is Broken”
- “Too Coarse: This could be its own flow”

![right fit](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1496321175/Screen_Shot_2017-06-01_at_8.43.33_AM_qfv1er.png)

^ Show the “can we be sure money transferred to bank?”
^ If there is one really intense issue, turn it 45 degrees

---

# Command _(Blue)_

- User intention, action or decision

![inline fit](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1497294140/Screen_Shot_2017-06-12_at_3.01.56_PM_yu2swz.png)

^ 
- Post it: “Contribute” - ask if appropriate
- Show a post it with “Make Contribution” next to Contribution made

---

# Relationship between Commands and Events

- A Model of _Cause_ and _Effect_
  - _Commands_ are the user intention to change state (DoSomething)
  - _Events_ are the record of the change of state (SomethingHappened)

![fit right](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1497294140/Screen_Shot_2017-06-12_at_3.01.45_PM_ygrdeu.png)

---

# Actors _(Yellow)_

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

# Read Models _(Green)_

- Data that supports user decisions

![inline fit](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1496321177/Screen_Shot_2017-06-01_at_8.44.26_AM_ssf2wk.png)

^ Redo the images so they are more readable
^ show a user making a contribution based on the risk profile

---

# Policies and Reactions _(Purple)_

- “Whenever...”

![inline fit](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1497294140/Screen_Shot_2017-06-12_at_3.01.26_PM_dewcbs.png)

^
- Really hammer on the “whenever x” “then y”
- The policy says when some trigger, then we generate events 
- PostIt: Whenever a member is added, what happens?

---

# Policies can be manual or automatic

---

# External Systems _(Pink)_

- Some other external System or party

![fit inline](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1496321187/Screen_Shot_2017-06-01_at_8.44.42_AM_jiin0w.png)

^ PostIt: Show a Bank and have an Event “InterestEarned”

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

![inline fit](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1497294138/Screen_Shot_2017-06-12_at_3.01.18_PM_djtsyp.png)

^ Give example of Payment terms expiring (30 day bills)

---

# Consequences of Another Event

![inline fit](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1497294141/Screen_Shot_2017-06-12_at_3.01.36_PM_ghf7i1.png)

^ Interest Earned <- Whenever Then -> AccountIncremented

---

# A Domain Expert’s Precision

When a precise definition arises, write it down!

![inline](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1496320414/Screen_Shot_2017-06-01_at_8.32.59_AM_n0sbht.png)

---

# Don’t forget the unhappy path

- What if something fails?
- What if something is contested?
- Ask lots of questions to clarify!

---

# Start by modeling flows over time

- A good model will emerge
- Tomorrow we’ll look at restructuring to aggregates

---

# Making Progress Clarifying

- Start at the end and move toward the beginning
- Start at the beginning and move toward the end
- Ask how things can fail
- Find rules that impact outcomes of the command
- It’s okay to duplicate

---

# HotSpots

- Might find that an area has a lot of complexity
- It’s okay to move post-its to make more room for hotspots

---

# Let's Begin!

---

# Gather into teams

- Gather into two groups
- Create two "circles" of related events
- Elaborate on flows
    + Go as "wide" as possible
    + Identify all possible branches (all possible flows including failures)
    + Continue until there is no detail left to uncover!
