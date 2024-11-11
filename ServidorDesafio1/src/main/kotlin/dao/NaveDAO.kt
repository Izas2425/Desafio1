package dao

import modelo.Nave
import modelo.Usuario

interface NaveDAO {
        fun insertar(nave: Nave): Boolean
        fun obtenerPorMatricula(matricula: String): Nave?
        fun actualizar(nave: Nave): Boolean
        fun eliminar(matricula: String): Boolean
        fun obtenerTodos(): List<Nave>
}