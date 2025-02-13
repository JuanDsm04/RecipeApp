package com.jdsm.myapplication.data.model

data class Recipe(
    val id: Int,
    val title: String,
    val description: String,
    val preparationTime: Int,
    val favorite: Boolean,
    val imagePath: String? = null
)