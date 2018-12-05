theme: Letters from Sweden, 2
slidenumbers: true

# Performance testing

**Questions?**

Contact your instructors at:

_W:_ http://redelastic.com

_E:_ jason.goodwin@redelastic.com

_E:_ kevin.webber@redelastic.com

---

# Agenda
- Performance Testing Basics
- Levels of Testing
- Utilizing Gatling for Performance Testing
- Performance Analysis

---

# Basics

---

# Why Performance Test
- Acquisition of knowledge as to the rate limits of a component or platform
- Identification of bottlenecks
- Iterative improvements to code/configuration/infrastructure
- Shake out errors under load (e.g. ulimits, leaks)

---

# Rate Limits
- often described as
  - requst per second
  - qps: queries per second
  - tps: transaction per second
- the highest rate a service can handle
- beyond this rate requests queue (increased latency)

---

# Making Changes
- Premature Optimization is the Evil
  - Cost of complexity > cost of servers
  - Don't make decisions without measurement
- Measure every performance change to verify the effect
  - Each change's impact should be verified

---

# Long Tests (Soak)
- Run with traffic over time
- Instability/degradation can arise
- Memory leaks will surface

---

# Fast Tests
- Measure for rate limits
- Determine failures that occur at high rates
- Vary data to avoid compiler/runtime optimizations

---

# Realistic Tests
- Test in a system as close to production as possible
- Will be necessary to test with a cluster of machines
- Immutable servers preferrable to avoid human error/config drift
  - terraform.io

---

# Network has limits
- Testing one service may hide network bottlenecks
- Use CPU-friendly compression (Snappy, LZ4)
  - Reduces latency
  - Moves network bandwidth boundaries

---

![fit](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1484166265/Screen_Shot_2017-01-11_at_3.23.43_PM_ahx4ro.png)

---

# Levels of Testing

---

# Individual Microservices
- Test at the service API
- Hides network limitation
- Hides datastore limitations
- Useful for identifying bottlenecks in a single service

---

# Platform Testing
- Generating realistic load is challenging
- Goal is to discover platform limits and raise them
- Platform should be at production scale

---

# Goals and Tradeoffs
- Testing is complex and expensive
- Main goal: Find major bottlenecks, remove them
- Budget for Goals: Producing realistic tests is expensive
  - Less realistic tests means more test/improve cycles
  - 80/20 rule
- Scale out a slow component to remove bottleneck if not easy fix

---

![fit left](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1484229924/Screen_Shot_2017-01-12_at_9.04.36_AM_quxhx3.png)
# Goal: Itentify Ratios of Services
- Determine capacity of components relative to one another
  - Slowest component is 1:1
  - All other components measured in relation to that component (eg 1:10)
  - Allows to scale up/down all components without one bottleneck

---

# Tools of the Trade

---

# Gatling for Scala Teams
- async, non-blocking http (asyncHttpClient, netty)
  - allows greater load to be applied with fewer resources
- simulations are purely scala
- generates reports
- can be operated from command-line for Continuous Delivery

---

# But I Already Know JMeter
- JMeter uses a thread per request
  - Context switches limit parallelism

---

![fit left](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1484250064/Screen_Shot_2017-01-12_at_2.40.28_PM_fhd9jq.png)
# See Inside the JVM
- VisualVM to identify bottlenecks and leaks
  - Order by time in method
  - Order by size in memory
- Profiling affects performance

---

# Testing Services with Gatling

---

# Download Gatling Zip Bundle
- http://gatling.io/#/resources/download

---

# Simulations
- located in user-files/simulations
- scala scripts are placed here

---

# Recording Real User Sessions
- Can produce with Gatling Recorder
  - ./bin/recorder.sh
- Acts as a proxy between browser and service
- Generates and saves scala simulations
- Produces more realistic sessions faster
- Can be modified by hand to add variable data

---

# Custom Scripting
- copy imports from example script
- place in _./user-files/simulations/my-simulation.scala_

``` scala
package com.redelastic

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class BasicSimulation extends Simulation {

}
```

---

# Common Params
- httpConf can be used to eliminate duplication
- describe base url and headers for all requests

``` scala
val httpConf = http
    .baseURL("http://www.redelastic.com")
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
```

---

# Build Scenario
- builder pattern produces each request in the scenario

``` scala
val scn = scenario("RedElasticSimulation")
  .exec(http("index")
    .get("/index.html")
    .headers(Map("Header" -> "value")
    .check(status.is(200)) //verify the result is as expected
  .pause(1)
  .exec((http("another")
    .[...])
```

---

# Varying Data
- can feed sheets of user data in via csv
- then can use variables in scenarios

```scala
first, last /*titles used in interpolation*/
jay, goodwin
kevin, webber

.feed(csv("file.csv"))
  .exec(http("/user/$$last-$first"))
```

---

# Conditionals and Loops
- Can repeat events
- Can add conditional logic
  - Can use RNGs or conditional on feed

``` scala
.doIf("${firstname}", "jason") {
  repeat(10){
    exec/...
  }
}
```

---

# Simulation Definition
- Finally define number of users and ramp time
- One time use per user
  - 1000 users over 100 seconds = 10 users/s
- Put the httpConf in that was defined earlier (baseUrl, etc)

``` scala
  setUp(scn.inject(rampUsers(1000) over (100 seconds))).protocols(httpConf)
```

---

# Our Example Test

``` scala
class RedElasticSimulation extends Simulation {
  val httpConf = http
    .baseURL("http://www.redelastic.com")

  val scn = scenario("RedElasticSimulation")
    .exec(http("index")
      .get("/index.html")
      .check(status.is(200)))
      .pause(2)

  setUp(scn.inject(rampUsers(2) over (2 seconds))).protocols(httpConf)
}
```

---

![inline fit](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1484248565/Screen_Shot_2017-01-12_at_2.15.21_PM_v80m1s.png)
# Running
- Run ./bin.gatling.sh
- Select your test from the list
- Will give information about running test progress

---

![fit](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1484247228/Screen_Shot_2017-01-12_at_1.53.13_PM_bnen6i.png)
# Viewing Reports
- After completing, a report will be generated with views into the data collected

---

![fit left](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1484249716/Screen_Shot_2017-01-12_at_2.34.32_PM_l2n3ew.png)
# Finding QPS limits
- Apply more concurrency until latency increases
- Back off until latency is low
- Record QPS at point before performance degrades

---

# Scaling Out
- Gatling has no co-ordinated cluster capability
- Run gatling on several servers
- Collect together simulation.log files
- Run gatling in ReportOnly mode to produce a consolidated report

---

# Community Extensions
- TCP extension
  - Can test any protocol theoretically
- Kafka Producer Extension
  - Stress test with Kafka in the picture

---

# Testing in Continual Delivery Pipeline
- Can produce a report on each build/deploy cycle
- Fake services increase isolation

---

# QA
