package fr.eni.ecole.enishop.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import fr.eni.ecole.enishop.bo.Article
import fr.eni.ecole.enishop.dao.ArticleDAO
import fr.eni.ecole.enishop.utils.DateRoomConverter

@Database(entities = [Article::class], version = 1)
@TypeConverters(DateRoomConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun articleDao() : ArticleDAO

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            var instance = INSTANCE

            if (instance == null) {
                instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "NotreMusique.db",
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
            }
            return instance
        }
    }
}