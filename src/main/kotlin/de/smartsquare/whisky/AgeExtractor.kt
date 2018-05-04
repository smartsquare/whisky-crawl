package de.smartsquare.whisky


class AgeExtractor {
    fun parseAge(input: String): Int {

        val age = extractAge("(\\d+)\\s*Jahre", input)

        if (age != 0) {
            return age
        }

        return extractAge("(\\d+)[Jj]-\\d{4}", input)
    }

    private fun extractAge(ageRegex: String, input: String): Int {
        val split = ageRegex.toRegex().find(input)
        return split?.groups?.get(1)?.value?.toInt() ?: 0
    }
}