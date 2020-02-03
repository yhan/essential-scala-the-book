import cats.effect.{IO, _}
import cats.implicits._
import org.scalatest.{FlatSpec, Matchers}

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

// Reference: https://typelevel.org/cats-effect/datatypes/io.html

class IOSpec extends FlatSpec with Matchers {
  behavior of "cats.effect.IO"
  it can "safely composable with another IO" in {
    val io: IO[Int] = IO.pure(25)
    io.flatMap(n => IO(println(s"Number is: $n")))
  }

  it can "apply" in {

    def putStrlLn(value: String) = IO(println(value))
    val readLn = IO(scala.io.StdIn.readLine)

    val ioUnit: IO[Unit] = for {
      _ <- putStrlLn("What's your name?")
      n <- readLn
      _ <- putStrlLn(s"Hello, $n!")
    } yield ()
  }

  behavior of "IO.pure"
  it can "not suspend side effect" in {
    IO.pure(println("*** THIS IS WRONG! ***"))
  }

  // Convert Future to IO
  def convert[A](fa: => Future[A])(implicit ec: ExecutionContext): IO[A] =
    IO.async { cb =>
      // This triggers evaluation of the by-name param and of onComplete,
      // so it's OK to have side effects in this callback
      fa.onComplete {
        case Success(a) => cb(Right(a))
        case Failure(e) => cb(Left(e))
      }
    }
}

class ContextShiftSpec extends FlatSpec with Matchers {
  behavior of "shift"
  it should "" in {}

  def fib[F[_]](n: Int, a: Long = 0, b: Long = 1)(
    implicit F: Sync[F],
    cs: ContextShift[F]
  ): F[Long] = {

    F.suspend {
      val next = if (n > 0) fib(n - 1, b, a + b) else F.pure(a)

      // Triggering a logical fork every 100 iterations
      if (n % 100 == 0) cs.shift *> next else next
    }
  }
}
