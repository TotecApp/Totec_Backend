package ds.com.models

import org.junit.jupiter.api.Assertions.*

class TagDTOTest {
    @org.junit.jupiter.api.Test
    fun criacao() {
        val tag = TagDTO("testTag")
        assertEquals("testTag", tag.name)
    }

    @org.junit.jupiter.api.Test
    fun modificacao() {
        val tag = TagDTO("testTag")
        tag.name = "newTag"
        assertEquals("newTag", tag.name)
    }

    @org.junit.jupiter.api.Test
    fun `Nome vazio`() {
        assertThrows(IllegalArgumentException::class.java) {
            TagDTO("")
        }
    }
}