package exercises

import org.junit.Assert
import org.junit.Test

class ReverseStringTest {
    @Test
    fun reverseUsingSB() {
        Assert.assertEquals("bs niltoK", reverseUsingSB("Kotlin sb"))
    }

    @Test
    fun reverseUsingLoop() {
        Assert.assertEquals("pool niltoK", reverseUsingLoop("Kotlin loop"))
    }
}