import org.scalatest.{FlatSpec, Matchers}
import prod.OperandTester

class MiscSpec extends FlatSpec with Matchers {
    "==" should "be able to compare operands of different types" in {
        OperandTester.equalOperator shouldBe false
    }
}
