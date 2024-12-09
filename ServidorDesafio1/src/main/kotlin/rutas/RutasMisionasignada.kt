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
         get("{id?}") {
            // Intenta obtener y convertir el parámetro "id" de la URL a Int.
            val id = call.parameters["id"]?.toIntOrNull() ?: return@get call.respond(HttpStatusCode.BadRequest, null)

            // Llama a usuarioDAO.obtener(id) para buscar el usuario por el ID proporcionado.
            if(misionasignadaDAO.obtenerAsignadas(id).isNotEmpty()){
                return@get call.respond(HttpStatusCode.OK, misionasignadaDAO.obtenerAsignadas(id))
            }else{
                return@get call.respond(HttpStatusCode.NotFound, null)
            }

        }
    }
    route("/listadoMisionesasignadasSuperadas") {
        get ("{id?}"){
            val id = call.parameters["id"]?.toIntOrNull() ?: return@get call.respond(HttpStatusCode.BadRequest, null)

            if (misionasignadaDAO.obtenerSuperadas(id).isNotEmpty()) {
                return@get call.respond(HttpStatusCode.OK, misionasignadaDAO.obtenerSuperadas(id))
            } else {
                return@get call.respond(HttpStatusCode.NotFound, null)
            }
        }
    }

    route("/listadoMisionesasignadasNoSuperadas") {
        get ("{id?}"){
            val id = call.parameters["id"]?.toIntOrNull() ?: return@get call.respond(HttpStatusCode.BadRequest, null)
            if (misionasignadaDAO.obtenerNoSuperadas(id).isNotEmpty()) {
                return@get call.respond(HttpStatusCode.OK, misionasignadaDAO.obtenerNoSuperadas(id))
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

    route("/actualizarMisionasignada") {
        put("{id?}") {
            val id = call.parameters["id"]?.toIntOrNull() ?: return@put call.respond(HttpStatusCode.BadRequest, false)
            val mis = call.receive<Misionasignada>()
            val misionasignada = misionasignadaDAO.obtenerPorId(id)?: return@put call.respond(HttpStatusCode.NotFound, false)

            if (!misionasignadaDAO.actualizar(mis)) {
                    return@put call.respond(HttpStatusCode.BadRequest, false)
            }

            call.respond(HttpStatusCode.Accepted, true)
        }
    }

}