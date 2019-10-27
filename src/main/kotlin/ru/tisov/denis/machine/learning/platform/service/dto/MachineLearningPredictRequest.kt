package ru.tisov.denis.machine.learning.platform.service.dto

import java.util.*

data class MachineLearningPredictRequest(val trainingJobId: UUID, val predictDataFilePath: String, val resultFilePath: String)