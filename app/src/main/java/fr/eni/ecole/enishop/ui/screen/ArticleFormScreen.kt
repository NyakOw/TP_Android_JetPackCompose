package fr.eni.ecole.enishop.ui.screen

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import fr.eni.ecole.enishop.ui.common.EniShopTextField
import fr.eni.ecole.enishop.ui.common.EniShopTopBar

@Composable
fun ArticleFormScreen(modifier: Modifier = Modifier, navHostController: NavHostController,     isDarkThemeActivated: Boolean,
                      onDarkThemeToggle: (Boolean) -> Unit) {

    var expanded by rememberSaveable {
        mutableStateOf(false)
    }
    var title by rememberSaveable {
        mutableStateOf("")
    }
    var description by rememberSaveable {
        mutableStateOf("")
    }
    var price by rememberSaveable {
        mutableStateOf("")
    }
    var category by rememberSaveable {
        mutableStateOf("")
    }

    val context = LocalContext.current

    Scaffold(
        topBar = { EniShopTopBar(
            navController = navHostController,
            isDarkThemeActivated = isDarkThemeActivated,
            onDarkThemeToggle =  onDarkThemeToggle
        ) }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 8.dp)
                .verticalScroll(
                    rememberScrollState()
                )
            ,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            EniShopTextField(
                label = "Titre",
                value = title,
                onValueChange = {
                    title = it
                })
            EniShopTextField(
                label = "Description",
                value = description,
                onValueChange = {
                    description = it
                })
            EniShopTextField(
                label = "Prix",
                value = price,
                onValueChange = {
                    price = it
                })

            EniShopTextField(
                value = category,
                modifier = Modifier.clickable {
                    expanded = !expanded
                },
                label = "Catégories",
                enabled = false,
                placeholder = { Text(text = "Choisir une catégorie !") },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "DropDown"
                    )
                }
            )
            CategoriesDropDowmMenu(
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                },
                onCategoryChange = {
                    category = it
                }
            )

            Button(modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = {
                    Toast.makeText(context, "$title a été ajouté", Toast.LENGTH_LONG).show()
                }) {
                Text("Enregistrer")
            }
        }
    }
}

@Composable
fun CategoriesDropDowmMenu(
    expanded: Boolean = false,
    onDismissRequest: () -> Unit = {},
    onCategoryChange: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {

    val categories = listOf("electronics", "jewelery", "men's clothing", "women's clothing")

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest

    ) {
        categories.forEach { category ->
            DropdownMenuItem(
                text = { Text(text = category) },
                onClick = {
                    onCategoryChange(category)
                    onDismissRequest()
                }
            )
        }

    }


}
