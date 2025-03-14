package fr.eni.ecole.enishop.ui.screen

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Card
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import fr.eni.ecole.enishop.vm.ArticleListViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.google.android.material.floatingactionbutton.FloatingActionButton
import fr.eni.ecole.enishop.bo.Article
import fr.eni.ecole.enishop.ui.common.EniShopTopBar
import fr.eni.ecole.enishop.ui.common.LoadingScreen
import java.util.Date

@Composable
fun ArticleListScreen(
    modifier: Modifier = Modifier,
    viewModel: ArticleListViewModel = viewModel(factory = ArticleListViewModel.Factory),
    onClickBehavior: (Long) -> Unit,
    onButtonClickBehavior: () -> Unit,
    navHostController: NavHostController,
    isDarkThemeActivated: Boolean,
    onDarkThemeToggle: (Boolean) -> Unit
) {

    val articles by viewModel.articles.collectAsState()
    val categories by viewModel.categories.collectAsState()

    var selectedCategory by rememberSaveable {
        mutableStateOf("")
    }

    val selectedArticles = if (selectedCategory != "") {
        articles.filter {
            it.category == selectedCategory
        }
    } else {
        articles
    }

    val isLoading by viewModel.isLoading.collectAsState()

    Scaffold(
        topBar = {
            EniShopTopBar(
                navController = navHostController,
                isDarkThemeActivated = isDarkThemeActivated,
                onDarkThemeToggle = onDarkThemeToggle
            )
        }
    ) {
        if (isLoading) {
            LoadingScreen()
        } else {
            Column(modifier = Modifier.padding(it)) {
                CategoryFilterChip(
                    categories = categories,
                    selectedCategory = selectedCategory,
                    onCategoryChange = {
                        selectedCategory = it
                    }
                )
                ArticleList(articles = selectedArticles, onClickBehavior = onClickBehavior)

                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(30.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.Bottom
                ) {
                    ListArticleFloatingActionButton(onButtonClickBehavior = onButtonClickBehavior)
                }
            }
        }
    }
}

@Composable
fun ArticleList(
    onClickBehavior: (Long) -> Unit,
    articles: List<Article>
) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        items(articles) {
            ArticleItem(article = it, onClickBehavior = onClickBehavior)
        }

    }


}


@SuppressLint("DefaultLocale")
@Composable
fun ArticleItem(
    article: Article,
    modifier: Modifier = Modifier,
    onClickBehavior: (Long) -> Unit
) {
    Card(
        modifier = Modifier.clickable {
            onClickBehavior(article.id)
        }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(8.dp)
        ) {
            AsyncImage(
                model = article.urlImage,
                contentDescription = article.name,
                modifier = Modifier
                    .size(80.dp)
                    .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
                    .padding(8.dp)
            )
            Text(
                text = article.name
            )
            Text(text = "${String.format("%.2f", article.price)} €")
        }
    }
}


@Composable
fun CategoryFilterChip(
    categories: List<String>,
    selectedCategory: String = "",
    onCategoryChange: (String) -> Unit = {}
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(categories) { category ->
            FilterChip(
                selected = category == selectedCategory,
                onClick = {
                    if (selectedCategory == category) {
                        onCategoryChange("")
                    } else {
                        onCategoryChange(category)
                    }
                },
                label = { Text(text = category) },
                leadingIcon = if (selectedCategory == category) {
                    {
                        Icon(
                            imageVector = Icons.Default.Done,
                            contentDescription = "Category Checked"
                        )
                    }
                } else {
                    null
                }
            )
        }
    }
}

@Composable
fun ListArticleFloatingActionButton(
    onButtonClickBehavior: () -> Unit
) {
    FloatingActionButton(
        onClick = { onButtonClickBehavior() },
        shape = CircleShape
    ) {
        Icon(
            modifier = Modifier.size(60.dp),
            imageVector = Icons.Default.Add,
            contentDescription = "Add",
        )
    }
}