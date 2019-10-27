package ru.tisov.denis.machine.learning.platform.dao

import ru.tisov.denis.machine.learning.platform.entity.Model
import java.util.*

interface ModelDao {

    fun save(job: Model)

    fun get(id: UUID): Model

}