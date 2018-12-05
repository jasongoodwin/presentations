# Reactive Streams

---

![inline](https://res.cloudinary.com/dqbo1a5ao/image/upload/v1484171491/presentations/akka-streams/01-array-vs-stream.jpg)

---

![inline](https://res.cloudinary.com/dqbo1a5ao/image/upload/v1484171493/presentations/akka-streams/02-array-vs-stream.jpg)

---

# Appeal of streams?

- Per-event processing _(≤ 1 second latencies)_
- Mini-batch processing _(≤ 10 second latencies)_
- Batch processing _(≤ 1 hour latencies)_
- Monitoring, analytics, complex event processing

---

![left](https://res.cloudinary.com/dqbo1a5ao/image/upload/v1484171497/presentations/akka-streams/03-river.jpg)

# Challenges?

- Ephemeral
- Unbounded in size
- Potential flooding
- Unfamiliar

---

# Reactive Streams

---

# Reactive Streams?

- Reactive Streams is a specification and low-level API for library developers

---

# Reactive Streams?

- Reactive Streams is a specification and low-level API for library developers
- Started as an initiative in late 2013 between engineers at Netflix, Pivotal, and Lightbend

---

# Reactive Streams?

- Reactive Streams is a specification and low-level API for library developers
- Started as an initiative in late 2013 between engineers at Netflix, Pivotal, and Lightbend
- Aimed to address two critical challenges
  - Interoperability between streaming libraries
  - Flow control over an asynchronous boundary

---

# What is Reactive Streams?

- TCK (Technology Compatibility Kit)
- API (JVM, JavaScript)
- Specifications for library developers

---

### Flow control

---

![inline](https://res.cloudinary.com/dqbo1a5ao/image/upload/v1484171492/presentations/akka-streams/04-backpressure.jpg)

---

![inline](https://res.cloudinary.com/dqbo1a5ao/image/upload/v1484171491/presentations/akka-streams/05-backpressure.jpg)

---

![inline](https://res.cloudinary.com/dqbo1a5ao/image/upload/v1484171498/presentations/akka-streams/06-backpressure.jpg)

---

![inline](https://res.cloudinary.com/dqbo1a5ao/image/upload/v1484171497/presentations/akka-streams/07-backpressure.jpg)

---

![inline](https://res.cloudinary.com/dqbo1a5ao/image/upload/v1484171492/presentations/akka-streams/08-backpressure.jpg)

---

![inline](https://res.cloudinary.com/dqbo1a5ao/image/upload/v1484171497/presentations/akka-streams/09-backpressure.jpg)

---

![inline](https://res.cloudinary.com/dqbo1a5ao/image/upload/v1484171498/presentations/akka-streams/10-backpressure.jpg)

---

![inline](https://res.cloudinary.com/dqbo1a5ao/image/upload/v1484171503/presentations/akka-streams/11-backpressure.jpg)

---

## An Rx-based approach to asyncrony

```java
public interface Processor<T, R> extends Subscriber<T>, Publisher<R> {}

public interface Publisher<T> {
    public void subscribe(Subscriber<? super T> s);
}

public interface Subscriber<T> {
    public void onSubscribe(Subscription s);
    public void onNext(T t);
    public void onError(Throwable t);
    public void onComplete();
}

public interface Subscription {
    public void request(long n);
    public void cancel();
}
```

![right fit](https://res.cloudinary.com/dqbo1a5ao/image/upload/v1484171497/presentations/akka-streams/12-flow-control.png)

---

# Interop

- **RxJava** (Netflix)
- **Reactor** (Pivotal)
- **Vert.x** (RedHat)
- **Akka Streams** (Lightbend)
- **Slick** (Lightbend)

![right fit](https://res.cloudinary.com/dqbo1a5ao/image/upload/v1484171498/presentations/akka-streams/13-interop.png)

---

# Reactive Streams

Visit the Reactive Streams website for more information.

[http://www.reactive-streams.org/](http://www.reactive-streams.org/)

---

# Akka Streams

---

# Akka Streams

- A library to express and run a chain of asynchronous processing steps acting on a sequence of elements
  - _DSL for async/non-blocking stream processing_
  - _Backpressure enabled by default_
  - _Implements the Reactive Streams spec for interoperability_
  - _Scala and Java APIs_

---

### Asynchronous boundary

---

![inline](https://res.cloudinary.com/dqbo1a5ao/image/upload/v1484171501/presentations/akka-streams/14-async-boundary.jpg)

---

![inline](https://res.cloudinary.com/dqbo1a5ao/image/upload/v1484171500/presentations/akka-streams/15-async-boundary.jpg)

---

## Basics

---

![inline](https://res.cloudinary.com/dqbo1a5ao/image/upload/v1484171499/presentations/akka-streams/17-graphs.jpg)

---

![inline](https://res.cloudinary.com/dqbo1a5ao/image/upload/v1484171500/presentations/akka-streams/18-graphs.jpg)

---

![inline](https://res.cloudinary.com/dqbo1a5ao/image/upload/v1484171500/presentations/akka-streams/19-graphs.jpg)

---

![inline](https://res.cloudinary.com/dqbo1a5ao/image/upload/v1484171500/presentations/akka-streams/20-graphs.jpg)

---

# Let's analyze flight data

1. Read in all flight data from 2008
2. Transform CSV rows to data structures
3. Compute the average delay per airline
4. Emit the results to console

---

![inline](https://res.cloudinary.com/dqbo1a5ao/image/upload/v1484171501/presentations/akka-streams/21-graphs.png)

---

```Scala
val g: RunnableGraph[_] = RunnableGraph.fromGraph(GraphDSL.create() {
    implicit builder =>

      // Source
      val A: Outlet[String] = builder.add(Source.fromIterator(() => flightDelayLines)).out
      val B: FlowShape[String, FlightEvent] = builder.add(csvToFlightEvent)
      val C: Inlet[Any] = builder.add(Sink.ignore).in

      import GraphDSL.Implicits._ // allows us to build our graph using ~> combinators

      // Graph
      A ~> B ~> C

      ClosedShape // defines this as a "closed" graph, not exposing any inlets or outlets
  })

  g.run() // materializes and executes the blueprint
```

---

# Materialization

Separates the _what_ from the _how_.

- Use Source/Flow/Sink to create a _blueprint_
- FlowMaterializer turns a blueprint into _Akka Actors_

![right fit](https://res.cloudinary.com/dqbo1a5ao/image/upload/v1484171501/presentations/akka-streams/22-actors.png)

---

## Graphs

---

![inline](https://res.cloudinary.com/dqbo1a5ao/image/upload/v1484171501/presentations/akka-streams/23-akka-streams.jpg)

---

![inline](https://res.cloudinary.com/dqbo1a5ao/image/upload/v1484171501/presentations/akka-streams/24-akka-streams.jpg)

---

![inline](https://res.cloudinary.com/dqbo1a5ao/image/upload/v1484171502/presentations/akka-streams/25-akka-streams.jpg)

---

![inline](https://res.cloudinary.com/dqbo1a5ao/image/upload/v1484171502/presentations/akka-streams/26-akka-streams.jpg)

---

## Advanced flow control

---

![inline](https://res.cloudinary.com/dqbo1a5ao/image/upload/v1484171502/presentations/akka-streams/27-akka-streams.jpg)

--- 

![inline](https://res.cloudinary.com/dqbo1a5ao/image/upload/v1484171502/presentations/akka-streams/28-akka-streams.jpg)

--- 

![inline](https://res.cloudinary.com/dqbo1a5ao/image/upload/v1484171503/presentations/akka-streams/29-akka-streams.jpg)

--- 

![inline](https://res.cloudinary.com/dqbo1a5ao/image/upload/v1484171492/presentations/akka-streams/30-akka-streams.jpg)

--- 

![inline](https://res.cloudinary.com/dqbo1a5ao/image/upload/v1484171493/presentations/akka-streams/31-akka-streams.jpg)

--- 

![inline](https://res.cloudinary.com/dqbo1a5ao/image/upload/v1484171495/presentations/akka-streams/32-akka-streams.jpg)

--- 

# Code and review

---

![inline](https://res.cloudinary.com/dqbo1a5ao/image/upload/v1484171493/presentations/akka-streams/33-RunnableGraph.png)

---

```scala
val g = RunnableGraph.fromGraph(GraphDSL.create() {
  implicit builder =>
    import GraphDSL.Implicits._

    // Source
    val A: Outlet[String] = builder.add(Source.fromIterator(() => flightDelayLines)).out

    // Flows
    val B: FlowShape[String, FlightEvent] = builder.add(csvToFlightEvent)
    val C: FlowShape[FlightEvent, FlightDelayRecord] = builder.add(filterAndConvert)
    val D: UniformFanOutShape[FlightDelayRecord, FlightDelayRecord] = builder.add(Broadcast[FlightDelayRecord](2))
    val F: FlowShape[FlightDelayRecord, (String, Int, Int)] = builder.add(averageCarrierDelay)

    // Sinks
    val E: Inlet[Any] = builder.add(Sink.ignore).in
    val G: Inlet[Any] = builder.add(Sink.foreach(averageSink)).in

    // Graph
    A ~> B ~> C ~> D
              E <~ D
         G <~ F <~ D

    ClosedShape
})

g.run()
```

---

![inline](https://res.cloudinary.com/dqbo1a5ao/image/upload/v1484171495/presentations/akka-streams/34-reactive-architecture.png)
