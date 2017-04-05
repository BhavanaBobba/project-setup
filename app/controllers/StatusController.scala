package controllers

import javax.inject.Inject

import com.sun.media.sound.InvalidDataException
import data.StatusDao
import play.api.mvc.{Action, Controller}
import io.swagger.annotations._
import models.Add
import play.api.libs.json._

@Api(value = "/status", description = "Server status")
class StatusController @Inject()(statusDao: StatusDao) extends Controller {
  @ApiOperation(nickname = "ping", value = "Ping Server", httpMethod = "GET", response = classOf[String])
  def ping = Action {
    Ok("Success")
  }

  @ApiOperation(nickname = "addingTwoNumbers", value = "Adds two Numbers", httpMethod = "POST", response = classOf[String])
  @ApiImplicitParams(Array(new ApiImplicitParam(name = "add", value = "Provide two numbers",
    dataType = "models.Add", required = true, paramType = "body")))
  def addingTwoNumbers() = Action { request => {

      val valuesToAdd = request.body.asJson.get.as[Add]
      println("Here")
      Ok(Json.toJson(statusDao.addingTwoNumbers(valuesToAdd)))
  }
  }

}

