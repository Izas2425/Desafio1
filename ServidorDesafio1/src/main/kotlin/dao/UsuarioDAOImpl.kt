package dao

import modelo.Usuario

class UsuarioDAOImpl: UsuarioDAO {

    // Pasado un usuario lo inserta en la base de datos
    override fun insertar(usuario: Usuario): Boolean {
        val sql = "INSERT INTO usuarios (nombre, password, role, edad, experiencia, foto ) VALUES (?, ?, ?, ?, ?, ?)"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setString(1, usuario.nombre)
            statement.setString(2, usuario.password)
            statement.setString(3, usuario.role)
            statement.setInt(4, usuario.edad)
            statement.setInt(5, usuario.experiencia)
            statement.setString(6, usuario.foto)
            return statement.executeUpdate() > 0
        }
        return false
    }

    // Pasado el id de un usuario devuelve los datos de éste
    override fun obtenerPorId(id: Int): Usuario? {
        val sql = "SELECT * FROM usuarios WHERE id = ?"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setInt(1, id)
            val resultSet = statement.executeQuery()

            if (resultSet.next()) {
                return Usuario(
                    id = resultSet.getInt("id"),
                    nombre = resultSet.getString("nombre"),
                    password = resultSet.getString("password"),
                    role = resultSet.getString("role"),
                    edad = resultSet.getInt("edad"),
                    experiencia = resultSet.getInt("experiencia"),
                    foto = resultSet.getString("foto")
                )
            }
        }
        return null
    }

    override fun obtenerPorNombre(nombre: String): Usuario? {
        val sql = "SELECT * FROM usuarios WHERE nombre = ?"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setString(1, nombre)
            val resultSet = statement.executeQuery()

            if (resultSet.next()) {
                return Usuario(
                    id = resultSet.getInt("id"),
                    nombre = resultSet.getString("nombre"),
                    password = resultSet.getString("password"),
                    role = resultSet.getString("role"),
                    edad = resultSet.getInt("edad"),
                    experiencia = resultSet.getInt("experiencia"),
                    foto = resultSet.getString("foto")
                )
            }
        }
        return null
    }

    // Pasado un usuario, actualiza sus datos en la bd
    override fun actualizar(usuario: Usuario): Boolean {
        val sql = "UPDATE usuarios SET nombre = ?, password = ?, role = ?, edad = ?, experiencia = ?, foto = ? WHERE id = ?"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setString(1, usuario.nombre)
            statement.setString(2, usuario.password)
            statement.setString(3, usuario.role)
            statement.setInt(4, usuario.edad)
            statement.setInt(5, usuario.experiencia)
            statement.setString(6, usuario.foto)

            return statement.executeUpdate() > 0
        }
        return false
    }

    override fun eliminar(id: Int): Boolean {
        val sql = "DELETE FROM usuarios WHERE id = ?"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setInt(1, id)

            return statement.executeUpdate() > 0
        }
        return false
    }

    override fun obtenerTodos(): List<Usuario> {
        val usuarios = mutableListOf<Usuario>()
        val sql = "SELECT * FROM usuarios"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            val resultSet = statement.executeQuery()

            while (resultSet.next()) {
                val usuario = Usuario(
                    id = resultSet.getInt("id"),
                    nombre = resultSet.getString("nombre"),
                    password = resultSet.getString("password"),
                    role = resultSet.getString("role"),
                    edad = resultSet.getInt("edad"),
                    experiencia = resultSet.getInt("experiencia"),
                    foto = resultSet.getString("foto")
                )
                usuarios.add(usuario)
            }
        }
        return usuarios
    }
}