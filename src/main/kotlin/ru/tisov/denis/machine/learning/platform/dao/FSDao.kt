package ru.tisov.denis.machine.learning.platform.dao

interface FSDao {

    fun writeFile(path: String, content: ByteArray)

    fun readFile(path: String): String

}