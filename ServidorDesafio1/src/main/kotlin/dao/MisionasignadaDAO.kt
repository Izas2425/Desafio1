package dao

import modelo.Misionasignada

interface MisionasignadaDAO {
    fun insertar(misionasignada: Misionasignada): Boolean
    fun obtenerPorId(id: Int): Misionasignada?
    fun eliminar(id: Int):Boolean
    fun obtenerTodas():List<Misionasignada>
    fun obtenerSuperadas():List<Misionasignada>
    fun obtenerNoSuperadas():List<Misionasignada>
}