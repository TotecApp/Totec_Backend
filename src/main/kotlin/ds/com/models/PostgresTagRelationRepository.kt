package ds.com.models
import ds.com.db.*
import ds.com.db.daoToModelTagRelation as daoToModel
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.dao.id.EntityID

class PostgresTagRelationRepository : TagRelationRepository{
    override suspend fun allTagRelations(): List<TagRelationDTO> = suspendTransaction {
        TagRelationDAO.all().map(::daoToModel)
    }

    override suspend fun getTaggedRecipes(tagIds: List<Int>): List<TagRelationDTO> {
        return suspendTransaction {
            TagRelationDAO
                .find{ TagRelationTable.tagId inList tagIds}
                .map(::daoToModel)
        }
    }

    override suspend fun getRecipeTags(recipeId: Int): List<TagRelationDTO> {
        return suspendTransaction {
            TagRelationDAO
                .find{ TagRelationTable.recipeId eq recipeId}
                .map(::daoToModel)
        }
    }

    override suspend fun addNewTagRelation(tagRelation: TagRelationDTO) {
        suspendTransaction {
            TagRelationDAO.new {
                tagId = EntityID(tagRelation.tagId, TagTable)
                recipeId = EntityID(tagRelation.recipeId, RecipeTable)
            }
        }
    }

    override suspend fun deleteTagRelation(tagId: Int, recipeId: Int): Boolean {
        return suspendTransaction {
            val rowsDeleted = TagRelationTable.deleteWhere { (TagRelationTable.tagId eq tagId) and (TagRelationTable.recipeId eq recipeId) }
            rowsDeleted == 1
        }
    }
}