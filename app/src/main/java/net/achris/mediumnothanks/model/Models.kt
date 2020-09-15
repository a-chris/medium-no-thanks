package net.achris.mediumnothanks.model

import java.time.LocalDate
import java.util.*

data class Article(
    val title: String,
    val date: Date,
    val url: String
)