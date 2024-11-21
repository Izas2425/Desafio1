package dao

import modelo.Combate


class CombateDAOImpl: CombateDAO {
    override fun insert(combate: Combate): Boolean {
        val sql = "INSERT INTO combates (idMision, cazas) VALUES (?, ?)"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setInt(1, combate.idmision)
            statement.setInt(2, combate.cazas)
            return statement.executeUpdate() > 0
        }
        return false
    }

    override fun obtenerPorId(idmision: Int): Combate? {
        val sql = "SELECT * FROM combates WHERE idMision = ?"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setInt(1, idmision)
            val resultSet = statement.executeQuery()

            if (resultSet.next()) {
                return Combate(
                    idmision = resultSet.getInt("idmision"),
                    cazas = resultSet.getInt("cazas")
                )
            }
        }
        return null
    }

    override fun eliminar(idmision: Int): Boolean {
        val sql = "DELETE FROM combates WHERE idMision = ?"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setInt(1, idmision)

            return statement.executeUpdate() > 0
        }
        return false
    }

    override fun obtenerTodos(): List<Combate> {
        val combates = mutableListOf<Combate>()
        val sql = "SELECT * FROM combates"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            val resultSet = statement.executeQuery()

            while (resultSet.next()) {
                val combate = Combate(
                    idmision = resultSet.getInt("idmision"),
                    cazas = resultSet.getInt("cazas")
                )
                combates.add(combate)
            }
        }
        return combates
    }
}