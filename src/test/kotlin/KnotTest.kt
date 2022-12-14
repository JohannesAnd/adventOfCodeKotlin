import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

internal class KnotTest {

    @Test
    fun moveOnTop() {
        val parent = Knot()
        val child = Knot()

        parent.child = child

        parent.move(Direction.R)
        parent.move(Direction.L)

        assertEquals(child.x, 0)
        assertEquals(child.y, 0)
    }

    @Test
    fun moveRight() {
        val parent = Knot()
        val child = Knot()

        parent.child = child

        parent.move(Direction.R)
        parent.move(Direction.R)

        assertEquals(child.x, 1)
        assertEquals(child.y, 0)
    }

    @Test
    fun moveLeft() {
        val parent = Knot()
        val child = Knot()

        parent.child = child

        parent.move(Direction.L)
        parent.move(Direction.L)

        assertEquals(child.x, -1)
        assertEquals(child.y, 0)
    }
}