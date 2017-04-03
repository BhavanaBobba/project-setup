package automation

import java.io.File
import java.util

import com.mohaine.util.StreamUtils
import org.apache.http.client.methods._
import org.apache.http.impl.client.HttpClientBuilder
import org.jbehave.core.annotations.{BeforeScenario, Given, Then}
import org.jbehave.core.configuration.{Configuration, MostUsefulConfiguration}
import org.jbehave.core.failures.FailingUponPendingStep
import org.jbehave.core.junit.JUnitStories
import org.jbehave.core.reporters.{Format, StoryReporterBuilder}
import org.junit.Assert._
import play.api.{Environment, ApplicationLoader}
import play.api.ApplicationLoader._
import play.core.server.NettyServer

abstract class Stories extends JUnitStories {
  override def configuration(): Configuration = {
    new MostUsefulConfiguration()
      .useStoryReporterBuilder(new StoryReporterBuilder().withFormats(Format.ANSI_CONSOLE, Format.STATS, Format.HTML))
      .usePendingStepStrategy(new FailingUponPendingStep())

  }
  configuredEmbedder().embedderControls().doGenerateViewAfterStories(true)
    .doIgnoreFailureInView(true).useThreads(1)
  configuredEmbedder().useMetaFilters(util.Arrays.asList("-skip"))
}

object Server {
  var server: Option[NettyServer] = None

}

class WebAppSteps {

  val client = HttpClientBuilder.create().build()
  val status = new Status()
  val baseUrl = "http://localhost:9000/project-setup/services"

  trait Response {
    def contentAsString: String
    def statusCode: Int
    def close: Unit
  }

  class Status() {
    var lastResponse: Option[Response] = None
  }

  @BeforeScenario
  def writeStoryDurations(): Unit = {
    // JBehave fails if this file is missing....
    val file = new File("target/jbehave/storyDurations.props")
    if (!file.exists()) {
      file.getParentFile.mkdirs()
      file.createNewFile()
    }
  }

  @Given("the server is started")
  def serverStarted(): Unit = {
    if (Server.server.isEmpty) {

      Server.server = startWebServer
    }
  }


  @Then("the response code should be $code")
  def validateResponseCode(code: Int) {
    assertNotNull(code)
    assertTrue("Have a response", status.lastResponse.isDefined)
    assertEquals(s"Response code should have been '$code'", code, status.lastResponse.get.statusCode)
  }

  @Then("the response text should contain $text")
  def validateResponseTextContain(text: String) {

    var success = false

    try {
      assertNotNull(text)
      assertTrue("Have a response", status.lastResponse.isDefined)
      //    println(status.lastResponse.get.contentAsString)
      assertTrue(s"Should contain '$text'", status.lastResponse.get.contentAsString.contains(text))
      success = true
    } finally {
      if (!success) {
        println("***************************************")
        println(status.lastResponse.get.contentAsString)
        println("***************************************")
      }
    }
  }

  @Then("the response text should not contain $text")
  def validateResponseTextNotContain(text: String) {
    assertNotNull(text)
    assertTrue("Have a response", status.lastResponse.isDefined)
    assertFalse(s"Should contain '$text'", status.lastResponse.get.contentAsString.contains(text))
  }

  protected def executeNext(req: HttpUriRequest): Unit = {
    if (status.lastResponse.isDefined) {
      status.lastResponse.get.close
    }
    val response: CloseableHttpResponse = client.execute(req)
    status.lastResponse = Some(new Response() {
      override lazy val contentAsString: String = {
        new String(StreamUtils.readStream(response.getEntity.getContent))
      }

      override def statusCode: Int = {
        response.getStatusLine.getStatusCode
      }

      override def close: Unit = {
        response.close()
      }
    })

  }

  class Dummy{}
  def startWebServer = {
    val environment = new Environment(
    new File("."),
        classOf[Dummy].getClassLoader,
        play.api.Mode.Dev
    )
    val context = createContext(environment)
    println("in server start")
    val application = ApplicationLoader(context).load(context)

    play.api.Play.start(application)

    Option(play.core.server.NettyServer.fromApplication(application))
  }

}
