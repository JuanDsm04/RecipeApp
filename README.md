# RecipeApp

RecipeApp is a mobile application designed to manage recipes. It provides an easy-to-use platform where users can store their favorite recipes, add descriptions, preparation times, and even images of the dishes. The app also allows users to mark recipes as favorites for quick access.

## Features

- **Create and manage recipes**: Users can add new recipes with details such as title, description, preparation time, and image.
- **Favorites**: Mark recipes as favorites for easy access.
- **Recipe images**: Upload images for each recipe to visually enhance the recipe cards.
- **Login and Logout**: Users can sign in to the app to access their personal recipe collection, and sign out when they are done.

## Services

- **Recipe Management**: The app allows users to create and manage recipes. The data is stored locally.
- **Image Storage**: Recipe images are stored locally.
- **Authentication**: Users can log in and out, ensuring their recipe data is linked to their account.

## Libraries

- **Room**: Used for local storage of recipe data, enabling efficient retrieval and manipulation of recipes.
- **Coil**: Handles image loading and caching for recipes.
- **Navigation**: Provides safe navigation between screens.
- **Kotlin Coroutines**: Manages background tasks for database operations and image loading.