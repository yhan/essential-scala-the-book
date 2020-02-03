
import scalaz.Functor


class FunctorDemo {

  implicit val demoFunctor = new Functor[Container] {
    def map[A, B](fa: Container[A])(f: A => B): Container[B] =
      Container(f(fa.first), f(fa.second))
  }

  case class Container[A](first: A, second: A)

}
