package fr.eni.ecole.enishop.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.TypeConverters
import fr.eni.ecole.enishop.bo.Article
import fr.eni.ecole.enishop.utils.DateRoomConverter

@Dao
@TypeConverters(DateRoomConverter::class)
interface ArticleDAO {

    @Insert
    suspend fun insert(article: Article) : Long

    @Query("SELECT * FROM ARTICLE WHERE id = :id")
    fun findById(id : Long) : Article

    @Query("SELECT * FROM ARTICLE")
    fun findAll() : List<Article>

    @Delete
    fun delete(article: Article)
}