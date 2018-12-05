import com.redelastic.example.Metrics
import smoke._
import com.typesafe.config.ConfigFactory

object MetricsExampleApp extends App {
  val smoke = new BasicExampleSmoke
}

class BasicExampleSmoke extends Smoke {
  val smokeConfig = ConfigFactory.load().getConfig("smoke")
  val executionContext = scala.concurrent.ExecutionContext.global

  onRequest {
    case GET(Path("/timer")) ⇒
      Metrics.ExampleDomain.withTimeMetric{ () => reply {
        Thread.sleep(1000)
        Metrics.ExampleDomain.reportSuccess
        Response(Ok, body = "It took me a second to build this response.\n")
      }}
    case GET(Path("/increment-gauge")) ⇒ reply {
      Metrics.ExampleDomain.incrementGauge
      Response(Ok, body = "Incremented gauge.\n")
    }
    case GET(Path("/decrement-gauge")) ⇒ reply {
      Metrics.ExampleDomain.decrementGauge
      Response(Ok, body = "Decremented gauge.\n")
    }
    case _ ⇒
      Metrics.ExampleDomain.report404
      reply(Response(NotFound))
  }
}
