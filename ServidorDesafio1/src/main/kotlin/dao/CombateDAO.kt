package dao

import modelo.Combate
import modelo.Mision

interface CombateDAO {
    fun insert(combate: Combate): Boolean
    fun obtenerPorId(idmision: Int): Combate?
    fun eliminar(idmision: Int): Boolean
    fun obtenerTodos(): List<Combate>
}