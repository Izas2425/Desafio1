package dao


import modelo.Mision
import modelo.Nave
import java.sql.Statement

class MisionDAOImpl: MisionDAO {
    override fun insertar(mision: Mision): Boolean {
        val sql = "INSERT INTO misiones (idMision, nombre, experiencia, matriculanave, descripcion) VALUES (?, ?, ?, ?, ?)"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setInt(1, mision.idmision)
            statement.setString(2, mision.nombre)
            statement.setInt(3, mision.experiencia)
            statement.setString(4, mision.matriculanave)
            statement.setString(5, mision.descripcion)
            return statement.executeUpdate() > 0
        }
        return false
    }

    override fun insertarYObtenerId(mision: Mision): Int? {
        val sql = "INSERT INTO misiones (idMision, nombre, experiencia, matriculanave, descripcion) VALUES (?, ?, ?, ?, ?)"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
            statement.setInt(1, mision.idmision)
            statement.setString(2, mision.nombre)
            statement.setInt(3, mision.experiencia)
            statement.setString(4, mision.matriculanave)
            statement.setString(5, mision.descripcion)

            val affectedRows = statement.executeUpdate()
            if(affectedRows >0){
                val generatedKeys = statement.generatedKeys
                if(generatedKeys.next()){
                    return generatedKeys.getInt(1)
                }
            }
        }
        return null
    }

    override fun obtenerPorId(idMision: Int): Mision? {
        val sql = "SELECT * FROM misiones WHERE idMision = ?"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setInt(1, idMision)
            val resultSet = statement.executeQuery()

            if (resultSet.next()) {
                return Mision(
                    idmision = resultSet.getInt("idmision"),
                    nombre = resultSet.getString("nombre"),
                    experiencia = resultSet.getInt("experiencia"),
                    matriculanave = resultSet.getString("matriculanave"),
                    descripcion = resultSet.getString("descripcion")
                )
            }
        }
        return null
    }

    override fun eliminar(idMision: Int): Boolean {
        val sql = "DELETE FROM misiones WHERE idMision = ?"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setInt(1, idMision)

            return statement.executeUpdate() > 0
        }
        return false
    }

    override fun obtenerTodos(): List<Mision> {
        val misiones = mutableListOf<Mision>()
        val sql = "SELECT * FROM misiones"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            val resultSet = statement.executeQuery()

            while (resultSet.next()) {
                val mision = Mision(
                    idmision = resultSet.getInt("idmision"),
                    nombre = resultSet.getString("nombre"),
                    experiencia = resultSet.getInt("experiencia"),
                    matriculanave = resultSet.getString("matriculanave"),
                    descripcion = resultSet.getString("descripcion")
                )
                misiones.add(mision)
            }
        }
        return misiones
    }

    override fun obtenerUltimoId(): Int? {
        val sql = "SELECT MAX(idmision) AS ultimoId FROM misiones"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            val resultSet = statement.executeQuery()
            if (resultSet.next()) {
                return resultSet.getInt("ultimoId")
            }

        }
        return null
    }


}