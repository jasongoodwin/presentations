theme: Letters from Sweden, 2
slidenumbers: true

# Advanced Akka - Designing and Testing

**Questions?**

Contact your instructors at:

_W:_ http://redelastic.com

_E:_ jason.goodwin@redelastic.com

_E:_ kevin.webber@redelastic.com

---

# Agenda: Advanced Akka
- DDD with Akka
- Designing with Tell
- Supervision
- Streams
- Testing

---

# DDD with Akka
- In MCV, where does the business logic go?

---

![left fit](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1483655692/Screen_Shot_2017-01-05_at_5.33.32_PM_vkeew0.png)
# What goes in an actor
- Actors make great aggregate roots
  - Consistency boundary enforced
  - One instance in memory in a system
  - Eliminates transactional and concurrency issues

---

![fit left](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1483826357/Screen_Shot_2017-01-07_at_4.58.04_PM_no2pzv.png)
# Enterprisey MVC pattern
- Controller - render view
- Service/Application Layer - usually put logic behind here
- DAO/Data layer - access the database, map table to model
- Model - data without behavior

---

# Anemic Domain Model Anti-pattern
- A class should have the behavior that acts on its data
- Application Layer (Service) directs to the domain
  - "Defines the jobs the software is supposed to do and directs the expressive domain objects to work out problems." - Evans, Domain Driven Design

---

![left fit](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1483944870/Screen_Shot_2017-01-09_at_1.53.54_AM_vuqmby.png)
# Database in Anemic Domains
- Mutable database viewed as record of truth
- ORM means that the record of truth has no logic
- Applications contain logic that acts upon data

---


![fit](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1484075756/1hezc6_rz2zie.jpg)

---

# Actors with EventSourcing
- Actors hold state that exists beyond request context
- Application becomes official system of record
- Datastores act as event logs
- Allows creation of rich domain models!

---

![fit](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1484001575/Screen_Shot_2017-01-09_at_5.38.17_PM_ibv134.png)

---

![fit left](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1484068469/Screen_Shot_2017-01-10_at_12.10.44_PM_yluega.png)
# Command Query Responsibility Separation
- Commands are clean in the domain model now
- Queries require strange code like asking actors for state
- CQRS fixes this by separating query responsibility

---

# EventSourcing and CQRS
- Fit well together
- Allow robust domain models unfettered by view code
- Adds complexity
- Harder for new team members to ramp up

---

# Designing with Tell

---

# Tell, Don't Ask.
- Send "Commands" that are acted upon
  - OO priciple: Behavior and State together
- Callbacks in futures are not safe for use within actors
  - Violates asynchronous boundaries
- Debugging timeouts is hard

---

![right fit](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1483818971/Screen_Shot_2017-01-07_at_2.53.30_PM_w7i9t5.png)
# What's wrong with Ask
- Actors encapsulate state within async boundary
- Using a future breaks that boundary safety

```
var myState=0
def receive = {
  case GetThenIncrementState(actor) =>
    val future = actor ? GiveMeValue
    future.onSuccess(x => myState = myState + x) //OOPS!
  case IncrementState(value) =>
    myState = myState + x
}
```

---

# Safely Handing Async Operations in Actors
- _Send messages_ from outside of async boundary
  - (if using async apis from actors)
- Closing over actor scope is dangerous


```
def receive = {
  case GetThenIncrementState(asyncService) =>
    val future = asyncService.apply
    future.onSuccess(x => self ! IncrementState(x)) //Safe!
  case IncrementState(value) =>
    myState = myState + x
}
```

---

# Safely Handling Async Operations in Actors
- Can use pipe instead of explicitly sending messages


```
def receive = {
  case GetThenIncrementState(asyncService) =>
    asyncService.apply.map(IncrementState(_)) pipeTo self
  case IncrementState(value) =>
    myState = myState + x
}

```

---

# Temporary Actors Instead of Ask
- Can model request/response model
- No timeouts/custom timeout
  - Code can log exactly what it's missing
- No async code in actors
- Models request response models

---

# Temporary Actors in Practice
- Extra/cameo pattern

![inline 80%](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1484081707/Screen_Shot_2017-01-10_at_3.54.23_PM_sigiz4.png)

---

# Cameo Pattern in Code

