package ru.tisov.denis.machine.learning.platform.entity

import java.util.*
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Model(@Id val id: UUID, val datasetId: UUID, val type: ModelType,
                 val algorithm: Algorithm, val status: ModelStatus, val modelPath: String,
                 val mean: Double? = null, val std: Double? = null)