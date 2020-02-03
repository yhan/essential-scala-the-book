case class Bag[A](content: A) {
    def map[B](f: A => B): Bag[B] = Bag(f(content))

    def flatMap[B](f: A => Bag[B]): Bag[B] = f(content)

    def flatten = content
}
