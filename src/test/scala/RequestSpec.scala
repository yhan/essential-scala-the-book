import org.scalatest.{FlatSpec, Matchers}

class RequestSpec extends  FlatSpec with Matchers {
    behavior of "Request"
    it should "be able to copy part of values of its properties" in {

        val notChangedName = "world"

        val request = Request(0, notChangedName).copy(42)

        request.idValue shouldBe 42
    }
}


