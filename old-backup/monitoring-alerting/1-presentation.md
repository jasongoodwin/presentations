theme: Letters from Sweden, 2
slidenumbers: true
autoscale: true

# Monitoring and Alerting

**Questions?**

Contact your instructors at:

_W:_ http://redelastic.com

_E:_ jason.goodwin@redelastic.com

_E:_ kevin.webber@redelastic.com

---

# Agenda: See inside your systems
- Monitoring and Alerting
- Infrastructure
- Loadbalancers
- Application Instance
- Logs
- Traces

---

# Monitoring and Alerting

---

# A day in the life of an unmonitored app
- System is running
- Exceptional condition occurs
- Customer notifies you via bad review

---

# Step 1: Monitoring
- Collecting, processing, aggregagating, and displaying _real-time qualtitative data_ about a system
  - Query/Error counts and types
  - Processing times
  - Server health

---

# Step 2: Alerting
- notification intended to be read by a human and that is pushed to a system
- Alerts as records
  - low severity - may be read
- Alerts as notifications
  - mid severity - eventually respond
- Alerts as pages
  - high severity - urgent

---

# White Box/Black Box
- Alerting exists across layers
- Blackbox is Symptoms
  - Serving 500s
  - Catch-alls
- Whitebox is Cause
  - Redis connection failed
  - Require knowledge of the case

---

# Monitoring Across Layers

---

# Stats Collection and Dashboards
- Ideally one location gets all of the data
- All alerting, monitoring comes from that one view
- In practice, may require multiple tools


---

![fit left](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1484415581/Screen_Shot_2017-01-14_at_12.38.46_PM_q1kcqw.png)
# Load Balancers
- AWS, GCP Loadbalancers Log Everything
  - Healthy Hosts
  - 5xx count
  - Response times
- Services like datadog can collect metrics

---

![fit left](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1484415474/Screen_Shot_2017-01-14_at_12.36.09_PM_vpxloj.png)
# Infrastructure
- Agent-based
  - Google's Stackdriver
  - Datadog
  - Often faster, better resolution
- Agentless
  - SNMP
  - SSH
  - Cloud metrics apis

---

# Logs
- Aggregate logs to aid in analysis
  - ELK stack
  - Stackdriver Logging
- Can alert (whitebox)


---

# API Monitoring Monitoring
- Build test flows based on real-user use-cases
  - Runscope is flexible, lots of integrations
- Can identify broken integrations and flows
- Last Mile is Noisy (notifications, not pages)

---

# Instrumented JVM
- OpsClarity has a custom JVM
  - Efficient
  - Significant insight with minimal instrumentation
  - $20/host - expensive but doesn't require instrumentation

---

# Application Metrics (will discuss shortly)
- Requires Instrumentation
  - Statsd w/ Datadog (demo to follow)
  - Codahale's metrics
- Internal view into application behavior
  - Cache hits/misses
  - Active sessions
- Excellent for alerting

---

# Traces (will discuss shortly)
- Distributed debug logs
  - Google's Dapper paper
- Aids in performance analysis
- Can aid support

---

# Recommendations
- Get the cheap wins in place (infra/lb monitoring)
- Instrument apps for metrics asap
- Apply user flow api-monitoring eventually
- Build a long term plan for tracing infrastructure
- Apply alerting as cross-cutting-concern

---

# Instrumenting Apps With Metrics

---

![fit left](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1484424907/Screen_Shot_2017-01-14_at_3.14.09_PM_pmcjts.png)
# Requirements
- Tool for collecting metrics
- Backend to receive metrics

---

# Example: statsd -> datadog
- statsd as instrumentation
- datadog as statsd backend

``` scala
libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "2.2.4" % "test" ,
  "com.datadoghq" % "java-dogstatsd-client" % "2.3" exclude("org.slf4j","slf4j-log4j12"),
)
```

---

# Reporting Counters
- Calculate occurance of events

``` scala
    def report404: Unit = {
      statsdClient.count("error", 1, {"404"})
    }
    def reportSuccess: Unit = {
      statsdClient.count("success", 1)
    }
```

---

![fit left](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1484437373/Screen_Shot_2017-01-14_at_6.42.17_PM_ewx3ch.png)
# Counters in Dashboards
- Describe rates of events
  - Success/Error
  - Cache hits/misses

---

# Reporting Gauges
- Report state

