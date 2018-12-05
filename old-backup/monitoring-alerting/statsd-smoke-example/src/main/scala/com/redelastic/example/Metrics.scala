package com.redelastic.example

import java.util.Date

import com.timgroup.statsd.{NoOpStatsDClient, NonBlockingStatsDClient}
import com.typesafe.config.ConfigFactory

/**
  * This applicaiton shows instrumentation of a simple REST server:
  * 1) Metrics w/ statsd and datadog
  * 2) Zipkin for tracing across "spans" in a distributed application
  */

object Metrics {
  val config = ConfigFactory.load()

  val statsdClient = config.getString("env") match {
    case "test" =>
      new NoOpStatsDClient
    case _ =>
      new NonBlockingStatsDClient(
        config.getString("service.name"),
        config.getString("statsd.backend-host"),
        config.getInt("statsd.backend-port")
      )
  }

  object ExampleDomain {
    def report404: Unit = {
      statsdClient.count("error", 1, {
        "404"
      })
    }

    def reportSuccess: Unit = {
      statsdClient.count("success", 1)
    }

    var gaugeValue = 0

    def incrementGauge: Unit = {
      gaugeValue = gaugeValue + 1
      statsdClient.gauge("gauge1", gaugeValue)
    }

    def decrementGauge: Unit = {
      gaugeValue = gaugeValue - 1
      statsdClient.gauge("gauge1", gaugeValue)
    }

    def withTimeMetric[T](f: () => T) = {
      val startTime = (new Date).getTime
      val res = f.apply()
      val timeTaken = (new Date).getTime - startTime

      statsdClient.recordExecutionTime("response-time", timeTaken)

      res
    }
  }

}
