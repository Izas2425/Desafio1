package dao

import modelo.Usuario

interface UsuarioDAO {
    fun insertar(usuario: Usuario): Boolean
    fun obtener(nombre: String): Usuario?
    fun actualizar(usuario: Usuario): Boolean
    fun eliminar(nombre: String): Boolean
    fun obtenerTodos(): List<Usuario>
}