import prod.Cat

object ChipShop {
    def willServe(cat: Cat): Boolean = {
        cat.food == "Chips"
    }
}
