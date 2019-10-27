package ru.tisov.denis.machine.learning.platform.dao

import ru.tisov.denis.machine.learning.platform.entity.TrainingJob
import java.util.*

interface TrainingJobDao {

    fun save(job: TrainingJob)

    fun get(id: UUID): TrainingJob?

}