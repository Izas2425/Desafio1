package dao

import modelo.Bombardero

interface BombarderoDAO {
    fun insertar(bombardero: Bombardero): Boolean
    fun obtenerPorId(idmision: Int): Bombardero?
    fun eliminar(idmision: Int): Boolean
    fun obtenerTodos(): List<Bombardero>
}