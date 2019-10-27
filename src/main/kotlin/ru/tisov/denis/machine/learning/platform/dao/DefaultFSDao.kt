package ru.tisov.denis.machine.learning.platform.dao

import org.springframework.stereotype.Component
import java.io.File

@Component
class DefaultFSDao : FSDao {

    override fun writeFile(path: String, content: ByteArray) {
        File(path).parentFile.mkdirs()
        File(path).writeBytes(content)
    }

}