package fr.eni.ecole.enishop.ui.screen

import android.app.SearchManager
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import fr.eni.ecole.enishop.bo.Article
import fr.eni.ecole.enishop.ui.common.EniShopTopBar
import fr.eni.ecole.enishop.utils.toFrenchStringFormat
import fr.eni.ecole.enishop.vm.ArticleDetailViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController

@Composable
fun ArticleDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: ArticleDetailViewModel = viewModel(factory = ArticleDetailViewModel.Factory),
    navHostController: NavHostController,
    articleId : Long = 1,
    isDarkThemeActivated: Boolean,
    onDarkThemeToggle: (Boolean) -> Unit
) {

    LaunchedEffect(key1 = Unit) {
        viewModel.initArticle(articleId)
    }

    val article by viewModel.article.collectAsState()

    Scaffold(
        topBar = { EniShopTopBar(
            navController = navHostController,
            isDarkThemeActivated = isDarkThemeActivated,
            onDarkThemeToggle =  onDarkThemeToggle
        ) }
    ) {
        Column(modifier = Modifier.padding(it)) {
            ArticleDetail(article, articleDetailViewModel = viewModel)
        }
    }
}

@Composable
fun ArticleDetail(
    article: Article,
    modifier: Modifier = Modifier,
    articleDetailViewModel: ArticleDetailViewModel
) {

    val context = LocalContext.current
    val isCheckedFav by articleDetailViewModel.checkedFav.collectAsState()

    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            modifier = Modifier.clickable {
                val intent = Intent(Intent.ACTION_WEB_SEARCH).apply {
                    putExtra(SearchManager.QUERY, article.name)
                }
                context.startActivity(intent)
            },
            text = article.name,
            fontSize = 30.sp,
            style = MaterialTheme.typography.titleLarge,
            lineHeight = 1.2.em,
            textAlign = TextAlign.Justify
        )
        Surface(
            color = MaterialTheme.colorScheme.surfaceContainer,
            modifier = Modifier.fillMaxWidth()
        ) {
            AsyncImage(
                model = article.urlImage,
                contentDescription = article.name,
                modifier = Modifier.size(200.dp)
            )
        }
        Text(
            text = article.description,
            textAlign = TextAlign.Justify
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Prix : ${article.price} €")
            Text(text = "Date de sortie : ${article.date.toFrenchStringFormat()}")
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(checked = isCheckedFav, onCheckedChange = {
                if (it) {
                    articleDetailViewModel.addArticle()
                    Toast.makeText(context, "Article enregistré dans vos favoris", Toast.LENGTH_SHORT).show()
                } else {
                    articleDetailViewModel.deleteArticle()
                    Toast.makeText(context, "Article supprimé de vos favoris", Toast.LENGTH_SHORT).show()
                }
                articleDetailViewModel.updateCheckBox()
            })
            Text(text = "Favoris ?")
        }
    }

}
