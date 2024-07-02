package ds.com.models
import ds.com.db.*
import ds.com.db.daoToModelTag as daoToModel
import ds.com.db.TagTable
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere

class PostgresTagRepository : TagRepository{
    override suspend fun allTags(): List<TagDTO> = suspendTransaction {
        TagDAO.all().map(::daoToModel)
    }

    override suspend fun tagInfo(tagname: String): List<TagDTO> = suspendTransaction {
        TagDAO
            .find{ TagTable.name eq tagname}
            .map(::daoToModel)
    }

    override suspend fun getTagId(name: String): Int = suspendTransaction {
        val tag = TagDAO.find{ TagTable.name eq name}.firstOrNull()
        tag?.id?.value ?: -1
    }

    override suspend fun tag(id: Int): TagDTO? = suspendTransaction {
        TagDAO
            .find { TagTable.id eq id }
            .limit(1)
            .map(::daoToModel)
            .firstOrNull()
    }

    override suspend fun addNewTag(tag: TagDTO): Unit = suspendTransaction {
        TagDAO.new {
            name = tag.name
        }
    }

    override suspend fun editTag(id: Int, newTag: TagDTO): Boolean = suspendTransaction {
        TagDAO
            .find { TagTable.id eq id }
            .limit(1)
            .map {
                it.name = newTag.name
            }
            .firstOrNull() != null
    }

    override suspend fun deleteTag(id: Int): Boolean = suspendTransaction {
        val rowsDeleted = TagTable.deleteWhere { TagTable.id eq id }
        rowsDeleted == 1
    }
}