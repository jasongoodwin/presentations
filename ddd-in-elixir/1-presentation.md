theme: Fira, 3
# Domain Driven Design (DDD) with Elixir
- jason@funnelcloud.io
- (we're hiring)
---
# Intent
- give an introduction to DDD conceps
  - design
  - architecture
- *HEURISTICS*
- Show how actor model fits DDD
^ heuristics: leading and guiding principles.
^ I give a few examples: manufacture. videogame
---
# Warmup:
- MVC - Where does the logic go?
---
# ANSWER?
---
# .
---
# ..
---
# ...
---
# MVC
- Model: logic and data!
- View: presentation!
- Controller: just there to confuse people
- Logic in the controller is not applying "Cohesion"
^ anemic domain model anti pattern
^ Lots of code out in the while doesn't follow the heart of OO design principles
^ Alan Kay said at OOPSLA "I coined the term Object Oriented and I can tell you C++ isn't what I had in mind"
^ Message passing
---
# "The Blue Book"
![inline](https://res.cloudinary.com/dz2kvnrvc/image/upload/v1542857911/Screen_Shot_2018-11-21_at_10.38.11_PM_nux61y.png)
---
# Domain Driven Design
- OOP principles
- Applied to business models
---
# Related Behavior and Data Go Together (Cohesion)
``` scala
class Player(var weapon: Weapon {
  val life = new Life(3)
  val facing = Direction.North
  val pos = (0,0)
  def attack() {
    //weapon uses strategy pattern
    weapon.attack(pos, facing)
  }
  def equip(weapon: Weapon){
    this.weapon = weapon
  }
}
```
^ If a class does too many different things it has low cohesion
^ If a class is too interested in another class' data it has low cohesion
^ Heuristic to judge the quality of some code you're reading.
---
# Anemic Models=Procedural Code
> "The fundamental horror of this anti-pattern is that it's so contrary to the basic idea of object oriented designing."
> - Martin Fowler
---
# DDD Principles
- "The model and the heart of the design shape each other."
- "The model is the backbone of the language used by all team members."
- "The model is distilled knowledge."
---
# Key Concept: Ubiquitous Language
![inline](https://cdn-images-1.medium.com/max/1600/1*NCyCLN72TiuF1NJyPkNr7Q.png)
---
# Ubiquitous Language
- example in manufacturing
- at an *inspection station*:
  - a *part*
  - is put on *quality hold*
  - after an *operator*
  - discovers a *defect.*
---
![inline](https://res.cloudinary.com/dz2kvnrvc/image/upload/v1542770253/Screen_Shot_2018-11-20_at_10.16.46_PM_krjrzk.png)
---
# Domain Model
- Attributes of Model
  - *Inspection Station* implements *Station*
  - *Part* has many *HistoryEvents*
  - *HistoryEvent* has an *Operator*
- Behavior of Model
  - *Inspection Station adds a HistoryEvent to a Part*
---
# Modelling
^ Don't see UML as often when talking about DDD
^ You see the ubiquitous language and simple diagrams everyone can digest
---
# Entities
- Defined by:
  - their identity;
  - and a "thread of continuity" (state/lifecycle.)
- eg Part, Operator, Station, Player, User
---
# Value Objects
- No identity.
- Immutable.
- Objects that describe things.
- eg Part HistoryEvent, User's Address
---
# Other Stuff
- Modules (Cohesive colletion of related behavior/data)
- Domain Services (Facade to Entity)
- Application Services (Facade to Application)
---
# Aggregates
- Grouping of related items.
- Person is aggregate "root".
- Address is part of the Person aggregate.
^ Interconnectedness of objects has no limit.
^ If you delete a user, do you delete the address?
^ Is the address used anywhere else in the app?
^ (Can delete an address if unused by user if in same context.)
---
# Aggregates in OTP
- Actors/processes good fit for "Aggregate Root"
- "User" process. "Machine" process.
- eg. User 123  has an Address
---
```
defmodule Player do
  defstruct name: "bob",
    position: {0, 0},
    facing: :north,
    weapon: nil
  def init(:ok) do
    {:ok, %__MODULE__{}}
  end
  def handle_call({:equip, weapon}, _from, state) do
    {reply, :ok, %{state | weapon: weapon}}
  end
  def handle_call(:attack, _from, state) do
    ... # dynamic dispatch to weapon behavior
  end
end
```
---
![inline](https://res.cloudinary.com/dz2kvnrvc/image/upload/v1542771153/Screen_Shot_2018-11-20_at_10.32.15_PM_hdqhvo.png)
---
# Bounded Context
- "a unified model is not reasonable"
- Draws a "box" of separation
- Allows re-use of namespace
  - Notification Address is different than Person Address
---
# Bounded Context
![inline](https://res.cloudinary.com/dz2kvnrvc/image/upload/v1542771916/Screen_Shot_2018-11-20_at_10.44.58_PM_hynyus.png)
^ One of the downsides is that two models can represent the same concept
^ We have 3 models representing a machine
^ They all have different implementations and behave differently
---
# Bounded Context in Elixir
- Umbrella app!
- Can help promise strong delineation between contexts
- Looks like Microservices
^ microservices were found to be a fit with DDD because a bounded context can describe a service
^ it just so happens that umbrella apps are a great fit for a bounded context
^ helps create a strong delineation between contexts to avoid tight coupling
---
# RECAP
- Aggregate: Collection of related things.
- Aggregate root: The core entity in an aggregate. GenServer
- Bounded context: A "line" representing hard delineation. UmbrellaApp
---
# Operations
- two types
  - commands
  - queries
---
# Commands: "Tell Don't Ask"
- Represent *intent* to cause an effect.
- Domain entities should be "told" what to do.
- Heuristic for keeping data and behavior together.
- Idiomatic OTP! "Tell" the aggregate root.
---
# Example
- AggregateRoot: do_something
- User: update_address(addy)
- Player: attack()
- Machine: load_part(part)
---
# Events: Things that Happened
- effect/state change occurred somewhere
- Commands causes Event
- AttackEnemy -> EnemyAttacked
- SendNotification -> NotificationSent
- FailInspection -> InspectionFailed
---
# Aggregates
- Receive Commands and Events
- Respond with more Commands and Events
```
GenServer.call(Player, :attack_enemy)
> {:ok, :enemy_attacked}
```
---
![inline](https://res.cloudinary.com/dz2kvnrvc/image/upload/v1542856490/Screen_Shot_2018-11-21_at_10.14.31_PM_qw8twq.png)
---
# How to Get Commands and Events Around?
- Commands have one destination (eg GenServer.call/cast)
- Events demand publish/subscribe (eg Kafka)
---
# "Event Bus" pattern
- See "Elixir EventBus" library
- Or Kafka!
- aggregate subscribes to bus
- listens to events
- responds with more commands or events
---
![inline](https://res.cloudinary.com/dz2kvnrvc/image/upload/v1542926383/Screen_Shot_2018-11-22_at_5.39.08_PM_rap3ow.png)
---
# Benefits
- decoupling/cohesion
  - don't need to know anything about the source
  - source doesn't need to know anything about consumer
  - clear delineation
---
# Example: PagerDuty
- event occurs
- notifier listens
- sends notification
- escalates if not resolved in n minutes
---
# Event Bus
![inline](https://res.cloudinary.com/dz2kvnrvc/image/upload/v1542849459/Screen_Shot_2018-11-21_at_8.17.08_PM_s7lll3.png)
^ Bounded Contexts/Aggregates
---
# Generating and Applying Events
```
  def handle_call(
        command,
        _,
        state
      ) do
    events = generate_events(command, state)
    events |> persist_events
    updated_state = apply_events(events, state)
    {:reply, {:ok, events}, state = updated_state}
  end
```
---
# Event Sourcing
```
  @doc "call from init!"
  def recover(id) do
    events = Repo.getEvents(id)
    current_state = apply_events(events, %State{})
    {:ok, current_state}
  end
```
^ Write the events somewhere you can read them from again
^ Never write state, just write the journal
^ To recover you can just replay those events
---
# The End!
"The heart of software is its ability to solve domain-related problems for its user. [...]
Yet these are not the priorities on most software projects. Most talented developers do not
have much interest in learning about the specific domain in which they are working, much
less making a major commitment to expand their domain-modeling skills..."
---
# The End!
"Instead, the technical talent goes to work on elaborate frame-works, trying to solve domain
problems with technology. Learning about and modeling the domain is left to others.
Complexity in the heart of software has to be tackled head-on. To do otherwise is to risk
irrelevance."

