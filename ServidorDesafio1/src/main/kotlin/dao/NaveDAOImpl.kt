package dao

import modelo.Nave
import modelo.Usuario


class NaveDAOImpl: NaveDAO {
    // Pasado una nave lo inserta en la base de datos
    override fun insertar(nave: Nave): Boolean {
        val sql = "INSERT INTO naves (matricula, foto, tipo, carga, pasajeros) VALUES (?, ?, ?, ?, ?)"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setString(1, nave.matricula)
            statement.setString(2, nave.foto)
            statement.setString(3, nave.tipo)
            statement.setBoolean(4, nave.carga)
            statement.setBoolean(5, nave.pasajeros)
            return statement.executeUpdate() > 0
        }
        return false
    }

    // Pasado la matricula de una nave devuelve los datos de ésta
    override fun obtenerPorMatricula(matricula: String): Nave? {
        val sql = "SELECT * FROM naves WHERE matricula = ?"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setString(1, matricula)
            val resultSet = statement.executeQuery()

            if (resultSet.next()) {
                return Nave(
                    matricula = resultSet.getString("matricula"),
                    foto = resultSet.getString("foto"),
                    tipo = resultSet.getString("tipo"),
                    carga = resultSet.getBoolean("carga"),
                    pasajeros = resultSet.getBoolean("pasajeros")
                )
            }
        }
        return null
    }

    // Pasada una nave, actualiza sus datos en la bd
    override fun actualizar(nave: Nave): Boolean {
        val sql = "UPDATE naves SET foto = ?, tipo = ?, carga = ?, pasajeros = ? WHERE matricula = ?"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setString(1, nave.foto)
            statement.setString(2, nave.tipo)
            statement.setBoolean(3, nave.carga)
            statement.setBoolean(4, nave.pasajeros)
            statement.setString(5, nave.matricula)

            return statement.executeUpdate() > 0
        }
        return false
    }

    override fun eliminar(matricula: String): Boolean {
        val sql = "DELETE FROM naves WHERE matricula = ?"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setString(1, matricula)

            return statement.executeUpdate() > 0
        }
        return false
    }

    override fun obtenerTodos(): List<Nave> {
        val naves = mutableListOf<Nave>()
        val sql = "SELECT * FROM naves"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            val resultSet = statement.executeQuery()

            while (resultSet.next()) {
                val nave = Nave(
                    matricula = resultSet.getString("matricula"),
                    foto = resultSet.getString("foto"),
                    tipo = resultSet.getString("tipo"),
                    carga = resultSet.getBoolean("carga"),
                    pasajeros = resultSet.getBoolean("pasajeros")
                )
                naves.add(nave)
            }
        }
        return naves
    }


}