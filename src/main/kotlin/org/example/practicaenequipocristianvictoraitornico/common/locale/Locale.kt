package org.example.practicaenequipocristianvictoraitornico.common.locale

import org.example.practicaenequipocristianvictoraitornico.common.config.Config
import org.example.practicaenequipocristianvictoraitornico.users.dao.UsersDao
import java.text.NumberFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*
import kotlin.math.pow
import kotlin.math.roundToInt

private val locale= Locale.getDefault()
private val leng= locale.displayLanguage
private val pais= locale.displayCountry
private val localeConfig= Locale.forLanguageTag(Config.configProperties.locale)

fun LocalDate.toLocalDate(): String {
    return this.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).withLocale(localeConfig))
}
fun LocalDateTime.toLocalDateTime(): String {
    return this.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).withLocale(localeConfig))
}

fun Double.toLocalNumber(): String {
    return NumberFormat.getNumberInstance(Locale.getDefault()).format(this)
}
fun Double.round(decimals: Int): Double {
    val factor = 10.0.pow(decimals)
    return (this * factor).roundToInt() / factor
}
fun Double.toLocalMoney(): String {
    return NumberFormat.getCurrencyInstance(Locale.getDefault()).format(this)
}