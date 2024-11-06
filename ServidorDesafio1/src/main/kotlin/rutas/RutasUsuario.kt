package rutas

import dao.UsuarioDAO
import dao.UsuarioDAOImpl
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import modelo.Usuario
import modelo.UsuarioLogIn

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
            val usuario = usuarioDAO.obtenerPorId(id) ?: return@get call.respond(HttpStatusCode.NotFound, null)

            // Si el usuario es encontrado, responde con un código 200 (OK) y el usuario en el cuerpo de la respuesta.
            call.respond(HttpStatusCode.OK, usuario)
        }
    }

    route("/login") {
        post{
            //  Recibe un usuarioLogIn (nombre y password) de inicio de sesión en el cuerpo de la solicitud.
            val user = call.receive<UsuarioLogIn>()

            //  Intenta obtener el usuario por nombre de usuario usando el DAO.
            val usuario = usuarioDAO.obtenerPorNombre(user.nombre) ?: return@post call.respond(HttpStatusCode.NotFound, null)

            //  Verifica si la contraseña proporcionada coincide con la almacenada.
            if (usuario.password != user.password){
                return@post call.respond(HttpStatusCode.BadRequest, null)
            }
            call.respond(HttpStatusCode.OK, usuario)
        }
    }

    route("/registrarUsuario") {
        post{
            val user = call.receive<Usuario>()
            val usuario = usuarioDAO.obtenerPorId(user.id)
            if(usuario != null) return@post call.respond(HttpStatusCode.BadRequest, null)

            if (!usuarioDAO.insertar(user)){
                return@post call.respond(HttpStatusCode.Conflict, false)
            }
            call.respond(HttpStatusCode.Created, true)
        }
    }

    route("/borrarUsuario") {
        delete("{id?}") {
            val id = call.parameters["id"]?.toIntOrNull()  ?: return@delete call.respond(HttpStatusCode.BadRequest, false)

            val usuario = usuarioDAO.obtenerPorId(id)?: return@delete call.respond(HttpStatusCode.NotFound, false)

            if (!usuarioDAO.eliminar(id)){
                return@delete call.respond(HttpStatusCode.Conflict, false)
            }
            call.respond(HttpStatusCode.Accepted, true)

        }
    }
    route("/modificarUsuario") {
        put("{id?}") {
            val id = call.parameters["id"]?.toIntOrNull() ?: return@put call.respond(HttpStatusCode.BadRequest, false)
            val user = call.receive<Usuario>()
            val usuario = usuarioDAO.obtenerPorId(id) ?: return@put call.respond(HttpStatusCode.NotFound, false)
            if (!usuarioDAO.actualizar(user)){
                return@put call.respond(HttpStatusCode.BadRequest, false)
            }
            call.respond(HttpStatusCode.Accepted, true)
        }
    }


}