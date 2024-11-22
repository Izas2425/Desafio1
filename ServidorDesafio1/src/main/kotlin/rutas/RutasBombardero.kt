package rutas

import dao.BombarderoDAO
import dao.BombarderoDAOImpl
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import modelo.Bombardero


val bombarderoDAO: BombarderoDAO = BombarderoDAOImpl()

fun Route.rutasBombardero(){
    route("/listadoBombardero"){
        get{
            if (bombarderoDAO.obtenerTodos().isNotEmpty()) {
                return@get call.respond(HttpStatusCode.OK, bombarderoDAO.obtenerTodos())
            } else {
                return@get call.respond(HttpStatusCode.NotFound, null)
            }
        }

        get("{idmision?}") {

            val idmision = call.parameters["idmision"]?.toIntOrNull() ?: return@get call.respond(HttpStatusCode.BadRequest, null)

            // Llama a naveDAO.obtener(matricula) para buscar la nave por la matricula proporcionada.
            val bombardero = bombarderoDAO.obtenerPorId(idmision) ?: return@get call.respond(HttpStatusCode.NotFound, null)

            // Si el usuario es encontrado, responde con un código 200 (OK) y el usuario en el cuerpo de la respuesta.
            call.respond(HttpStatusCode.OK, bombardero)
        }
    }

    route("/registrarBombardero") {
        post{
            val bom = call.receive<Bombardero>()
            val bombardero = bombarderoDAO.obtenerPorId(bom.idmision)
            if(bombardero != null) return@post call.respond(HttpStatusCode.BadRequest, null)

            if (!bombarderoDAO.insertar(bom)){
                return@post call.respond(HttpStatusCode.Conflict, false)
            }
            call.respond(HttpStatusCode.Created, true)
        }
    }

    route("/borrarBombardero") {
        delete("{id?}") {
            val id = call.parameters["id"]?.toIntOrNull()  ?: return@delete call.respond(HttpStatusCode.BadRequest, false)

            val bombardero = bombarderoDAO.obtenerPorId(id)?: return@delete call.respond(HttpStatusCode.NotFound, false)

            if (!bombarderoDAO.eliminar(id)){
                return@delete call.respond(HttpStatusCode.Conflict, false)
            }
            call.respond(HttpStatusCode.Accepted, true)

        }
    }

}