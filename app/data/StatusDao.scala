package data

import models.Add

/**
  * Created by venkatasaiharsharavuri on 4/4/17.
  */
class StatusDao {

  def addingTwoNumbers(add: Add): Int = {
    println("firstNumber" + add.firstNumber)
    add.firstNumber + add.secondNumber
  }
}
