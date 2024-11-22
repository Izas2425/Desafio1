package rutas

import dao.CombateDAO
import dao.CombateDAOImpl
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import modelo.Combate


val combateDAO: CombateDAO = CombateDAOImpl()

fun Route.rutasCombate() {
    route("/listadoCombates") {
        get {
            if (combateDAO.obtenerTodos().isNotEmpty()) {
                return@get call.respond(HttpStatusCode.OK, combateDAO.obtenerTodos())
            } else {
                return@get call.respond(HttpStatusCode.NotFound, null)
            }
        }

        get("{idmision?}") {

            val idmision =
                call.parameters["idmision"]?.toIntOrNull() ?: return@get call.respond(HttpStatusCode.BadRequest, null)

            val combate = combateDAO.obtenerPorId(idmision) ?: return@get call.respond(HttpStatusCode.NotFound, null)

            // Si el usuario es encontrado, responde con un código 200 (OK) y el usuario en el cuerpo de la respuesta.
            call.respond(HttpStatusCode.OK, combate)
        }
    }

    route("/registrarCombate") {
        post{
            val com = call.receive<Combate>()
            val combate = combateDAO.obtenerPorId(com.idmision)
            if(combate != null) return@post call.respond(HttpStatusCode.BadRequest, null)

            if (!combateDAO.insertar(com)){
                return@post call.respond(HttpStatusCode.Conflict, false)
            }
            call.respond(HttpStatusCode.Created, true)
        }
    }

    route("/borrarCombate") {
        delete("{id?}") {
            val id = call.parameters["id"]?.toIntOrNull()  ?: return@delete call.respond(HttpStatusCode.BadRequest, false)

            val combate = combateDAO.obtenerPorId(id)?: return@delete call.respond(HttpStatusCode.NotFound, false)

            if (!combateDAO.eliminar(id)){
                return@delete call.respond(HttpStatusCode.Conflict, false)
            }
            call.respond(HttpStatusCode.Accepted, true)

        }
    }

}