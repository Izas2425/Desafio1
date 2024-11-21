package dao

import modelo.Vuelo

interface VueloDAO {
    fun inserar(vuelo: Vuelo): Boolean
    fun obtenerporId(idmision: Int): Vuelo?
    fun eliminar(idmision: Int): Boolean
    fun obtenerTodos(): List<Vuelo>
}