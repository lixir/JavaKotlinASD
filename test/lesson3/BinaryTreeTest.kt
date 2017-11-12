package lesson3

import org.junit.Test

import org.junit.Assert.*

class BinaryTreeTest {
    @Test
    fun add() {
        val tree = BinaryTree<Int>()
        tree.add(10)
        tree.add(5)
        tree.add(7)
        tree.add(10)
        assertEquals(3, tree.size)
        assertTrue(tree.contains(5))
        tree.add(3)
        tree.add(1)
        tree.add(3)
        tree.add(4)
        assertEquals(6, tree.size)
        assertFalse(tree.contains(8))
        tree.add(8)
        tree.add(15)
        tree.add(15)
        tree.add(20)
        assertEquals(9, tree.size)
        assertTrue(tree.contains(8))
        assertTrue(tree.checkInvariant())
        assertEquals(1, tree.first())
        assertEquals(20, tree.last())
    }

    @Test
    fun remove(){
        val tree = BinaryTree<Int>()
        tree.add(10)
        tree.add(5)
        tree.add(7)
        tree.add(14)
        assertEquals(4, tree.size)
        assertTrue(tree.remove(5))
        assertFalse(tree.remove(5))
        assertFalse(tree.remove(8))
        assertTrue(tree.remove(10))
        assertEquals(2, tree.size)
        assertTrue(tree.remove(14))
        assertTrue(tree.remove(7))
        assertEquals(0, tree.size)

    }

    @Test
    fun addKotlin() {
        val tree = KtBinaryTree<Int>()
        tree.add(10)
        tree.add(5)
        tree.add(7)
        tree.add(10)
        assertEquals(3, tree.size)
        assertTrue(tree.contains(5))
        tree.add(3)
        tree.add(1)
        tree.add(3)
        tree.add(4)
        assertEquals(6, tree.size)
        assertFalse(tree.contains(8))
        tree.add(8)
        tree.add(15)
        tree.add(15)
        tree.add(20)
        assertEquals(9, tree.size)
        assertTrue(tree.contains(8))
        assertTrue(tree.checkInvariant())
        assertEquals(1, tree.first())
        assertEquals(20, tree.last())
    }
}
