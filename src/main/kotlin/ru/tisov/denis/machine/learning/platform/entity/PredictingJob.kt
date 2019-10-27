package ru.tisov.denis.machine.learning.platform.entity

import java.util.*

data class PredictingJob(val id: UUID, val status: PredictingJobStatus, val resultPath: String? = null)