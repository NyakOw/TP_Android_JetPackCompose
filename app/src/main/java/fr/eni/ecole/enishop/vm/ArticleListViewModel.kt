package fr.eni.ecole.enishop.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import fr.eni.ecole.enishop.bo.Article
import fr.eni.ecole.enishop.dao.DaoType
import fr.eni.ecole.enishop.repository.ArticleRepository
import fr.eni.ecole.enishop.room.AppDatabase
import fr.eni.ecole.enishop.service.ArticleApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ArticleListViewModel(
    private val articleRepository: ArticleRepository
) : ViewModel() {

    private val _articles = MutableStateFlow<List<Article>>(emptyList())
    val articles: StateFlow<List<Article>>
        get() = _articles

    private val _categories = MutableStateFlow<List<String>>(emptyList())
    val categories: StateFlow<List<String>>
        get() = _categories


    var isLoading  = MutableStateFlow<Boolean>(true)
    init {
        viewModelScope.launch(Dispatchers.IO) {
            val a = async {
                _articles.value = articleRepository.getAllArticles(daoType = DaoType.NETWORK)
            }
            val c = async {
                _categories.value = articleRepository.getAllCategories()
            }
            awaitAll(a, c)
            isLoading.value = false
        }
    }


    companion object {

        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
//                // Get the Application object from extras
//                val application = checkNotNull(extras[APPLICATION_KEY])
//                // Create a SavedStateHandle for this ViewModel from extras
//                val savedStateHandle = extras.createSavedStateHandle()

                val application = checkNotNull(extras[APPLICATION_KEY])
                return ArticleListViewModel(
                    ArticleRepository(
                        AppDatabase.getInstance(application.applicationContext).articleDao(),
                        ArticleApiService.articleApiService
                    )
                ) as T
            }
        }
    }


}