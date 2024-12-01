package rutas

import dao.MisionasignadaDAO
import dao.MisionasignadaDAOiImpl
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import modelo.Misionasignada
import modelo.Usuario

val misionasignadaDAO: MisionasignadaDAO = MisionasignadaDAOiImpl()

fun Route.rutasMisionasignada() {
    route("/listadoMisionesasignadas") {
        get {
            if (misionasignadaDAO.obtenerTodas().isNotEmpty()) {
                return@get call.respond(HttpStatusCode.OK, misionasignadaDAO.obtenerTodas())
            } else {
                return@get call.respond(HttpStatusCode.NotFound, null)
            }
        }

        get("{id?}") {
            // Intenta obtener y convertir el parámetro "id" de la URL a Int.
            val id = call.parameters["id"]?.toIntOrNull() ?: return@get call.respond(HttpStatusCode.BadRequest, null)

            // Llama a usuarioDAO.obtener(id) para buscar el usuario por el ID proporcionado.
            val misionasignada = misionasignadaDAO.obtenerPorId(id) ?: return@get call.respond(HttpStatusCode.NotFound, null)

            // Si el usuario es encontrado, responde con un código 200 (OK) y el usuario en el cuerpo de la respuesta.
            call.respond(HttpStatusCode.OK, misionasignada)
        }
    }
    route("/listadoMisionesasignadasSuperadas") {
        get {
            if (misionasignadaDAO.obtenerSuperadas().isNotEmpty()) {
                return@get call.respond(HttpStatusCode.OK, misionasignadaDAO.obtenerSuperadas())
            } else {
                return@get call.respond(HttpStatusCode.NotFound, null)
            }
        }
    }

    route("/listadoMisionesasignadasNoSuperadas") {
        get {
            if (misionasignadaDAO.obtenerNoSuperadas().isNotEmpty()) {
                return@get call.respond(HttpStatusCode.OK, misionasignadaDAO.obtenerNoSuperadas())
            } else {
                return@get call.respond(HttpStatusCode.NotFound, null)
            }
        }
    }

    route("/registrarMisionasignada") {
        post{
            val misa = call.receive<Misionasignada>()
            val misionasignada = rutas.misionasignadaDAO.obtenerPorId(misa.id)
            if(misionasignada != null) return@post call.respond(HttpStatusCode.BadRequest, null)

            if (!misionasignadaDAO.insertar(misa)){
                return@post call.respond(HttpStatusCode.Conflict, false)
            }
            call.respond(HttpStatusCode.Created, true)
        }
    }

    route("/borrarMisionasignada") {
        delete("{id?}") {
            val id = call.parameters["id"]?.toIntOrNull()  ?: return@delete call.respond(HttpStatusCode.BadRequest, false)

            val misionasignada = misionasignadaDAO.obtenerPorId(id)?: return@delete call.respond(HttpStatusCode.NotFound, false)

            if (!misionasignadaDAO.eliminar(id)){
                return@delete call.respond(HttpStatusCode.Conflict, false)
            }
            call.respond(HttpStatusCode.Accepted, true)

        }
    }

}