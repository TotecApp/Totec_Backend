package ds.com.models
import ds.com.db.UserDAO
import ds.com.db.daoToModelUser as daoToModel
import ds.com.db.UserTable
import ds.com.db.suspendTransaction
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere

class PostgresUserRepository : UserRepository {
    override suspend fun allUsers(): List<UserDTO> = suspendTransaction {
        UserDAO.all().map(::daoToModel)
    }

    override suspend fun user(name: String): UserDTO? = suspendTransaction {
        UserDAO
            .find { UserTable.username eq name }
            .limit(1)
            .map(::daoToModel)
            .firstOrNull()
    }

    override suspend fun addNewUser(user: UserDTO): Unit = suspendTransaction {
        UserDAO.new {
            username = user.username
            password = user.password
        }
    }

    override suspend fun editUser(name: String, newName: String, newPass: String): Boolean = suspendTransaction {
        UserDAO
            .find { UserTable.username eq name }
            .limit(1)
            .map {
                it.username = newName
                it.password = newPass
            }
            .firstOrNull() != null
    }

    override suspend fun deleteUser(name: String): Boolean = suspendTransaction {
        val rowsDeleted = UserTable.deleteWhere { UserTable.username eq name }
        rowsDeleted == 1
    }
}