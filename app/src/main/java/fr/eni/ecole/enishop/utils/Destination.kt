package fr.eni.ecole.enishop.utils

import androidx.navigation.NavType
import androidx.navigation.navArgument

interface Destination {
    val route : String
}


object ArticleListDestination : Destination {
    override val route : String = "Home"
}

object ArticleFormDestination : Destination {
    override val route : String = "ArticleForm"
}

object ArticleDetailDestination : Destination{
    override val route : String = "ArticleDetail"

    val argName by lazy {
        "articleId"
    }

    var args = listOf(navArgument(argName){
        type = NavType.LongType
    })

    val routeWithArgs = "$route/{$argName}"
}