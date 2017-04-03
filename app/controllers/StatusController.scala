package controllers

import play.api.mvc.{Action, Controller}
import io.swagger.annotations.{ApiOperation, _}

@Api(value = "/status", description = "Server status")
class StatusController() extends Controller {
  @ApiOperation(nickname = "ping", value = "Ping Server", httpMethod = "GET", response = classOf[String])
  def ping = Action {
    Ok("Success")
  }
}

object StatusController extends StatusController()
