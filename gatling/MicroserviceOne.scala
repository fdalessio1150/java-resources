package computerdatabase

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class MicroserviceOne extends Simulation {

  val httpConf = http
    .baseUrl("http://localhost:8080/v1/") 
    .acceptHeader("application/json") 

  val scn = scenario("MicroserviceOne")
			.during(120 seconds) {
				exec(http("post_clients")
				.post("clients")
				.body(StringBody("""{"id": "ab9ca49a-2fb5-4fd3-84ec-4095b560058f", "nome": "Felipe"  }"""))
				.asJson)
			}
	  
  setUp(scn.inject(rampUsers(100) during(10 seconds)))
		.throttle(reachRps(60) in (10 second)
				  ,holdFor(2 minutes)
				  )
		.protocols(httpConf)
}

