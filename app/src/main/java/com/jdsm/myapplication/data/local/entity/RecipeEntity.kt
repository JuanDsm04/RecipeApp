package com.jdsm.myapplication.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jdsm.myapplication.data.model.Recipe

@Entity
data class RecipeEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String,
    val preparationTime: Int,
    val favorite: Boolean,
    val imagePath: String? = null
)

fun Recipe.mapToEntity(): RecipeEntity {
    return RecipeEntity(
        id = id,
        title = title,
        description = description,
        preparationTime = preparationTime,
        favorite = favorite,
        imagePath = imagePath
    )
}

fun RecipeEntity.mapToModel(): Recipe {
    return Recipe(
        id = id,
        title = title,
        description = description,
        preparationTime = preparationTime,
        favorite = favorite,
        imagePath = imagePath
    )
}