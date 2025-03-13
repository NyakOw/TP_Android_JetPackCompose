package fr.eni.ecole.enishop.utils

import java.text.SimpleDateFormat
import java.util.Date

fun Date.toFrenchStringFormat(): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy")
    return formatter.format(this)
}