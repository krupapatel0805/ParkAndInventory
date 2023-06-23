package com.example.project_g20.models

import java.io.Serializable

data class Park(
    val name: String = "",
    val distance: Double = 0.0,
    var date: String = "",
    var notes: String = ""
) : Serializable