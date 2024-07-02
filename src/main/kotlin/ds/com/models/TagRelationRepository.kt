package ds.com.models

interface TagRelationRepository {
    suspend fun allTagRelations(): List<TagRelationDTO>
    suspend fun getTaggedRecipes(tagIds: List<Int>): List<TagRelationDTO>
    suspend fun getRecipeTags(recipeId: Int): List<TagRelationDTO>
    suspend fun addNewTagRelation(tagRelation: TagRelationDTO)
    suspend fun deleteTagRelation(tagId: Int, recipeId: Int): Boolean
}