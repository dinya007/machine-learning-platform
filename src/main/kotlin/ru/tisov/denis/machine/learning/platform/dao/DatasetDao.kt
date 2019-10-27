package ru.tisov.denis.machine.learning.platform.dao

import ru.tisov.denis.machine.learning.platform.entity.Dataset
import java.util.*

interface DatasetDao {

    fun save(dataset: Dataset)

    fun get(id: UUID): Dataset?

}