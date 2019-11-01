package ru.tisov.denis.machine.learning.platform.entity

import java.util.*
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Prediction(@Id val id: UUID, val modelId: UUID, val dataPath: String,
                      val status: PredictionStatus, val resultPath: String)