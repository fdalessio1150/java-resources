package computerdatabase

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class PostMysqlPerfTest extends Simulation {

  val path = "/home/felipe/applications/python/data-generators/data/data-per-line.json"

  val feeder = jsonFile(path)

  val httpConf = http
    .baseUrl("http://localhost:8081/v1/") 
    .acceptHeader("application/json") 

  val scn = scenario("post-mysql-demo-scenario")
			.during(120 seconds) {
				feed(feeder)
				.exec(
					http("post-customer")
					.post("customer")
					.body(StringBody(
						"""{
							"id_pessoa":  "${id_pessoa}",
							"nome":  "${nome}",
							"id_endereço":  "${id_endereço}",
							"endereço_completo":  "${endereço_completo}",
							"estado":  "${estado}"
						}"""
						))
					.asJson)
			}	
	  
  setUp(scn.inject(rampUsers(20) during(5 seconds)))
          .throttle(reachRps(20) in (5 second), holdFor(2 minutes))
		      .protocols(httpConf)
}
