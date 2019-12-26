import org.scalatest.{FlatSpec, Matchers}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Await, Future}
import scala.concurrent.duration._


class FutureSpec extends FlatSpec with  Matchers {
    behavior of "Future"
    it should "not block the next operation" in {
        printThread("Main")
        val fut = Future {
            printThread("Pool")
            Thread.sleep(1000); 21 + 21 }
        println(s"ended =  ${fut.isCompleted}")

        Thread.sleep(1000)
        printFuture(fut) // TODO HOw to be notified when future completed??
    }

    it should "handle exception" in {
        val wait = 20
        val fut = Future { Thread.sleep(wait); 21 /0 }
        println(s"ended =  ${fut.isCompleted}")

        Thread.sleep(wait*2)
        printFuture(fut)
    }

    private def printFuture(fut: Future[Int]) = {
        println(s"ended =  ${fut.isCompleted}, result = ${fut.value}")
    }

    it should "execute in parallel using for" in {
//        Because for expressions serialize their transformations,[3] if you don't create the futures before
//        the for expression, they won't run in parallel.

        val fut1 = Future {
            printThread("first")
            Thread.sleep(100); 21 + 21 }

        val fut2 = Future {
            printThread("second")
            Thread.sleep(100); 23 + 23 }

        val future = for {
            x <- fut1
            y <- fut2
        } yield x + y

        Thread.sleep(1)
        future.isCompleted shouldBe false
    }

    def printThread(label: String): Unit = println(s"[${Thread.currentThread().getId}] $label")

    it should "can be awaited" in {
        val fut = Future { Thread.sleep(20); 21 + 21 }
        val result = Await.result(fut, 100.millisecond)
        println(s"result is $result")

        result shouldBe 42
    }

    "" should "" in {
      val fut = Future {         throw new Exception       }
//val fut = Future { Thread.sleep(10000); 21 + 21 }
      fut.isCompleted shouldBe false
      fut.value shouldBe None
    }
}


