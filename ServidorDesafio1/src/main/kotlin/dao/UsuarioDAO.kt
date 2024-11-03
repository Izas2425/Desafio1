package dao

import modelo.Usuario

interface UsuarioDAO {
    fun insertar(usuario: Usuario): Boolean
    fun obtener(id: Int): Usuario?
    fun actualizar(usuario: Usuario): Boolean
    fun eliminar(id: Int): Boolean
    fun obtenerTodos(): List<Usuario>
}