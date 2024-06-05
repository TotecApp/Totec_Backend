package ds.com.models
import kotlinx.serialization.Serializable

@Serializable
data class UserDTO(
    val username: String,
    var password: String
) {
    init {
        require(username.isNotEmpty()) { "Username não pode ser vazio" }
        require(password.isNotEmpty()) { "Password não pode ser vazia" }
    }
}



