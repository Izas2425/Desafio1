package dao

import modelo.Mision

interface MisionDAO  {
    fun insertar(mision: Mision): Boolean
    fun obtenerPorId(idmision: Int): Mision?
    fun eliminar(idMision: Int): Boolean
    fun obtenerTodos(): List<Mision>
    fun obtenerUltimoId(): Int?
}