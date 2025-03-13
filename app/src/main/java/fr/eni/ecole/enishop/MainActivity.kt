package fr.eni.ecole.enishop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import fr.eni.ecole.enishop.service.DataStoreManager
import fr.eni.ecole.enishop.ui.screen.ArticleDetailScreen
import fr.eni.ecole.enishop.ui.screen.ArticleFormScreen
import fr.eni.ecole.enishop.ui.screen.ArticleListScreen
import fr.eni.ecole.enishop.ui.theme.EniShopTheme
import fr.eni.ecole.enishop.utils.ArticleDetailDestination
import fr.eni.ecole.enishop.utils.ArticleFormDestination
import fr.eni.ecole.enishop.utils.ArticleListDestination
import kotlinx.coroutines.launch


private const val TAG = "MainActivity"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        var activity = this

        setContent {
            var isDarkThemeActivated by rememberSaveable {
                mutableStateOf(false)
            }

            // Lire le DataStore
            LaunchedEffect(Unit) {
                DataStoreManager.isDarkThemeActivated(activity).collect{
                    isDarkThemeActivated = it
                }
            }

            EniShopTheme(darkTheme = isDarkThemeActivated) {
                EniShopApp(isDarkThemeActivated = isDarkThemeActivated, onDarkThemeToggle = {
                    isActivated : Boolean ->
                        lifecycleScope.launch {
                            DataStoreManager.setDarkThemeActivated(activity, isActivated)
                        }
                })
            }
        }
    }
}


@Composable
fun EniShopApp(
    navHostController: NavHostController = rememberNavController(),
    isDarkThemeActivated: Boolean,
    onDarkThemeToggle: (Boolean) -> Unit
) {
    EniShopNavHost(
        navHostController,
        isDarkThemeActivated = isDarkThemeActivated,
        onDarkThemeToggle =  onDarkThemeToggle
    )
}

@Composable
fun EniShopNavHost(
    navHostController: NavHostController,
    isDarkThemeActivated: Boolean,
    onDarkThemeToggle: (Boolean) -> Unit
) {
    NavHost(
        navController = navHostController,
        startDestination = ArticleListDestination.route
    ) {

        composable(
            ArticleListDestination.route
        ){
            ArticleListScreen(
                onButtonClickBehavior = {navHostController.navigate(ArticleFormDestination.route)},
                onClickBehavior = {navHostController.navigate("${ArticleDetailDestination.route}/$it")},
                navHostController = navHostController,
                isDarkThemeActivated = isDarkThemeActivated,
                onDarkThemeToggle =  onDarkThemeToggle
            )
        }

        composable(
            ArticleFormDestination.route
        ) {
            ArticleFormScreen(navHostController = navHostController,
                isDarkThemeActivated = isDarkThemeActivated,
                onDarkThemeToggle =  onDarkThemeToggle)
        }

        composable(
            ArticleDetailDestination.routeWithArgs,
            arguments = ArticleDetailDestination.args
        ) { navBackStackEntry ->
            val articleId = navBackStackEntry.arguments?.getLong(ArticleDetailDestination.argName)?:0
            ArticleDetailScreen(articleId = articleId, navHostController = navHostController,
                isDarkThemeActivated = isDarkThemeActivated,
                onDarkThemeToggle =  onDarkThemeToggle)
        }

    }
}

