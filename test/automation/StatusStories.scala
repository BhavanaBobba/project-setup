package automation

import org.apache.http.client.methods.HttpGet
import org.jbehave.core.annotations.When
import org.jbehave.core.configuration.scala.ScalaContext
import org.jbehave.core.io.{CodeLocations, StoryFinder}
import org.jbehave.core.steps.InjectableStepsFactory
import org.jbehave.core.steps.scala.ScalaStepsFactory
import play.api.libs.json.Json

/**
  * Created by bbobb on 3/17/17.
  */
class StatusStories extends Stories {

  override def storyPaths(): java.util.List[String] = {
    new StoryFinder().findPaths(CodeLocations.codeLocationFromClass(this.getClass()), "/stories/Status*.story", "")
  }

  override def stepsFactory(): InjectableStepsFactory = {
    new ScalaStepsFactory(configuration(), new ScalaContext(classOf[StatusSteps].getName))
  }

}

class StatusSteps extends WebAppSteps {

  private val url = baseUrl + "/status"

  @When("I call get application status")
  def callRunAnalysisJobs(): Unit = {
    val get = new HttpGet(url)
    executeNext(get)
  }
}