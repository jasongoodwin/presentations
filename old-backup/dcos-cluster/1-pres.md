theme: Sketchnote, 6
slidenumbers: true
autoscale: true

# Example of a Refactoring to Akka and EventSourcing on DC/OS

**Questions?**

_W:_ http://redelastic.com

_E:_ jason.goodwin@redelastic.com

_T:_ @jayrefactoring

---

# Where I come from
![inline](https://images-na.ssl-images-amazon.com/images/I/51v9jqhqpVL._SX260_.jpg)
![inline](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1493230786/Screen_Shot_2017-04-26_at_2.18.30_PM_ydtiy4.png)

---

# Where I am
- How do you know if you're going the right way if you haven't gone up the mountain before?

> "Devs love proof of concepts, but we need them to go deeper on fundamentals of distributed sys, regardless of frameworks. Jason, u have shown us walk the talk. Thanks for that." - Director of Engineering, Stealthmode Project

---

# In MVC, where does the business logic go?
- Controller?
- View?
- Service aka Application Layer?
- Model?

---

# This is not a demonstration of technology, but approaches
- Microservices
- DDD
- EventSourcing
- Cluster/Sharding
- Deployment/Scaling Automation

---

# Agenda:
- Refactoring A Service out of a Monolith (w/ DDD)
- Deploying it and Scaling it on DC/OS

---

# The design principles we want to refactor to: Rich Domain Models

> "The alternative to good design is bad design, not no design at all." - Douglas Martin

---

- We'll take the cart, refactor it, and put it in its own service

---

![inline](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1493058290/Screen_Shot_2017-04-24_at_2.24.06_PM_i2gur1.png)
# Today, Your CRUD Apps Act on External State

---

# With Transaction Scripts
> "Transaction Script organizes business logic by procedures where each procedure handles a single request from the presentation." PoEAA, Martin Fowler

---

## Really, it's Procedural Code Acting on Data
- You could do the same thing with a PERL script and a database

---

# This Anti-pattern is "Anemic Domain Models"

> "The key point here is that the Service Layer is thin - all the key logic lies in the domain layer." - Martin Fowler

---

## We Should Tell Objects What to Do

> "I'm sorry that I long ago coined the term "objects" for this topic because it gets many people to focus on the lesser idea. The big idea is 'messaging.'" - Alan Kay

---

## We shouldn't act on objects from outside

---

# It's like a car with the engine on the outside

``` scala
val car: Car = carService.getCarFromDatabase

carService.accelerate(car)
carService.turnLeft(car)
carService.save(car)
```

---

# The car should do things in response to input

``` scala
val car: Car = new Mercedes

car.pushGas()
car.turnWheelLeft()
```

---

# We Call this a Rich 'Domain Model'

> "An object model of the domain that incorporates both behavior and data." - PoEAA, Martin Fowler

---

![inline](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1493059627/Screen_Shot_2017-04-24_at_2.46.42_PM_vsnnfm.png)
# Transaction Script vs Rich Domain Model

---

![inline](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1493061341/Screen_Shot_2017-04-24_at_3.15.19_PM_tzqfo8.png)
# Refactoring to Rich Domain Models Using EventSourcing

---

# Step0: Forget the database for now
- Delete the DAOs

---

# Step1: State and Behavior Go Together

> Procedural code gets information then makes decisions. Object-oriented code tells objects to do things. - Alec Sharp

---

# Tell Don't Ask
- Move all behavior into the Cart class
- All state is encapsulated, the actor affects its own state

``` scala
cart.empty()
cart.changeItem(item, qty)
```

---

![inline](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1493062076/Screen_Shot_2017-04-24_at_3.27.35_PM_z2bfua.png)
# Step2: Persist History of Events In Datastore

---

![inline](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1493215564/Screen_Shot_2017-04-26_at_10.05.22_AM_vpbwzh.png)
## Replay events to recover current state

---

# This is called "Event Sourcing"
- RICH DOMAINS - logic and data together at last!
- Simpler Concurrency
- Datastore contains history (BI)

---

# Event Sourcing In Practice w/ Akka
- Akka Actors send Messages (Commands)
- Process one at a time synchronously
- Event Sourcing - persist and recovery

``` scala
def receive {
  case EmptyCartCommand =>
    persist(CartEmptiedEvent) { _ =>state = Map.empty }
}
def receiveRecover {
  case CartEmptied => state = Map.empty
}

```

---

![inline](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1493240921/Screen_Shot_2017-04-26_at_5.08.21_PM_pmzlfj.png)
# Step 3: Move Cart Service into Own Application

---

# Step 4: Repeat!

---

# ~Infinite Scale
-

---

# Scaling with Akka
- Akka abstracts *where* things are running
- Send command to an actor: `actor ! command`
- Actor could be remote or local - code doesn't care

---

# Passivation
- Actors can wake/sleep to save memory
- After a period of inactivity, an actor can passivate
- Once a message comes it will recover again

---

# Horizontal Scaling
-

---

## All of this stuff fits in one machine?
- Nope

---

![inline](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1493222751/Screen_Shot_2017-04-26_at_12.05.18_PM_plbtzw.png)
# 'Shards' are allocated to each node

---

![inline](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1493216726/Screen_Shot_2017-04-26_at_10.24.56_AM_eyuqpa.png)
# Those shards are re-balanced when scaling

---

# ShardId
- An entity's location is determined by a shardId
- For example, we might identify an entity's shard by hashing the userId: `cart.userId.hashCode % numberOfShards`

---

## Demo of scaling up on DC/OS!
-

---

![inline](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1493226748/Screen_Shot_2017-04-26_at_1.11.48_PM_yv4cac.png)

---

# Pool of resources
- CPU and Memory shown from pool of agents

---

# Package Management
- Let you run out-of-the-box tasks in DC/OS

---

# Scheduler
- Gives 'tasks' to mesos to run
- Let's run our cart!

---

# What we're looking at
- look at marathon config,
- container image config,
- cpu memory, talk about agent allocation,
- pass in environment variable

---

# Better Read Models

---

![inline](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1493230079/Screen_Shot_2017-04-26_at_2.07.23_PM_zfwago.png)
# CQRS - Command Query Responsibility Separation

---

# Why is cart a good place to start?
- CQRS, ES, Cluster Sharding, Messaging...
- Microservices and Distributed computing...
- Distributed transactions...

---

# Build capabilities
- Start small
- Acquire knowledge in team
- Succeed
- Repeat

---
