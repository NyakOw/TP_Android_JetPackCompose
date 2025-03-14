package fr.eni.ecole.enishop

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import fr.eni.ecole.enishop.bo.Article
import fr.eni.ecole.enishop.ui.screen.ArticleDetail
import org.junit.Rule
import org.junit.Test
import java.util.Date

class ArticleDetailTest {

    @get: Rule
    val composeTestRule = createComposeRule()

    @Test
    fun articleDetail_Show_OK(){
        val articleTest = Article(
            id = 6,
            name = "Article Test",
            description = "Description Test",
            price = 22.15,
            urlImage = "https://ex.com/image.jpg",
            date = Date()

        )

        // Charger le composant
        composeTestRule.setContent {
            Surface(
                modifier = Modifier.fillMaxSize()
            ) {
//                ArticleDetail(articleTest)
            }
        }

        composeTestRule.onNodeWithText("Article Test").assertIsDisplayed()
        composeTestRule.onNodeWithText("Description Test").assertIsDisplayed()
        composeTestRule.onNodeWithText("Prix : 22.15 €").assertIsDisplayed()
    }
}