package ru.tisov.denis.machine.learning.platform.service.dto

data class PredictionRequest(val dataPath: String, val modelPath: String, val resultPath: String)