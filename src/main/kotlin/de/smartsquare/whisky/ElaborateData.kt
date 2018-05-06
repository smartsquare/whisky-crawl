package de.smartsquare.whisky

import com.google.gson.Gson
import de.smartsquare.whisky.domain.Whisky
import java.nio.file.Files
import java.nio.file.Paths


fun main(args: Array<String>) {
    val gson = Gson()

    val whiskyDeInformation = Files.readAllLines(Paths.get("whiskyde.json")).map { line -> gson.fromJson(line, Whisky::class.java) }
    val whiskyWorldInformation = Files.readAllLines(Paths.get("whiskyworld.json")).map { line -> gson.fromJson(line, Whisky::class.java) }

    val distilleryGroupWW: Map<String, List<Whisky>> = whiskyWorldInformation.groupBy { it.distillery }


    var matchCounter = 1
    for (index in 0..whiskyDeInformation.size - 1) {

        val referenceWhisky = whiskyDeInformation[index]

        val whiskyForDistillery: List<Whisky> = distilleryGroupWW.get(referenceWhisky.distillery).orEmpty()
        val matchingWhisky = whiskyForDistillery.filter { referenceWhisky.isSameAs(it) }

        if (!matchingWhisky.isEmpty()) {
            matchCounter++
            System.out.println("Match found for: ")
            printWhisky(referenceWhisky)
            matchingWhisky.forEach { printWhisky(it) }
            System.out.println("-------------------------")

        }

    }

    System.out.println(matchCounter)
}

private fun printWhisky(whisky: Whisky) {
    System.out.println("${whisky.source}: ${whisky.name} - ${whisky.price}Eur - ${whisky.age} - ${whisky.liter} - ${whisky.alcohol} - ${whisky.description}")
}


