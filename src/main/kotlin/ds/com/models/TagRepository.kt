package ds.com.models

interface TagRepository {
    suspend fun allTags(): List<TagDTO>
    suspend fun tagInfo(tagname: String): List<TagDTO>
    suspend fun getTagId(name: String): Int
    suspend fun tag(id: Int): TagDTO?
    suspend fun addNewTag(tag: TagDTO)
    suspend fun editTag(id: Int, newTag: TagDTO): Boolean
    suspend fun deleteTag(id: Int): Boolean
}