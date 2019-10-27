package ru.tisov.denis.machine.learning.platform.entity

import java.util.*

data class Model(val id: UUID, val datasetId: UUID, val type: ModelType,
                 val algorithm: Algorithm, val status: ModelStatus, val modelPath: String)