package ds.com.models

interface UserRepository {
    suspend fun allUsers(): List<UserDTO>
    suspend fun user(name: String): UserDTO?
    suspend fun addNewUser(user: UserDTO)
    suspend fun getUserId(name: String): Int
    suspend fun editUser(name: String, newName: String, newPass: String): Boolean
    suspend fun changeUsername(name: String, newName: String, pass: String): Boolean
    suspend fun changePassword(name: String, currPassword: String, newPassword: String): Boolean

    suspend fun deleteUser(name: String): Boolean
}



