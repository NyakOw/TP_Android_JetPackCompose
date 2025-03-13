package fr.eni.ecole.enishop.dao

import fr.eni.ecole.enishop.dao.memory.ArticleDAOMemoryImpl

abstract class DaoFactory {

    companion object {

        fun createArticleDAO(type: DaoType): ArticleDAO {

//            val dao : ArticleDAO = when (type) {
//                DaoType.MEMORY -> ArticleDAOMemoryImpl()
//                DaoType.NETWORK -> TODO()
//            }

            return ArticleDAOMemoryImpl()

        }
    }
}