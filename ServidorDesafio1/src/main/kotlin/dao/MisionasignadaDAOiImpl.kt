package dao

import modelo.Misionasignada
import modelo.Usuario

class MisionasignadaDAOiImpl: MisionasignadaDAO {
    override fun insertar(misionesasignadas: Misionasignada): Boolean {
        val sql = "INSERT INTO misionesasignadas (id, idusuario, idmision, estado ) VALUES (?, ?, ?, ?)"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setInt(1, misionesasignadas.id)
            statement.setInt(2, misionesasignadas.idusuario)
            statement.setInt(3, misionesasignadas.idmision)
            statement.setString(4, misionesasignadas.estado)

            return statement.executeUpdate() > 0
        }
        return false
    }

    override fun obtenerPorId(id: Int): Misionasignada? {
        val sql = "SELECT * FROM misionesasignadas WHERE id = ?"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setInt(1, id)
            val resultSet = statement.executeQuery()

            if (resultSet.next()) {
                return Misionasignada(
                    id = resultSet.getInt("id"),
                    idusuario = resultSet.getInt("idusuario"),
                    idmision = resultSet.getInt("idmision"),
                    estado = resultSet.getString("estado")
                )
            }
        }
        return null
    }

    override fun eliminar(id: Int): Boolean {
        val sql = "DELETE FROM misionesasignadas WHERE id = ?"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setInt(1, id)

            return statement.executeUpdate() > 0
        }
        return false
    }

    override fun obtenerTodas(): List<Misionasignada> {
        val misionesasignadas = mutableListOf<Misionasignada>()
        val sql = "SELECT * FROM misionesasignadas"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            val resultSet = statement.executeQuery()

            while (resultSet.next()) {
                val misionasignada = Misionasignada(
                    id = resultSet.getInt("id"),
                    idusuario = resultSet.getInt("idusuario"),
                    idmision = resultSet.getInt("idmision"),
                    estado = resultSet.getString("estado")
                )
                misionesasignadas.add(misionasignada)
            }
        }
        return misionesasignadas
    }

    override fun obtenerSuperadas(): List<Misionasignada> {
        val misionesSuperadas = mutableListOf<Misionasignada>()
        val sql = "SELECT * FROM misionesasignadas WHERE estado = 'Superada'"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            val resultSet = statement.executeQuery()

            while (resultSet.next()) {
                val misionasignada = Misionasignada(
                    id = resultSet.getInt("id"),
                    idusuario = resultSet.getInt("idusuario"),
                    idmision = resultSet.getInt("idmision"),
                    estado = resultSet.getString("estado")
                )
                misionesSuperadas.add(misionasignada)
            }
        }
        return misionesSuperadas
    }

    override fun obtenerNoSuperadas(): List<Misionasignada> {
        val misionesNoSuperadas = mutableListOf<Misionasignada>()
        val sql = "SELECT * FROM misionesasignadas WHERE estado = 'No superada'"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            val resultSet = statement.executeQuery()

            while (resultSet.next()) {
                val misionasignada = Misionasignada(
                    id = resultSet.getInt("id"),
                    idusuario = resultSet.getInt("idusuario"),
                    idmision = resultSet.getInt("idmision"),
                    estado = resultSet.getString("estado")
                )
                misionesNoSuperadas.add(misionasignada)
            }
        }
        return misionesNoSuperadas
    }
}
