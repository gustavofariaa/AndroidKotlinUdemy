package exercises

import org.junit.Assert
import org.junit.Test

class VowelConsonantTest {
    @Test
    fun countVowels() {
        Assert.assertEquals(11, countVowels("Quantas vogais tem esta frase?"))
    }

    @Test
    fun countVowelsFilter() {
        Assert.assertEquals(11, countVowelsFilter("Quantas vogais tem esta frase?"))
    }

    @Test
    fun countConsonants() {
        Assert.assertEquals(21, countConsonants("Geralmente uma frase possui mais consoantes"))
    }

    @Test
    fun countConsonantsFilter() {
        Assert.assertEquals(21, countConsonantsFilter("Geralmente uma frase possui mais consoantes"))
    }

    @Test
    fun countVowelsConsonants() {
        val phrase = "Estou gostando muito de aprender Kotlin!"
        Assert.assertEquals(15, countVowels(phrase))
        Assert.assertEquals(15, countVowelsFilter(phrase))
        Assert.assertEquals(19, countConsonants(phrase))
        Assert.assertEquals(19, countConsonantsFilter(phrase))
    }
}