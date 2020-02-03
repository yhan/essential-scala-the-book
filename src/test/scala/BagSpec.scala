import org.scalatest.{FlatSpec, Matchers}


/*
    reference:
    https://medium.com/beingprofessional/understanding-functor-and-monad-with-a-bag-of-peanuts-8fa702b3f69e
* */

class BagSpec extends FlatSpec with Matchers {
  behavior of "Monad"
  it should "respect Left-identity law" in {
    // unit(x).flatMap(f) == f(x)
    Bag(Sugar(1)).flatMap(double) shouldBe double(Sugar(1))
  }

  it should "respect Right-identity law" in {
    // Monad[X].flatMap(unit) == Monad[X]
    Bag(Sugar(1)).flatMap(Bag.apply) shouldBe Bag(Sugar(1))
  }

  it should "respect Associativity law" in {
    //m.flatMap(f).flatMap(g) == m.flatMap(x ⇒ f(x).flatMap(g))

    val bag = Bag(Sugar(1))
    bag.flatMap(double).flatMap(triple) shouldBe bag.flatMap(
      x => double(x).flatMap(triple)
    )
  }

  def double(sugar: Sugar): Bag[Sugar] = {
    Bag(Sugar(sugar.weight * 2))
  }

  def triple(sugar: Sugar): Bag[Sugar] = {
    Bag(Sugar(sugar.weight * 3))
  }

  def simpleDouble(sugar: Sugar): Sugar = {
    Sugar(sugar.weight * 2)
  }

  def simpleTriple(sugar: Sugar): Sugar = {
    Sugar(sugar.weight * 3)
  }

  behavior of "Functor"
  it should "respect Identity law" in {
    /*
       Functor[X].map(x => identity(x)) == Functor[X]
        or
       Functor[X].map(identity) == Functor[X]
     */
    val bag = Bag(Sugar(1))
    bag.map(identity) shouldBe bag
  }

  it should "respect Associative law" in {
    // Functor[X].map(f).map(g) == Functor[X].map(x => g(f(x))
    val bag = Bag(Sugar(1))
    bag.map(simpleDouble).map(simpleTriple) shouldBe bag.map(x => triple(simpleDouble(x))
    )
  }
}

case class Sugar(weight: Int)
