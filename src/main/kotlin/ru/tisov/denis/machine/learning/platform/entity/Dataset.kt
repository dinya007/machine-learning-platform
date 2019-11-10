package ru.tisov.denis.machine.learning.platform.entity

import java.util.*
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Dataset(@Id val id: UUID, val status: DatasetStatus, val dataPath: String, val analyzePath: String)