import cats.syntax.StrongSyntax

package object syntax {
  object person extends PersonSyntax
  object string extends StringSyntax
}


final class StringOps(val self: String) {
  def join(second: String): String = {
    self.concat(second)
  }
}

trait StringSyntax {
  implicit def syntaxString(s: String): StringOps = new StringOps(s)
}

trait PersonSyntax {
  implicit def syntaxPerson(p: Person): PersonOps = new PersonOps(p)
}

final class PersonOps(val self: Person ) {
  def addAge(delta: Int): Person = {
    Person(self.age + delta)
  }
}