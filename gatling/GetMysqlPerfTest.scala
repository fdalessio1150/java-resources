 
package computerdatabase

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class GetMysqlDemoPerfTest extends Simulation {

  val httpConf = http
    .baseUrl("http://localhost:8081/v1/") 
    .acceptHeader("application/json")
    .acceptEncodingHeader("gzip, deflate")

  val scn = scenario("get-mysql-demo-scenario")
            .during(60 seconds) {
              exec(http("get-customer")
              .get("customer"))
            }
	  
  setUp(scn.inject(rampUsers(1) during(5 seconds)))
          .throttle(reachRps(5) in (5 second), holdFor(1 minutes))
		      .protocols(httpConf)
}