package dao

import modelo.Mision
import modelo.Vuelo

class VueloDAOImpl: VueloDAO {
    override fun inserar(vuelo: Vuelo): Boolean {
        val sql = "INSERT INTO vuelos (idMision, duracion, carga, pasajeros) VALUES (?, ?, ?, ?)"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setInt(1, vuelo.idmision)
            statement.setInt(2, vuelo.duracion)
            statement.setBoolean(3, vuelo.carga)
            statement.setBoolean(4, vuelo.pasajeros)
            return statement.executeUpdate() > 0
        }
        return false
    }

    override fun obtenerporId(idmision: Int): Vuelo? {
        val sql = "SELECT * FROM vuelos WHERE idMision = ?"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setInt(1, idmision)
            val resultSet = statement.executeQuery()

            if (resultSet.next()) {
                return Vuelo(
                    idmision = resultSet.getInt("idmision"),
                    duracion = resultSet.getInt("duracion"),
                    carga = resultSet.getBoolean("carga"),
                    pasajeros = resultSet.getBoolean("pasajeros")
                )
            }
        }
        return null
    }

    override fun eliminar(idmision: Int): Boolean {
        val sql = "DELETE FROM vuelos WHERE idMision = ?"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setInt(1, idmision)

            return statement.executeUpdate() > 0
        }
        return false
    }

    override fun obtenerTodos(): List<Vuelo> {
        val vuelos = mutableListOf<Vuelo>()
        val sql = "SELECT * FROM vuelos"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            val resultSet = statement.executeQuery()

            while (resultSet.next()) {
                val vuelo = Vuelo(
                    idmision = resultSet.getInt("idmision"),
                    duracion = resultSet.getInt("duracion"),
                    carga = resultSet.getBoolean("carga"),
                    pasajeros = resultSet.getBoolean("pasajeros")
                )
                vuelos.add(vuelo)
            }
        }
        return vuelos
    }
}