``` scala
    var gaugeValue = 0

    def incrementGauge: Unit = {
      gaugeValue = gaugeValue + 1
      statsdClient.gauge("gauge1", gaugeValue)
    }

    def decrementGauge: Unit = {
      gaugeValue = gaugeValue - 1
      statsdClient.gauge("gauge1", gaugeValue)
    }

```

---

![fit left](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1484438522/Screen_Shot_2017-01-14_at_7.01.25_PM_esmvsn.png)
# Gauges in Dashboard
- Describes states
  - Services running
  - Number of live user sessions
  - Service connections (1 vs 0)

---

# Reporting Execution Time
- Count the duration

``` scala
    def withTimeMetric[T](f: () => T) = {
      val startTime = (new Date).getTime
      val res = f.apply()
      val timeTaken = (new Date).getTime - startTime

      statsdClient.recordExecutionTime("response-time", timeTaken)

      res
    }
```

---

![fit left](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1484439708/Screen_Shot_2017-01-14_at_7.21.07_PM_hja7tw.png)
# Execution Time in Dashboard
- Time is viewed as statistics. eg:
  - max
  - mean
  - 95th percentile
  - aveage

---

# Tracing

---

# Landscape Today
- Metrics, Logging, not complete view
- Query could hit dozens of services
- Need to see into a system as a whole
  - Common to use a correlation guid
  - Effort required to "trace" in Log Aggregation
  - Metrics not whole view

---

![fit left](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1484491614/Screen_Shot_2017-01-15_at_9.46.04_AM_eqsgm6.png)
# Problem
- A query can hit systems and subsystems
  - Hard to visualize
  - Manual correlation (search ELK for ID)
- Has to scale
  - Log aggregation at DEBUG level hard to manage
  - Log aggregation at ERROR level insufficient

---

# Dapper, a Large-Scale Distributed Systems Tracing Infrastructure
- Google paper on tracing infrastructure
  - application-level transparency
  - ubiquitous deployment
  - efficient

> Ads Review team estimates that their latency numbers have improved by two orders of magnitude using data gleaned from the Dapper tracing platform.

---

# Uses
- Support
- Outlier analysis
- Network quality impact (outliers)
- Problematic combinations of queries

---

# Dapper Implementations
- Zipkin
  - Java-based
  - Many instrumentations complete

---

# Ubiquitous Deployment
- Requires instrumenting all used communication libraries
  - Akka (and related tools)
  - HTTP Client
  - HTTP Server
  - Cassandra Client
  - Zookeeper Client
  - Kafka Client

---

# And Logs (Annotations)
- Instrument application logging as well
- Logs tag along with the traces to give information
- run DEBUG level in prd

---

![fit inline](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1484522881/Screen_Shot_2017-01-15_at_4.38.42_PM_ob71id.png)
# Spans
- crossing a service

---

![fit inline](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1484537660/Screen_Shot_2017-01-15_at_10.33.32_PM_esd6jq.png)
# Spans for insight
- logs tacked onto spans (aka "Annotations")

---

# Traces
- Spans are sent with ID/Parent Id
- IDs are correlated to a sigle trace in zipkin

---


![fit inline](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1484596494/Screen_Shot_2017-01-16_at_2.53.06_PM_gjeclo.png)

---

# Efficiency
- a fraction of request traces are logged
  - eg 1 in 1000

``` scala
  tracing {
    host = "localhost"
    port = 9410
    sample-rate = 1000
  }
```

---

# Real-time insight
- No need to switch to DEBUG
- Always be collecting information

---

![fit left](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1484588358/Screen_Shot_2017-01-16_at_12.38.33_PM_y58lbp.png)
# Querying
- Placing annotations allows one to query on data
  - Annotation take form of keys and values

---

# Akka Instrumentation

``` scala
case class Command(id: String) extends TracingSupport
case class Ack(id: String) extends TracingSupport

class SpanActor extends Actor with ActorTracing {
  def receive = {
    case msg @ Command(id) =>
      //start trace
      trace.sample(msg, "WebActor")
      //annotate
      trace.recordKeyValue(msg, self.path.name, label)

      val ack = Ack(id)
      //respond w/ correct span hierarchy
      sender ! ack.asResponseTo(msg)
  }
}

spanActor ? Command(reqId)
```

---

# Tracing Ecosystem
- APIs can be opaque (especially Zipkin)
- Documentation for most platforms is lacking
- Akka instrumentation relatively complete
- Highly valuable to instrument/open source

---

# Alternatives
- OpenTracing client lib
  - abstracts away zipkin's api
  - gives support for different backends
- Jaeger
  - used by uber
- Appdash
    - used by sourcegraph
