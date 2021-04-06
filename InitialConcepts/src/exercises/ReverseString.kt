package exercises

import java.lang.StringBuilder

fun reverseUsingSB(str: String): String {
    return StringBuilder(str).reverse().toString()
}

fun reverseUsingLoop(str: String): String {
    return str.mapIndexed { index, _ ->
        val position = str.length - index - 1
        str[position]
    }.joinToString("")
}
