package ru.tisov.denis.machine.learning.platform.entity

data class DatasetAnalyze(
        val categorical: Categorical,
        val numerical: Numerical
)

data class Categorical(
        val count: Long,
        val features: List<CategoricalFeature>
)

data class Numerical(
        val count: Long,
        val features: List<NumericalFeature>
)

data class CategoricalFeature(
        val count: Long,
        val frequencyTop: Long,
        val missing: Long,
        val name: String,
        val top: String,
        val unique: Long,
        val values: List<CategoricalValue>
)

data class NumericalFeature(
        val correlation: Double,
        val count: Long,
        val max: Double,
        val mean: Double,
        val median: Double,
        val min: Double,
        val missing: Long,
        val name: String,
        val values: List<Double>,
        val zeros: Long
)

data class CategoricalValue(
        val count: Int,
        val name: String
)