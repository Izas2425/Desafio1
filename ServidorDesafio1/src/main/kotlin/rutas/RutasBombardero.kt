package rutas

import dao.BombarderoDAO
import dao.BombarderoDAOImpl
import io.ktor.server.routing.*

val bombarderoDAO: BombarderoDAO = BombarderoDAOImpl()

fun Route.rutasBombardero(){

}