import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class PacketTest {

    @Test
    fun emptyList() {
        val input = "[]"

        assertEquals(Packet(input).toString(), input)
    }

    @Test
    fun number() {
        val input = "3"

        assertEquals(Packet(input).toString(), input)
    }

    @Test
    fun numberTwoDigits() {
        val input = "31"

        assertEquals(Packet(input).toString(), input)
    }

    @Test
    fun numberArray() {
        val input = "[1,2,3]"

        assertEquals(Packet(input).toString(), input)
    }

    @Test
    fun nestedEmptyArray() {
        val input = "[[]]"

        assertEquals(Packet(input).toString(), input)
    }

    @Test
    fun nestedArray() {
        val input = "[[4,5,6]]"

        assertEquals(Packet(input).toString(), input)
    }

    @Test
    fun nestedMixedArray() {
        val input = "[1,2,3,[4,5,6]]"

        assertEquals(Packet(input).toString(), input)
    }

    @Test
    fun compareEmptyLists() {
        val p1 = Packet("[]")
        val p2 = Packet("[]")


        assertEquals(p1.compareTo(p2), 0)
    }

    @Test
    fun compareSameNumber() {
        val p1 = Packet("2")
        val p2 = Packet("2")


        assertEquals(p1.compareTo(p2), 0)
    }

    @Test
    fun compareNumbers() {
        val p1 = Packet("4")
        val p2 = Packet("3")


        assertEquals(p1.compareTo(p2), 1)
    }

    @Test
    fun compareNumbersRight() {
        val p1 = Packet("4")
        val p2 = Packet("5")


        assertEquals(p1.compareTo(p2), -1)
    }

    @Test
    fun compareArray() {
        val p1 = Packet("[1,2,3]")
        val p2 = Packet("[1,2,4]")


        assertEquals(p1.compareTo(p2), -1)
    }

    @Test
    fun compareArrayWrong() {
        val p1 = Packet("[1,2,5]")
        val p2 = Packet("[1,2,4]")


        assertEquals(p1.compareTo(p2), 1)
    }

    @Test
    fun compareNestedMixed() {
        val p1 = Packet("[1,[2,[3,[4,[5,6,7]]]],8,9]")
        val p2 = Packet("[1,[2,[3,[4,[5,6,0]]]],8,9]")

        assertEquals(p1.compareTo(p2), 1)
    }

    @Test
    fun compareLists() {
        val p1 = Packet("[[[]]]")
        val p2 = Packet("[[]]")


        assertEquals(p1.compareTo(p2), 1)
    }

    @Test
    fun compareLists2() {
        val p1 = Packet("[9]")
        val p2 = Packet("[[8,7,6]]")


        assertEquals(p1.compareTo(p2), 1)
    }
}
