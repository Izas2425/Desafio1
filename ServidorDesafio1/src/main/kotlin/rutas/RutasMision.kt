package rutas

import dao.MisionDAO
import dao.MisionDAOImpl
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import modelo.Mision


val misionDAO: MisionDAO = MisionDAOImpl()

fun Route.rutasMision(){
    route("/listadoMisiones") {
        get {
            if (misionDAO.obtenerTodos().isNotEmpty()) {
                return@get call.respond(HttpStatusCode.OK, misionDAO.obtenerTodos())
            } else {
                return@get call.respond(HttpStatusCode.NotFound, null)
            }
        }

        get("{idmision?}") {

            val idmision =
                call.parameters["idmision"]?.toIntOrNull() ?: return@get call.respond(HttpStatusCode.BadRequest, null)

            // Llama a naveDAO.obtener(matricula) para buscar la nave por la matricula proporcionada.
            val mision = misionDAO.obtenerPorId(idmision) ?: return@get call.respond(HttpStatusCode.NotFound, null)

            // Si el usuario es encontrado, responde con un código 200 (OK) y el usuario en el cuerpo de la respuesta.
            call.respond(HttpStatusCode.OK, mision)
        }
    }
        route("/ultimoId"){
            get{

                val ultimoId = misionDAO.obtenerUltimoId()
                if (ultimoId != null) {
                    return@get call.respond(HttpStatusCode.OK, ultimoId)

                }
                return@get call.respond(HttpStatusCode.NotFound, null)
            }
        }



    route("/registrarMision") {
        post{
            val mis = call.receive<Mision>()
            val mision = misionDAO.obtenerPorId(mis.idmision)
            if(mision != null) return@post call.respond(HttpStatusCode.BadRequest, null)

            if (!misionDAO.insertar(mis)){
                return@post call.respond(HttpStatusCode.Conflict, null)
            }
            call.respond(HttpStatusCode.Created, true)
        }
    }

    route("/registrarMisionYObtenerId") {
        post{
            val mis = call.receive<Mision>()
            val idObtenido = misionDAO.insertarYObtenerId(mis)

            if (idObtenido == null){
                return@post call.respond(HttpStatusCode.Conflict, null)
            }
            call.respond(HttpStatusCode.Created, idObtenido)
        }
    }

    route("/borrarMision") {
        delete("{id?}") {
            val id = call.parameters["id"]?.toIntOrNull()  ?: return@delete call.respond(HttpStatusCode.BadRequest, false)

            val mision = misionDAO.obtenerPorId(id)?: return@delete call.respond(HttpStatusCode.NotFound, false)

            if (!misionDAO.eliminar(id)){
                return@delete call.respond(HttpStatusCode.Conflict, false)
            }
            call.respond(HttpStatusCode.Accepted, true)

        }
    }
}