package ru.tisov.denis.machine.learning.platform.entity

import java.util.*

data class Prediction(val id: UUID, val modelId: UUID, val dataPath: String,
                      val status: PredictionStatus, val resultPath: String)