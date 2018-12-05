theme: Plain Jane, 1
slidenumbers: true
autoscale: false

# Day2 - Aggregates and Catalogs

**Questions?**

_W:_ http://redelastic.com

_E:_ jason.goodwin@redelastic.com

_T:_ @jayrefactoring

---


# Defining Aggregates

---

> We only spoke about Events so far!
> We haven't talked about any entities

---

# Aggregate - TRADITIONAL PALE YELLOW
- A root entity that may contain other values and entities
- A context boundary between key elements of the domain

![fit inline](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1496321188/Screen_Shot_2017-06-01_at_8.44.53_AM_hsgsbr.png)

---

# Aggregates separate by a "bounded context"
- contextual
- transactional
- atomic
- asynchronous

^ Good place to start for defining micro-services

---

# Relationship between Aggregates and Events
- _Aggregates_ receive _Commands_
- _Commands_ change _Aggregate_ state
- _Aggregates_ emit _Events_ on state change

![fit 95%](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1496321188/Screen_Shot_2017-06-01_at_8.44.53_AM_hsgsbr.png)

^ show picture of Command, Aggregate and Event

---

# Qualities of an Event Catalog
- append only
- list of domain events
- grouped by aggregate

^ the catalog can't change events or fields after publishing

---

# What is it?
- Describes events that are _published_
- Gives information to other teams to allow _subscription_

---

# Grouped by Aggregates
![fit](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1496321188/Screen_Shot_2017-06-01_at_8.44.53_AM_hsgsbr.png)

---

# Message format
- To allow subscription, services must know how to read events
- Describe the message format and fields
- Describe serialization (e.g. protocol buffers)

---

# Example

## DefinedBenefitPlan
*ContributionAdded*

- Published to Kafka benefit\_plan\_contribution\_events

```
# Protocol Buffer
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
- Need to deploy everything together? _not microservices!_

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
- A separate "integration event" can be used

---

# Integration Events
![fit inline](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1496685043/Screen_Shot_2017-06-05_at_1.48.30_PM_ewnmxy.png)

^ could consider event sourcing, cqrs introduction?
^ what about integration events? patterns in development like process manager
