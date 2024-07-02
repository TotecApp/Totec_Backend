package ds.com.models
import kotlinx.serialization.Serializable

@Serializable
data class TagDTO (
    var name: String
) {
    init {
        require(name.isNotEmpty()) { "Nome não pode ser vazio" }
    }
}