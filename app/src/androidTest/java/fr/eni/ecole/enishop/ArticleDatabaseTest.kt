package fr.eni.ecole.enishop

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import fr.eni.ecole.enishop.bo.Article
import fr.eni.ecole.enishop.room.AppDatabase
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Date


@RunWith(AndroidJUnit4::class)
class MusicDatabaseTest {

    private lateinit var db: AppDatabase
    private lateinit var articleATester : Article

    @Before
    fun createDb() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).build()
        articleATester = Article(
            id = 0L,
            name = "Chausseur Blouge",
            description = "Très confortable grace à sa couleur",
            price = 20.0,
            urlImage = "Image Chaussure",
            date = Date()
        )
    }


    @After
    fun closeDb() {
        db.close()
    }


    @Test
    fun testInsertArticle() = runTest {
        val id = db.articleDao().insert(articleATester)
        assertTrue(id > 0)
    }

    @Test
    fun testFindArticleById() = runTest {
        val id = db.articleDao().insert(articleATester)
        assertTrue(id > 0)

        val foundArticle = db.articleDao().findById(id)
        assertNotNull(foundArticle)
        assertNotNull(foundArticle.name)
        assertEquals(articleATester.name, foundArticle.name)
        assertNotNull(foundArticle.description)
        assertEquals(articleATester.description, foundArticle.description)
    }

    @Test
    fun testDeleteArticle() = runTest {
        val id = db.articleDao().insert(articleATester)
        assertTrue(id > 0)

        val articleFound = db.articleDao().findById(id)
        assertNotNull(articleFound)

        val articleDeleted = db.articleDao().findById(id)
        assertNull(articleDeleted)
    }
}