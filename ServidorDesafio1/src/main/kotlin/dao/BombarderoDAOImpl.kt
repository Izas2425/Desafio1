package dao

import modelo.Bombardero
import modelo.Vuelo

class BombarderoDAOImpl: BombarderoDAO {
    override fun insertar(bombardero: Bombardero): Boolean {
        val sql = "INSERT INTO bombarderos (idMision, objetivos, carga, pasajeros) VALUES (?, ?, ?, ?)"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setInt(1, bombardero.idmision)
            statement.setInt(2, bombardero.objetivos)
            statement.setBoolean(3, bombardero.carga)
            statement.setBoolean(4, bombardero.pasajeros)
            return statement.executeUpdate() > 0
        }
        return false
    }

    override fun obtenerPorId(idmision: Int): Bombardero? {
        val sql = "SELECT * FROM bombarderos WHERE idMision = ?"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setInt(1, idmision)
            val resultSet = statement.executeQuery()

            if (resultSet.next()) {
                return Bombardero(
                    idmision = resultSet.getInt("idmision"),
                    objetivos = resultSet.getInt("objetivos"),
                    carga = resultSet.getBoolean("carga"),
                    pasajeros = resultSet.getBoolean("pasajeros")
                )
            }
        }
        return null
    }

    override fun eliminar(idmision: Int): Boolean {
        val sql = "DELETE FROM bombarderos WHERE idMision = ?"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setInt(1, idmision)

            return statement.executeUpdate() > 0
        }
        return false
    }

    override fun obtenerTodos(): List<Bombardero> {
        val bombarderos = mutableListOf<Bombardero>()
        val sql = "SELECT * FROM bombarderos"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            val resultSet = statement.executeQuery()

            while (resultSet.next()) {
                val bombardero = Bombardero(
                    idmision = resultSet.getInt("idmision"),
                    objetivos = resultSet.getInt("objetivos"),
                    carga = resultSet.getBoolean("carga"),
                    pasajeros = resultSet.getBoolean("pasajeros")
                )
                bombarderos.add(bombardero)
            }
        }
        return bombarderos
    }
}