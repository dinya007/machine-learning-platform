package ru.tisov.denis.machine.learning.platform.dao

import ru.tisov.denis.machine.learning.platform.entity.Prediction
import java.util.*

interface PredictionDao {

    fun save(job: Prediction)

    fun get(id: UUID): Prediction

}