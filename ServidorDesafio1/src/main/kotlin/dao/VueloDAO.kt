package dao

import modelo.Vuelo

interface VueloDAO {
    fun insertar(vuelo: Vuelo): Boolean
    fun obtenerPorId(idmision: Int): Vuelo?
    fun eliminar(idmision: Int): Boolean
    fun obtenerTodos(): List<Vuelo>
}