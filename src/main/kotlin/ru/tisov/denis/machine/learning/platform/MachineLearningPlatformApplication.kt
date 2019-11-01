package ru.tisov.denis.machine.learning.platform

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import ru.tisov.denis.machine.learning.platform.dao.DatasetDao
import ru.tisov.denis.machine.learning.platform.dao.ModelDao
import ru.tisov.denis.machine.learning.platform.entity.*
import java.util.*


@SpringBootApplication
class MachineLearningPlatformApplication

fun main(args: Array<String>) {
    val context = runApplication<MachineLearningPlatformApplication>(*args)

    val datasetDao = context.getBean(DatasetDao::class.java)
    val modelDao = context.getBean(ModelDao::class.java)

    val datasetId1 = UUID.randomUUID()
    val datasetId2 = UUID.randomUUID()
    datasetDao.save(Dataset(datasetId1, "/some/path/1"))
    datasetDao.save(Dataset(datasetId2, "/some/path/2"))
    modelDao.save(Model(UUID.randomUUID(), datasetId1, ModelType.REGRESSION, Algorithm.XG_BOOST, ModelStatus.SUCCESS, "/model/path/1"))
    modelDao.save(Model(UUID.randomUUID(), datasetId1, ModelType.REGRESSION, Algorithm.XG_BOOST, ModelStatus.ERROR, "/model/path/2"))
    modelDao.save(Model(UUID.randomUUID(), datasetId1, ModelType.REGRESSION, Algorithm.XG_BOOST, ModelStatus.STARTED, "/model/path/3"))
    modelDao.save(Model(UUID.randomUUID(), datasetId2, ModelType.CLASSIFICATION, Algorithm.XG_BOOST, ModelStatus.STARTED, "/model/path/10"))
}


