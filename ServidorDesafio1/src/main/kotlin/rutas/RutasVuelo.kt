package rutas

import dao.VueloDAO
import dao.VueloDAOImpl
import io.ktor.server.routing.*

val vueloDAO: VueloDAO = VueloDAOImpl()

fun Route.rutasVuelos(){

}