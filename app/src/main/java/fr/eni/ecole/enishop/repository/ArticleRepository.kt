package fr.eni.ecole.enishop.repository

import fr.eni.ecole.enishop.bo.Article
import fr.eni.ecole.enishop.dao.ArticleDAO
import fr.eni.ecole.enishop.dao.DaoFactory
import fr.eni.ecole.enishop.dao.DaoType

class ArticleRepository(
    private val articleDAORoom: ArticleDAO
) {

    val articleDaoMemory = DaoFactory.createArticleDAO(DaoType.MEMORY)

    fun getArticle(id: Long, daoType: DaoType = DaoType.MEMORY): Article {
        return when (daoType) {
            DaoType.MEMORY -> articleDaoMemory.findById(id)
            else -> articleDAORoom.findById(id)
        }

    }

    suspend fun addArticle(article: Article, daoType: DaoType = DaoType.MEMORY): Long {
        return when (daoType) {
            DaoType.MEMORY -> articleDaoMemory.insert(article)
            else -> articleDAORoom.insert(article)
        }
    }

    fun getAllArticles(daoType: DaoType = DaoType.MEMORY): List<Article> {
        return when (daoType) {
            DaoType.MEMORY -> articleDaoMemory.findAll()
            else -> articleDAORoom.findAll()
        }
    }

    fun deleteArticle(article: Article, daoType: DaoType = DaoType.MEMORY) {
        return when (daoType) {
            DaoType.MEMORY -> articleDaoMemory.delete(article)
            else -> articleDAORoom.delete(article)
        }

    }
}