package controller

import controllers.StatusController
import org.scalatest.{BeforeAndAfter, FunSpec, Matchers}
import org.scalatest.mock.MockitoSugar
import play.api.test.FakeRequest
import play.api.test.Helpers._

/**
  * Created by bbobb on 3/17/17.
  */
class StatusControllerSpecs extends FunSpec with Matchers with BeforeAndAfter with MockitoSugar {

  val service: StatusController = new StatusController()
  val fakeRequestGet = FakeRequest(GET, "/")

  describe("StatusControllerSpec") {

    it("shows the application is up and running"){
      val response = contentAsString(service.ping().apply(fakeRequestGet))
      response should be ("Success")
    }

  }
}