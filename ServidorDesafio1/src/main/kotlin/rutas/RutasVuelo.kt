package rutas

import dao.VueloDAO
import dao.VueloDAOImpl
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import modelo.Mision
import modelo.Vuelo

val vueloDAO: VueloDAO = VueloDAOImpl()

fun Route.rutasVuelos(){
    route("/listadoVuelos"){
        get{
            if (vueloDAO.obtenerTodos().isNotEmpty()) {
                return@get call.respond(HttpStatusCode.OK, vueloDAO.obtenerTodos())
            } else {
                return@get call.respond(HttpStatusCode.NotFound, null)
            }
        }

        get("{idmision?}") {

            val idmision = call.parameters["idmision"]?.toIntOrNull() ?: return@get call.respond(HttpStatusCode.BadRequest, null)

            // Llama a naveDAO.obtener(matricula) para buscar la nave por la matricula proporcionada.
            val vuelo = vueloDAO.obtenerPorId(idmision) ?: return@get call.respond(HttpStatusCode.NotFound, null)

            // Si el usuario es encontrado, responde con un código 200 (OK) y el usuario en el cuerpo de la respuesta.
            call.respond(HttpStatusCode.OK, vuelo)
        }
    }

    route("/registrarVuelo") {
        post{
            val vue = call.receive<Vuelo>()
            val vuelo = vueloDAO.obtenerPorId(vue.idmision)
            if(vuelo != null) return@post call.respond(HttpStatusCode.BadRequest, null)

            if (!vueloDAO.insertar(vue)){
                return@post call.respond(HttpStatusCode.Conflict, false)
            }
            call.respond(HttpStatusCode.Created, true)
        }
    }

    route("/borrarVuelo") {
        delete("{id?}") {
            val id = call.parameters["id"]?.toIntOrNull()  ?: return@delete call.respond(HttpStatusCode.BadRequest, false)

            val vuelo = vueloDAO.obtenerPorId(id)?: return@delete call.respond(HttpStatusCode.NotFound, false)

            if (!vueloDAO.eliminar(id)){
                return@delete call.respond(HttpStatusCode.Conflict, false)
            }
            call.respond(HttpStatusCode.Accepted, true)

        }
    }

}