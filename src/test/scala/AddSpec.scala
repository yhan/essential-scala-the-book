import org.scalatest.AsyncFunSpec

import scala.concurrent.Future

class AddSpec extends AsyncFunSpec {
    def addSoon(addends: Int*): Future[Int] =
        Future {
            Thread.sleep(1)
            addends.sum }

    describe("addSoon") {
        it("will eventually compute a sum of passed Ints") {
            val futureSum: Future[Int] = addSoon(1, 2)
            // You can map assertions onto a Future, then return
            // the resulting Future[Assertion] to ScalaTest:
            futureSum map { sum => assert(sum == 3) }
        } }
}
