package prod

class Cat(val colour: String, val food: String)

class CatTrainer(val cat: Cat) {
  def canTrain : Boolean = {
    cat.colour.compareToIgnoreCase("black") == 0
  }
}

object CatTraining {
  def train(implicit trainer: CatTrainer): String ={
    if(trainer.canTrain) return  "OK"
    "KO"
  }
}