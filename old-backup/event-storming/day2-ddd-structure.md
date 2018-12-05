build-lists: false
footer: Exploring Event-Driven Systems and DDD © 2017 RedElastic Inc.
slidenumbers: true
theme: business class, 1

# Exploring Event-Driven Systems and DDD

Diving deeper into structuring event-driven systems using _event storming_ combined with _domain-driven design_.

- RedElastic Inc.
- [redelastic.com](redelastic.com)

![right](https://dl.dropboxusercontent.com/u/14279899/Deckset/reactive-to-legacy-java/small-redelastic-logo.png)

---

# Core Concepts of Systems Structure

#### How to apply event storming to enterprise architecture

---

Monolithic systems create productivity bottlenecks.

![inline](https://dl.dropboxusercontent.com/u/14279899/Deckset/redelastic-training/resource-contention-1.png)

---

Modular systems enable team productivity.

![inline](https://dl.dropboxusercontent.com/u/14279899/Deckset/redelastic-training/resource-contention-2.png)

---

![inline](https://dl.dropboxusercontent.com/u/14279899/Deckset/redelastic-training/Learn%20Paper%20-%2032.png)

---

![inline fit](https://dl.dropboxusercontent.com/u/14279899/Deckset/microservices_wp/domain-driven-design/Page_1.jpg)

---

![inline fit](https://dl.dropboxusercontent.com/u/14279899/Deckset/microservices_wp/domain-driven-design/Page_2.jpg)

---

![inline fit](https://dl.dropboxusercontent.com/u/14279899/Deckset/microservices_wp/domain-driven-design/Page_3.jpg)

---

![inline fit](https://dl.dropboxusercontent.com/u/14279899/Deckset/microservices_wp/domain-driven-design/Page_4.jpg)

---

![inline fit](https://dl.dropboxusercontent.com/u/14279899/Deckset/microservices_wp/domain-driven-design/Page_5.jpg)

---

# Language

- Ubiqitous language makes it clear when speaking across technology and business across all teams within the organization
- Interesting events become part of your organizational event catalog

---

# Journal

- All events that happen within your organization are published to various journals
- Journal entries are _immutable_ (past events cannot be changed or deleted!)
- Journal entries can be used for real-time behaviour, recovery from crashes, audits, and so forth

^
- draw a journal

---

![inline fit](https://dl.dropboxusercontent.com/u/14279899/Deckset/microservices_wp/cqrs-webinar/Page_1.jpg)

---

![inline fit](https://dl.dropboxusercontent.com/u/14279899/Deckset/microservices_wp/cqrs-webinar/Page_2.jpg)

---

![inline fit](https://dl.dropboxusercontent.com/u/14279899/Deckset/microservices_wp/cqrs-webinar/Page_3.jpg)

---

![inline fit](https://dl.dropboxusercontent.com/u/14279899/Deckset/microservices_wp/cqrs-webinar/Page_4.jpg)

---

![inline fit](https://dl.dropboxusercontent.com/u/14279899/Deckset/microservices_wp/cqrs-webinar/Page_5.jpg)

---

# Modelling Structure in Event-Driven Systems

#### Introducing the building blocks of Domain-Driven Design

---

# Aggregate _(pale yellow)_

- A root entity that contains state
- A context boundary between key elements of the domain

![right fit](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1497302192/Screen_Shot_2017-06-12_at_5.16.16_PM_pmmqp8.png)

---

# Aggregates separated by a “bounded context”

- Contextual
- Transactional
- Atomic
- Asynchronous

^ Good place to start for defining micro-services

---

# Relationship between Aggregates and Events

1. _Aggregates_ receive _Commands_
1. _Commands_ change _Aggregate_ state
1. _Aggregates_ emit _Events_ on state change

![right fit](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1497301911/Screen_Shot_2017-06-12_at_5.11.28_PM_hqg3xo.png)

---

# The Big Picture
![inline](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1497414476/Screen_Shot_2017-06-14_at_12.26.42_AM_mub3rk.png)

^ Might be unclear how everything fits together beyond this.
^ This excercise demonstrates how to model,
^ We'll look at event driven systems
^ we'll look at how this gets turned into requirements and code

---

# Event Driven - Publisher
- Events are published to an event bus
- Published to a "topic"
- Fire and forget

![fit right](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1497414479/Screen_Shot_2017-06-14_at_12.27.29_AM_a98oek.png)

^Events can go to the top without any benefit or effect

---

# Event Driven - Subscribers
- Subscriber starts
- Connects to the "bus"
- Subscribes to any topics it is interested in
- Whenever an event is pushed, all subscribers receive it

![fit left](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1497414480/Screen_Shot_2017-06-14_at_12.27.01_AM_fkpb8e.png)

^ Once the events are there, new services can be spun up and deployed without any co-ordination cost

---

# Decoupling reduces cost
- No regression testing
- Minimal co-ordination cost
- Rapid release

^ If it's internal only, you could release it 5 times a day

---

# Turning It Into Software

---

# Step 1 - Event Storming

![inline](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1497414478/Screen_Shot_2017-06-14_at_12.26.48_AM_ddzyep.png)

^ Show Cart example with email follow up

---

# Step 2 - Requirements
- As a _user_
- I can _add items to my cart_
- So that I may _continue shopping_

^ Show cart example again

---

# Step 2 - Requirements
- As a _user_
- I can _receive an email about my cart contents_
- So that I may _remember to fulfill my order_

---

# Step 2 - Requirements - Acceptance Criteria
- _Given_ a user is inactive for x minutes
- _When_ they have products in their cart
- _Then_ send them a follow up email _and emit an event_

^ See we may now add the emitting an event as acceptance criteria
^ This might be like registration abandonment

---

# Step 3 - Write Code and Emit Events!
- Turn the model into code...
- It can perfectly match the model
  - (Given modern approaches used)
- And emit some events

---

# Step 3 - Write Code - Cart
``` scala
def receive = {
  case AddItem(item) =>
    persist(ItemAdded(item)) {
      contents += item
      emitEvent(ItemAdded(item))
    }
  case AbandonSession =>
    emitEvent(SessionAbandoned)
}
```

^ 13 words here so just bear with me for a second

---

# Requirements can turn back into storming to flesh out ideas

---

# Write some requirements and acceptance criteria
- As an _user-category_ I want to _some goal_ so that _some reason_
- As a _member_ I want to _receive an estimate_ so that _I know how long I have to endure millennials_
- Given _context_ when _action or event_ then _observable consequence_
- Given _I have a phoned in request for estimate_ when _it is ready_ then _I should receive estimate in mail_

---

^ do some other stuff and come back later

---

# Introducing the Event Catalog

#### How to share interesting events throughout the organization

---

# What's an Event Catalog?

- Describes events that are _published_
- Gives information to other teams to allow _subscription_

---

# Qualities of an Event Catalog

- Append only
- List of domain events
- Grouped by aggregate

^ the catalog can’t change events or fields after publishing

---

# Grouped by Aggregates

![inline fit](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1496321188/Screen_Shot_2017-06-01_at_8.44.53_AM_hsgsbr.png)

---

# Message format

- To allow subscription, services must know how to read events
- Describe the message format and fields
- Describe serialization (e.g. protocol buffers)

---

# Example - DefinedBenefitPlan

- _ContributionAdded_ - published to Kafka `benefit_plan_contribution_events`

```
# protobuf
message Person {
  required string id = 1;
  required int32 quantity = 2;
  required string memberId = 3;
  optional string memberName = 4;
}
```

---

# Why not add subscribers to the catalog?
- Just an API
- Need to deploy everything together?
- _Not microservices!_

---

# When Events Change

![fit inline](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1496684320/Screen_Shot_2017-06-05_at_1.38.01_PM_zew5ff.png)

---

# When Events Change (Additions)

- additive changes can work
  - forward/backward compatible

---

# When Events Change (Destructively)
- destructive changes cause problems
  - reading history
  - breaks subscribers

---

# Integration Events

- Domain events may occur before transactions complete
- A separate “integration event” can be used

---

# Integration Events

![fit inline](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1496685043/Screen_Shot_2017-06-05_at_1.48.30_PM_ewnmxy.png)

^ could consider event sourcing, cqrs introduction?
^ what about integration events? patterns in development like process manager

---

# What's next?

---

![inline fit](https://dl.dropboxusercontent.com/u/14279899/Deckset/microservices_wp/getting-started/Page_1.jpg)

---

![inline fit](https://dl.dropboxusercontent.com/u/14279899/Deckset/microservices_wp/getting-started/Page_2.jpg)

---

![inline fit](https://dl.dropboxusercontent.com/u/14279899/Deckset/microservices_wp/getting-started/Page_3.jpg)

---

![inline fit](https://dl.dropboxusercontent.com/u/14279899/Deckset/microservices_wp/getting-started/Page_4.jpg)

---

![inline fit](https://dl.dropboxusercontent.com/u/14279899/Deckset/microservices_wp/getting-started/Page_5.jpg)

---

# Let's Begin!

---

# Gather into teams

- Gather into two groups
- Add structure to flows
    + Identify bounded contexts
    + Identify aggregates
    + Continue until there is no detail left to uncover!
