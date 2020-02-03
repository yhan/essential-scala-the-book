import org.scalatest.{FlatSpec, Matchers}
import cats._
import cats.effect._
import cats.implicits._
import doobie._
import doobie.implicits._
import doobie.util.ExecutionContexts
import doobie.util.transactor.Transactor.Aux

class MyFirstDoobieProgram extends FlatSpec with Matchers {

  behavior of "basic"
  it should "run and get the initial value" in {

    val program1 = 42.pure[ConnectionIO]
    // program1: ConnectionIO[Int] = Pure(42)


    // We need a ContextShift[IO] before we can construct a Transactor[IO]. The passed ExecutionContext
    // is where nonblocking operations will be executed. For testing here we're using a synchronous EC.
    implicit val cs = IO.contextShift(ExecutionContexts.synchronous)

    // A transactor that gets connections from java.sql.DriverManager and executes blocking operations
    // on an our synchronous EC. See the chapter on connection handling for more info.
    val xa: Aux[IO, Unit] = Transactor.fromDriverManager[IO](
      "com.mysql.cj.jdbc.Driver",
      "jdbc:mysql://localhost:3306/world?useSSL=false&serverTimezone=America/Chicago",
      "root",
      "my-secret-pw",
//      "org.postgresql.Driver",     // driver classname
//      "jdbc:postgresql:world",     // connect URL (driver-specific)
//      "postgres",                  // user
//      "",                          // password
      Blocker.liftExecutionContext(ExecutionContexts.synchronous) // just for testing
    )

    val io = program1.transact(xa)
    // io: IO[Int] = Async(
    //   cats.effect.internals.IOBracket$$$Lambda$8180/248688506@6db044f4,
    //   false
    // )
    val result: Int = io.unsafeRunSync
    // res0: Int = 42

    result shouldBe 42
  }

}
