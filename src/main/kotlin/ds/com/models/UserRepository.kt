package ds.com.models

interface UserRepository {
    suspend fun allUsers(): List<UserDTO>
    suspend fun user(name: String): UserDTO?
    suspend fun addNewUser(user: UserDTO)
    suspend fun editUser(name: String, newName: String, newPass: String): Boolean
    suspend fun deleteUser(name: String): Boolean
}



