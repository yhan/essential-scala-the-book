import org.scalatest.{FlatSpec, Matchers}
import prod.CatTraining
import prod.implicitContext._

class ImplicitSpec extends FlatSpec with Matchers{

  "implicit" can "be infered from package implicit declaration" in {
      CatTraining.train shouldBe "OK"
  }
}
