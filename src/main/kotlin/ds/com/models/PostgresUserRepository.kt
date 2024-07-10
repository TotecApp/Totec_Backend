package ds.com.models
import com.typesafe.config.ConfigException.Null
import ds.com.db.UserDAO
import ds.com.db.daoToModelUser as daoToModel
import ds.com.db.UserTable
import ds.com.db.suspendTransaction
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.update

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

    override suspend fun getUserId(name: String): Int = suspendTransaction {
        val user = UserDAO.find{UserTable.username eq name}.firstOrNull()
        user?.id?.value ?: -1
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

    override suspend fun changeUsername(name: String, newName: String, pass: String): Boolean = suspendTransaction {
        val user = UserDAO.find {
            (UserTable.username eq name) and (UserTable.password eq pass)
        }.limit(1).firstOrNull()

        if (user != null) {
            UserTable.update({ UserTable.id eq user.id }) {
                it[username] = newName
            }
            return@suspendTransaction true
        }
        return@suspendTransaction false
    }

    override suspend fun changePassword(name: String, currPassword: String, newPassword: String): Boolean = suspendTransaction {
        val user = UserDAO.find {
            (UserTable.username eq name) and (UserTable.password eq currPassword)
        }.limit(1).firstOrNull()

        if (user != null) {
            UserTable.update({ UserTable.id eq user.id }) {
                it[password] = newPassword
            }
            return@suspendTransaction true
        }
        return@suspendTransaction false
    }

    override suspend fun deleteUser(name: String): Boolean = suspendTransaction {
        val rowsDeleted = UserTable.deleteWhere { UserTable.username eq name }
        rowsDeleted == 1
    }
}