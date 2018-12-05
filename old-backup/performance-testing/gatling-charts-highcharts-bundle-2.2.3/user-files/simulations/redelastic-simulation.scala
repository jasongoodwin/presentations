package computerdatabase

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class RedElasticSimulation extends Simulation {
  val httpConf = http
    .baseURL("http://www.redelastic.com")
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")

  val scn = scenario("RedElasticSimulation")
    .exec(http("index")
      .get("/index.html")
      .headers(Map("Header" -> "value"))
      .check(status.is(200)))
      .pause(2)
  setUp(scn.inject(rampUsers(2) over (2 seconds))).protocols(httpConf)
}
