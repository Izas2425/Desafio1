package dao

import modelo.Usuario

interface UsuarioDAO {
    fun insertar(usuario: Usuario): Boolean
    fun obtenerPorId(id: Int): Usuario?
    fun obtenerPorNombre(nombre: String) : Usuario?
    fun actualizar(usuario: Usuario): Boolean
    fun eliminar(id: Int): Boolean
    fun obtenerTodos(): List<Usuario>
    fun obtenerPilotos(): List<Usuario>
    fun obtenerPilotosPorExperiencia(): List<Usuario>
}