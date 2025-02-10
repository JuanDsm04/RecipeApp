package com.jdsm.myapplication.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jdsm.myapplication.data.model.Recipe

@Entity
data class RecipeEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val description: String,
    val preparationTime: Int,
    val favorite: Boolean
)

fun Recipe.mapToEntity(): RecipeEntity {
    return RecipeEntity(
        id = id,
        title = title,
        description = description,
        preparationTime = preparationTime,
        favorite = favorite
    )
}

fun RecipeEntity.mapToModel(): Recipe {
    return Recipe(
        id = id,
        title = title,
        description = description,
        preparationTime = preparationTime,
        favorite = favorite
    )
}