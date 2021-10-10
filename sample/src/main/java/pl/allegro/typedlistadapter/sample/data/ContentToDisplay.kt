package pl.allegro.typedlistadapter.sample.data

object ContentToDisplay {

    operator fun invoke() = listOf(
        Car(brand = "Seat", model = "Ibiza"),
        Car(brand = "Fiat", model = "Bravo"),
        Coffee(name = "Latte"),
        Advertisement,
        Coffee(name = "Americano"),
        Advertisement,
        Car(brand = "Skoda", model = "Felicia"),
    )
}