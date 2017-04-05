package models

import play.api.libs.json.Json

/**
  * Created by venkatasaiharsharavuri on 4/4/17.
  */
case class Add(firstNumber: Int, secondNumber: Int)

object Add {
  implicit val addJsonFormat = Json.format[Add]
}
