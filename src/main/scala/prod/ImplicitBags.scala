package prod

trait ImplicitBags {
  implicit val catTrainer : CatTrainer = new CatTrainer(new Cat("black", "rice"))

}
