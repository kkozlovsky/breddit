package ru.kerporation.breddit.utils

import java.util.*

fun <T : Any> Optional<T>.toNullable(): T? = this.orElse(null);

fun <T : Any> T?.toOptional(): Optional<T> = Optional.ofNullable(this)
