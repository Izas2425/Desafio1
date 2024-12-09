package dao

import modelo.Misionasignada

interface MisionasignadaDAO {
    fun insertar(misionasignada: Misionasignada): Boolean
    fun obtenerPorId(id: Int): Misionasignada?
    fun obtenerPorIdUsuario(idUsuario: Int): Misionasignada?
    fun eliminar(id: Int):Boolean
    fun obtenerTodas():List<Misionasignada>
    fun obtenerSuperadas(idUsuario: Int):List<Misionasignada>
    fun obtenerNoSuperadas(idUsuario: Int):List<Misionasignada>
    fun obtenerAsignadas(idUsuario: Int):List<Misionasignada>
    fun actualizar(misionasignada: Misionasignada): Boolean
}