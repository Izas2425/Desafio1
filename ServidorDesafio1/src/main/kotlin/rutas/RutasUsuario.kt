package rutas

import dao.UsuarioDAO
import dao.UsuarioDAOImpl
import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

val usuarioDAO: UsuarioDAO = UsuarioDAOImpl()

fun Route.rutasUsuario() {
    route("/listadoUsuarios") {
        get {
            if (usuarioDAO.obtenerTodos().isNotEmpty()) {
                return@get call.respond(HttpStatusCode.OK, usuarioDAO.obtenerTodos())
            } else {
                return@get call.respond(HttpStatusCode.NotFound, null)
            }
        }
        get("{id?}") {
            // Intenta obtener y convertir el parámetro "id" de la URL a Int.
            val id = call.parameters["id"]?.toIntOrNull() ?: return@get call.respond(HttpStatusCode.BadRequest, null)

            // Llama a usuarioDAO.obtener(id) para buscar el usuario por el ID proporcionado.
            val usuario = usuarioDAO.obtener(id) ?: return@get call.respond(HttpStatusCode.NotFound, null)

            // Si el usuario es encontrado, responde con un código 200 (OK) y el usuario en el cuerpo de la respuesta.
            call.respond(HttpStatusCode.OK, usuario)
        }
    }


}