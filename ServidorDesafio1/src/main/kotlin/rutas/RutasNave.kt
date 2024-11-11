package rutas

import dao.NaveDAO
import dao.NaveDAOImpl
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import modelo.Nave
import modelo.Usuario

val naveDAO: NaveDAO = NaveDAOImpl()

fun Route.rutasNave(){
    route("/listadoNaves"){
        get{
            if (naveDAO.obtenerTodos().isNotEmpty()) {
                return@get call.respond(HttpStatusCode.OK, naveDAO.obtenerTodos())
            } else {
                return@get call.respond(HttpStatusCode.NotFound, null)
            }
        }

        get("{matricula?}") {

            val matricula = call.parameters["matricula"]?: return@get call.respond(HttpStatusCode.BadRequest, null)

            // Llama a naveDAO.obtener(matricula) para buscar la nave por la matricula proporcionada.
            val nave = naveDAO.obtenerPorMatricula(matricula) ?: return@get call.respond(HttpStatusCode.NotFound, null)

            // Si el usuario es encontrado, responde con un código 200 (OK) y el usuario en el cuerpo de la respuesta.
            call.respond(HttpStatusCode.OK, nave)
        }
    }

    route("/registrarNave") {
        post{
            val nav = call.receive<Nave>()
            val nave = naveDAO.obtenerPorMatricula(nav.matricula)
            if(nave != null) return@post call.respond(HttpStatusCode.BadRequest, null)

            if (!naveDAO.insertar(nav)){
                return@post call.respond(HttpStatusCode.Conflict, false)
            }
            call.respond(HttpStatusCode.Created, true)
        }
    }

    route("/borrarNave") {
        delete("{matricula?}") {
            val matricula = call.parameters["matricula"]  ?: return@delete call.respond(HttpStatusCode.BadRequest, false)

            val nave = naveDAO.obtenerPorMatricula(matricula)?: return@delete call.respond(HttpStatusCode.NotFound, false)

            if (!naveDAO.eliminar(matricula)){
                return@delete call.respond(HttpStatusCode.Conflict, false)
            }
            call.respond(HttpStatusCode.Accepted, true)
        }
    }

    route("/modificarNave") {
        put("{matricula?}") {
            val matricula = call.parameters["matricula"] ?: return@put call.respond(HttpStatusCode.BadRequest, false)
            val nav = call.receive<Nave>()
            val nave = naveDAO.obtenerPorMatricula(matricula) ?: return@put call.respond(HttpStatusCode.NotFound, false)
            if (!naveDAO.actualizar(nav)){
                return@put call.respond(HttpStatusCode.BadRequest, false)
            }
            call.respond(HttpStatusCode.Accepted, true)
        }
    }
}
