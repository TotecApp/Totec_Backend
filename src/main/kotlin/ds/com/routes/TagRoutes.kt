package ds.com.routes
import io.ktor.server.routing.*
import ds.com.models.*
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ds.com.models.TagRepository
import ds.com.models.TagRelationRepository
import io.ktor.server.application.*

fun Route.tagRouting(repository: TagRepository, repositoryTagRelation: TagRelationRepository) {
    route("/allTags") {
        allTags(repository)
    }
    route("/addTag") {
        //addTag(repository)
    }
    route("/deleteTag") {
        //deleteTag(repository)
    }
    route("/addTagRelation") {
        //addTagRelation(repositoryTagRelation)
    }
    route("/deleteTagRelation") {
        //deleteTagRelation(repositoryTagRelation)
    }
    route("/taggedRecipes") {
        taggedRecipes(repository, repositoryTagRelation)
    }
}

fun Route.allTags(repository: TagRepository) {
    get {
        try {
            var tags = repository.allTags()
            if(tags.isEmpty()){
                val baseTags = listOf("Vegan", "Vegetarian", "Gluten-Free", "Dairy-Free", "Low-Carb", "Low-Fat", "High-Protein", "Desert")
                for (tag in baseTags) {
                    repository.addNewTag(TagDTO(tag))
                }
                tags = repository.allTags()
            }
            call.application.log.info(tags.toString())
            call.respondText(Json.encodeToString(tags), ContentType.Application.Json)
        } catch (ex: Exception) {
            println("Error: ${ex.message}")
            call.respond(HttpStatusCode.BadRequest)
        }
    }
}

fun Route.taggedRecipes(repository: TagRepository, repositoryTagRelation: TagRelationRepository) {
    post {
        try {
            val tagNames = call.receive<List<String>>()
            val tagIds = tagNames.map { repository.getTagId(it) }
            val recipes = repositoryTagRelation.getTaggedRecipes(tagIds)
            call.respondText(Json.encodeToString(recipes), ContentType.Application.Json)
        } catch (ex: Exception) {
            println("Error: ${ex.message}")
            call.respond(HttpStatusCode.BadRequest)
        }
    }
}
