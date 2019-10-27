package ru.tisov.denis.machine.learning.platform.dao

import ru.tisov.denis.machine.learning.platform.entity.PredictingJob
import java.util.*

interface PredictingJobDao {

    fun save(job: PredictingJob)

    fun get(id: UUID): PredictingJob?

}