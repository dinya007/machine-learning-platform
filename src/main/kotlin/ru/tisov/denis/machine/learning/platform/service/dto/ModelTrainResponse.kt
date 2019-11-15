package ru.tisov.denis.machine.learning.platform.service.dto

import ru.tisov.denis.machine.learning.platform.entity.ModelStatus

data class ModelTrainResponse(val status: ModelStatus, val modelAnalysis: ModelAnalysis?)