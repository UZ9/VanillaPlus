package com.yerti.vanillaplus.utils

fun String?.isInt() : Boolean {
    return this?.toIntOrNull() == null
}