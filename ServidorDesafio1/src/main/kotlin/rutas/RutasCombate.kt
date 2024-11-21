package rutas

import dao.CombateDAO
import dao.CombateDAOImpl
import io.ktor.server.routing.*

val combateDAO: CombateDAO = CombateDAOImpl()

fun Route.rutasCombate() {

}