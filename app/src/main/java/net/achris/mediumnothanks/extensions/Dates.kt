package net.achris.mediumnothanks.extensions

import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

private val SHORT_DATE_FORMATTER = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)

fun Date.formatToShortDate(): String = this.toInstant()
    .atZone(ZoneId.systemDefault())
    .toLocalDate()
    .format(SHORT_DATE_FORMATTER)