``` scala
//expects to receive exactly 2 replies
val handler = context.actorOf(Props(handlerClass, 2))

//These are equivalent
productsService.tell(GetProductInfo(cart(0)), handler)
productsService forward GetProductInfo(cart(1))

context.watch(handler)
```

---

# Cameo Pattern in Code

``` scala
var received = 0
var reply = //...

system.scheduler().scheduleOnce(500 milliseconds, self, "timeout")

def receive = {
  case ProductInfo =>
    received = received + 1
    if(received >= expected) {
      sender ! reply
      context.stop(self)
    }
  case "timeout" =>
    log.error(s"only received partial reply $reply")
}
```

---

# Supervision

---

![fit left](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1484163521/Screen_Shot_2017-01-11_at_2.37.52_PM_d59ml5.png)
# Actor Hierarchy
- Actors exist in tree-like hierarchies
- Above all actors exists one root “gaurdian” actor.
- When an actor creates a “child,” it can supervise it.
- So actors can be thought of in a directory-like structure:
  -/gaurdian/restaurant-manager/chef

---

# Supervision
- Remove response to failure from thing that can fail
- Assumes errors are due to bad state
- If unexpected errors occur,
  - “restart”/recreate the failing actor.

---

# Lifecycle Monitoring
- By default, exceptions cause a message to be dropped
  - Place logging and metrics on restarts
- Don't be afraid of using restarts in special ways
  - Configuration changes from zk
  - Actors around remote connections

---

# Testing Actors

---

![left fit](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1484088199/Screen_Shot_2017-01-10_at_5.42.11_PM_mxk5bj.png)
# Actors as Async Boundaries
- Remote services cross async boundaries
- Sending and receiving messages can be proxied by actors
- Allows uniform API and test strategies

---

# Akka Testkit
- dedicated test library for akka
- test different levels:
  - synchronous testing for testing actors
  - asynchronous testing for integration tests
- mocking capabilities (expectations)

---

# Synchronous Unit Tests

---

# Unit Testing with TestActorRef
- Synchronous
- No need to use Await/Thread.sleep
- Exceptions thrown on main thread
- Can access underlying actor's state

---

# Synchronous Testing Example
- TestActorRef uses CallingThreadDispatcher
  -
``` scala
val actorRef = TestActorRef[MyActor]
actorRef ! Command //Does not progress until complete

//Assertions can access the underlying actor
val actor = actorRef.underlyingActor
actor.state should equal("expected value")
```

---

# Designing for Testability
- Composing behavior allows finer grain testing

``` scala
class ArticleParser extends Actor {
  def receive = {
    case ParseArticle(htmlDoc) =>
      val title = getTitle(htmlDoc)
      val body = getBodyText(htmlDoc)
      sender ! ParsedDocument(title, body)
  }

  def getTitleText(doc: String) = {...}
  def getBodyText(doc: String) = {...}
}

describe("ArticleParser"){
    val actorRef = TestActorRef[MyActor]
  it("should parse title of html document if well formatted"){
    actorRef.underlyingActor.getTitleText(html) //test in isolation
  }
}

```

---

# Mocking

---

# Mocks vs Stubs
- Stubs return an expected result
- Mocks imply expectations
    - Methods should be called with expected values

---

# TestProbes as Mocks
- Take the place of an actor ref
  - Can assert expectations
  - Can reply
  - Dependency Injection FTW

``` scala
val probe = TestProbe()
val actor = context.actorOf(Props(clazz, probe))
```

---

# TestProbe Example
- TestProbe w/ Response is a stub
- TestProbe w/ Expectations and Response is a mock
- The latter is a more complete excercise
``` scala
val probe = TestProbe()
val actor = context.actorOf(Props(clazz, probe))
actor ! Command
probe.expectMsg(0 millis, ExpectedMsg) // TestActor runs on CallingThreadDispatcher
probe.reply("world")
actor.underlyingActor.state should be("world")
```

---

# Asynchronous Testing with ScalaTest
- Better approaches than await
- Map assertions and return them w/ Async Specs

``` scala
class AddSpec extends AsyncFlatSpec {
[...]
  it should "eventually compute a sum of passed Ints" in {
    val futureSum: Future[Int] = addSoon(1, 2)
    futureSum map { sum => assert(sum == 3) }
  }
}
```
