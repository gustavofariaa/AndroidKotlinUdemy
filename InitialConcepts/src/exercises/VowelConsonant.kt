package exercises

fun countVowels(phrase: String): Int {
    val vowels = "aeiou"
    var totalVowels = 0
    for (letter in phrase)
        if (vowels.contains(letter, true)) totalVowels++
    return totalVowels
}

fun countConsonants(phrase: String): Int {
    val consonants = "bcdfghjklmnpqrstvwxyz"
    var totalConsonants = 0
    for (letter in phrase)
        if (consonants.contains(letter, true)) totalConsonants++
    return totalConsonants
}

fun countVowelsFilter(phrase: String): Int {
    val vowels = "aeiou"
    return phrase.filter { it.toLowerCase() in vowels }.length
}

fun countConsonantsFilter(phrase: String): Int {
    val consonants = "bcdfghjklmnpqrstvwxyz"
    return phrase.filter { it.toLowerCase() in consonants }.length
}

