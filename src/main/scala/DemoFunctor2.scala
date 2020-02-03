import scalaz.Functor

class Demo{

  implicit val OptionFunctor = new Functor[Option] {
    def fmap[A, B](f: A => B): Option[A] => Option[B] = { option =>
      option map f
    }

    override def map[A, B](fa: Option[A])(f: A => B): Option[B] = {
      fa.map(f)
    }
  }

  implicit val ListFunctor: Functor[List] = new Functor[List] {
    def fmap[A, B](f: A => B): List[A] => List[B] = list => list.map(f)

    override def map[A, B](fa: List[A])(f: A => B): List[B] = fa.map(f)
  }
}


