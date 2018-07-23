package computerdatabase

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class AdminSimulation extends Simulation {

  val httpConf = http
    .baseURL("http://localhost:8080/clients") 
    .acceptHeader("application/json")
    .doNotTrackHeader("1")
    .acceptEncodingHeader("gzip, deflate")
    .userAgentHeader("Mozilla/5.0 (Windows NT 5.1; rv:31.0) Gecko/20100101 Firefox/31.0")

  val scn = scenario("AdminSimulation")
		.during(180 seconds) {
//		.repeat(100) {
    exec(http("get_request_clients")
      .get("/"))
	}

  setUp(
    scn.inject(rampUsers(2000) over (20 seconds))
  ).protocols(httpConf)
}