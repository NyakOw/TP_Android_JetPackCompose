package fr.eni.ecole.enishop.repository

import fr.eni.ecole.enishop.bo.Article
import fr.eni.ecole.enishop.dao.ArticleDAO
import fr.eni.ecole.enishop.dao.DaoFactory
import fr.eni.ecole.enishop.dao.DaoType
import fr.eni.ecole.enishop.service.ArticleApiService

class ArticleRepository(
    private val articleDAORoom: ArticleDAO,
    private val articleApiService: ArticleApiService
) {

    val articleDaoMemory = DaoFactory.createArticleDAO(DaoType.MEMORY)

    suspend fun getArticle(id: Long, daoType: DaoType = DaoType.MEMORY): Article {
        return when (daoType) {
            DaoType.MEMORY -> articleDaoMemory.findById(id)
            DaoType.NETWORK -> articleApiService.getArticleData(id)
            else -> articleDAORoom.findById(id)
        }
    }

    suspend fun addArticle(article: Article, daoType: DaoType = DaoType.MEMORY): Long {
        return when (daoType) {
            DaoType.MEMORY -> articleDaoMemory.insert(article)
            else -> articleDAORoom.insert(article)
        }
    }

    suspend fun getAllArticles(daoType: DaoType = DaoType.MEMORY): List<Article> {
        return when (daoType) {
            DaoType.MEMORY -> articleDaoMemory.findAll()
            DaoType.NETWORK -> articleApiService.getAllArticleData()
            else -> articleDAORoom.findAll()
        }
    }

    fun deleteArticle(article: Article, daoType: DaoType = DaoType.MEMORY) {
        return when (daoType) {
            DaoType.MEMORY -> articleDaoMemory.delete(article)
            else -> articleDAORoom.delete(article)
        }

    }

    suspend fun getAllCategories() : List<String>{
        return ArticleApiService.articleApiService.getCategories()
    }
}