package ru.tisov.denis.machine.learning.platform.dao

import org.springframework.stereotype.Component
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

@Component
class DefaultFSDao : FSDao {

    override fun readFile(path: String): String {
        return Files.readString(Paths.get(path))
    }

    override fun writeFile(path: String, content: ByteArray) {
        File(path).parentFile.mkdirs()
        File(path).writeBytes(content)
    }

}