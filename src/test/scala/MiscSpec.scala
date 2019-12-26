
import org.scalatest.{FlatSpec, Matchers}
import prod.OperandTester

class MiscSpec extends FlatSpec with Matchers {
  "==" should "be able to compare operands of different types" in {
    OperandTester.equalOperator shouldBe false
  }

  "Cats ===" should "work with exists to test existence and value matching When value exist" in {
    val bidRequest = BidRequest(Some(BidRequestExtension(Some(true))))
    val isTopLevel =
      bidRequest.extension.flatMap(_.isTopLevel).exists(_ === true)

    isTopLevel shouldBe true
  }

  "Cats ===" should "work with exists to test existence and value matching When value DOES NOT exist" in {
    val bidRequest =
      BidRequest(Some(BidRequestExtension(None: Option[Boolean])))
    val isTopLevel =
      bidRequest.extension.flatMap(_.isTopLevel).exists(_ === false)

    isTopLevel shouldBe false
  }

  "Collection flatMapping with expression producing Option" should "do filtering" in {
    val total = 100
    val origin = 1 to total
    val filtered = origin.flatMap(
      x => Either.cond(x % 2 == 0, x, "Not an even number").toOption
    )

    filtered.forall(_ % 2 == 0) shouldBe true
    filtered.size shouldBe total / 2
  }

  "Empty char" should "not be digit" in {
    val input = "1,,2"
    val digitsOnly = input
      .split(',')
      .filter(s => s.forall(_.isDigit) && s.nonEmpty)
      .map(_.toInt)
    digitsOnly shouldBe Array(1, 2)

    //        val hours = str.split(",").filter(s => s.forall(_.isDigit) && s.nonEmpty).map(_.toInt).toSet
  }

  "toSet on duplicated" should "???" in {
    val input = "1,2,2"
    val set = input
      .split(',')
      .filter(s => s.forall(_.isDigit) && s.nonEmpty)
      .map(_.toInt)
      .toSet

    set shouldBe Set(1, 2)

    val set2: Set[Int] = List(1, 1, 1).toSet
    set2.size shouldBe 1

//    set2(0) shouldBe 1
    println(s"********************** -> $set2 ${set2(2)}")
  }

  "Option forAll" should "returns true when Option is None" in {
    val p: Option[Person] = None
    val isAdult = p.forall(_.isAdult)

    isAdult shouldBe true
  }

  "Expression `for` " can "iterate" in {
    val multiLines =
      """4
        | 5
        | a
        | 8
        | 10
        |""".stripMargin
    val filtered: Array[String] = for (line <- lines(multiLines)
                                       if isNumeric(line.trim)) yield line

    filtered.size shouldBe 4
  }

  def isNumeric(input: String): Boolean = input.forall(_.isDigit)

  def lines(input: String): Array[String] = {
    input.split("\n")
  }

  "Array" can "apply implicit convert" in {
    val numbers = Array(1, 2, 3, 4)
    val first = numbers(0) // read the first element
    numbers(3) = 100 // replace the 4th array element with 100
    val biggerNumbers = numbers.map(_ * 2) // multiply all numbers by two
  }

  "Ops definition on Person" can "act as extension methods" in {
//    "hello".join(" world") shouldBe "hello world"
    import syntax.person._
    Person(14).addAge(1).isAdult shouldBe false
  }

  "Ops definition on scala.String" can "act as extension methods" in {
    import syntax.string._
        "hello".join(" world") shouldBe "hello world"
  }
}



case class Person(age: Int) {
  def isAdult: Boolean = age > 18
}

case class BidRequest(extension: Option[BidRequestExtension])

case class BidRequestExtension(isTopLevel: Option[Boolean])
