import org.scalatest.{FlatSpec, FunSuite, Matchers}
import prod.{Cat, OperandTester}


class CatSpec extends FlatSpec with Matchers{
    /*
        Define an object ChipShop with a method willServe. This method should
accept a Cat and return true if the cat’s favourite food is chips, and false
otherwise.

     */

    "Chip shop " should "serve chips for cats whose favorite food is chips " in {
        val oswald = new Cat("Black", "Milk")
        val henderson = new Cat("Ginger", "Chips")
        val quentin = new Cat("Tabby and white", "Curry")

        ChipShop.willServe(oswald) shouldBe false
    }
}