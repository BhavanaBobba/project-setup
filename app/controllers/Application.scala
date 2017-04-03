package controllers

import play.api.mvc._

class Application extends Controller {
  def index = Action {
    request => Ok(views.html.swagger())
  }
  def swagger = Action {
    request => Ok(views.html.swagger())
  }
}

object Application extends Application
