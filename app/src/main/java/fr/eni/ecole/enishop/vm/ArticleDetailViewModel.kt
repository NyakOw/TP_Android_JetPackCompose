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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ArticleDetailViewModel(private val articleRepository: ArticleRepository) : ViewModel() {


    private val _article = MutableStateFlow<Article>(Article())
    val article = _article.asStateFlow()

    private val _checkedFav = MutableStateFlow<Boolean>(false)
    val checkedFav = _checkedFav.asStateFlow()

    suspend fun initArticle(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val a = articleRepository.getArticle(id, DaoType.ROOM)
            if (a != null)
            _checkedFav.value = true
            val currentArticle = articleRepository.getArticle(id, daoType = DaoType.NETWORK)
            _article.value = currentArticle
        }
    }

    fun addArticle(){
        viewModelScope.launch(Dispatchers.IO) {
            articleRepository.addArticle(article.value, DaoType.ROOM)
        }
    }

    fun deleteArticle() {
        viewModelScope.launch(Dispatchers.IO) {
            articleRepository.deleteArticle(article.value, DaoType.ROOM)
        }
    }

    fun updateCheckBox() {
        _checkedFav.value = !_checkedFav.value
    }

    companion object {

        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {

                val application = checkNotNull(extras[APPLICATION_KEY])
                return ArticleDetailViewModel(
                    ArticleRepository(AppDatabase.getInstance(application.applicationContext).articleDao(),
                        ArticleApiService.articleApiService)
                ) as T
            }
        }
    }

}

