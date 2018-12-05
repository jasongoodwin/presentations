
import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class RecordedSimulation extends Simulation {

	val httpProtocol = http
		.baseURL("http://tv.giphy.com")
		.inferHtmlResources()
		.acceptHeader("*/*")
		.acceptEncodingHeader("gzip, deflate, sdch")
		.acceptLanguageHeader("en-US,en;q=0.8")
		.userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.95 Safari/537.36")

	val headers_0 = Map("Accept-Encoding" -> "gzip, deflate, sdch")

	val headers_1 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8",
		"Accept-Encoding" -> "gzip, deflate, sdch",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_3 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8",
		"Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_4 = Map(
		"Accept" -> "text/css,*/*;q=0.1",
		"Connection" -> "keep-alive")

	val headers_6 = Map("Connection" -> "keep-alive")

	val headers_11 = Map(
		"Connection" -> "keep-alive",
		"Origin" -> "https://redelastic.com")

	val headers_12 = Map(
		"Accept" -> "image/webp,image/*,*/*;q=0.8",
		"Connection" -> "keep-alive")

    val uri1 = "http://tv.giphy.com/v1/gifs/tv"
    val uri2 = "redelastic.com"

	val scn = scenario("RecordedSimulation")
		.exec(http("request_0")
			.get("/v1/gifs/tv?api_key=CW27AW0nlp5u0&tag=make%20it%20rain&internal=yes&callback=jQuery31104743456387994238_1484241997188&_=1484241997344")
			.headers(headers_0))
		.pause(1)
		.exec(http("request_1")
			.get("http://" + uri2 + "/")
			.headers(headers_1))
		.pause(3)
		.exec(http("request_2")
			.get("/v1/gifs/tv?api_key=CW27AW0nlp5u0&tag=make%20it%20rain&internal=yes&callback=jQuery31104743456387994238_1484241997188&_=1484241997345")
			.headers(headers_0)
			.resources(http("request_3")
			.get("https://" + uri2 + ":443/")
			.headers(headers_3),
            http("request_4")
			.get("https://" + uri2 + ":443/css/uikit.min.css")
			.headers(headers_4),
            http("request_5")
			.get("https://" + uri2 + ":443/css/components/slideshow.min.css")
			.headers(headers_4),
            http("request_6")
			.get("https://" + uri2 + ":443/js/uikit.min.js")
			.headers(headers_6),
            http("request_7")
			.get("https://" + uri2 + ":443/js/jquery-2.2.1.min.js")
			.headers(headers_6),
            http("request_8")
			.get("https://" + uri2 + ":443/js/components/sticky.js")
			.headers(headers_6),
            http("request_9")
			.get("https://" + uri2 + ":443/js/components/slideshow.min.js")
			.headers(headers_6),
            http("request_10")
			.get("https://" + uri2 + ":443/js/components/slider.min.js")
			.headers(headers_6),
            http("request_11")
			.get("https://" + uri2 + ":443/fonts/fontawesome-webfont.woff2")
			.headers(headers_11),
            http("request_12")
			.get("https://" + uri2 + ":443/favicon.ico")
			.headers(headers_12)))
		.pause(2)
		.exec(http("request_13")
			.get("/v1/gifs/tv?api_key=CW27AW0nlp5u0&tag=make%20it%20rain&internal=yes&callback=jQuery31104743456387994238_1484241997188&_=1484241997346")
			.headers(headers_0))
		.pause(4)
		.exec(http("request_14")
			.get("/v1/gifs/tv?api_key=CW27AW0nlp5u0&tag=make%20it%20rain&internal=yes&callback=jQuery31104743456387994238_1484241997188&_=1484241997347")
			.headers(headers_0))

	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}