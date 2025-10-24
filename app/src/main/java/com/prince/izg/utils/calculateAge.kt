package com.prince.izg.utils

import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter

fun calculateAge(dobString: String): Int {
    val cleaned = dobString.take(10) // take only the yyyy-MM-dd part
    // Define the expected date format
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    // Parse the input string into a LocalDate
    val birthDate = LocalDate.parse(cleaned, formatter)

    // Get today's date
    val currentDate = LocalDate.now()

    // Calculate the difference in years
    return Period.between(birthDate, currentDate).years
}
