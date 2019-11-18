
case class Director(firstName: String, lastName:String, yearOfBirth: Int) {
    def name : String = s"$firstName $lastName"

}

case class Film(name: String, yearOfRelease: Int, imdbRating: Double, director: Director) {
    def isDirectedBy(director0: Director) = director0 == director

    def ageOfDirector() : Int = yearOfRelease - director.yearOfBirth

}