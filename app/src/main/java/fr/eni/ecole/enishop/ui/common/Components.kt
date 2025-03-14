package fr.eni.ecole.enishop.ui.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EniShopTopBar(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    isDarkThemeActivated: Boolean,
    onDarkThemeToggle: (Boolean) -> Unit
) {

    var expanded by rememberSaveable {
        mutableStateOf(false)
    }
    TopAppBar(
        title = { EniShopTopBarTitle() },
        navigationIcon = {
            if (navController.previousBackStackEntry != null) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            }
        },
        actions = {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Burger Menu",
                modifier = Modifier
                    .clickable {
                        expanded = !expanded
                    }
            )
            DropdownMenu(expanded = expanded, onDismissRequest = {
                expanded = false
            }) {
                DropdownMenuItem(text = {
                    Text(text = "Dark Theme")
                },
                    trailingIcon = {
                        Switch(checked = isDarkThemeActivated, onCheckedChange = onDarkThemeToggle)
                    },
                    onClick = {
                        /*TODO*/
                })
            }
        }
    )
}


@Composable
fun EniShopTopBarTitle(modifier: Modifier = Modifier) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.ShoppingCart,
            contentDescription = "ShoppingCart",
            modifier = Modifier.size(40.dp)
        )
        Spacer(modifier = modifier.width(8.dp))
        Text(
            text = "ENI-SHOP",
            color = MaterialTheme.colorScheme.tertiary,
            fontSize = 40.sp
        )
    }
}

@Composable
fun EniShopTextField(

    label: String,
    value: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onValueChange: (String) -> Unit = {},
    trailingIcon: @Composable () -> Unit = {},
    placeholder: @Composable () -> Unit = {},

    ) {

    Surface(
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(text = label, fontSize = 20.sp)
            TextField(
                placeholder = placeholder,
                enabled = enabled,
                trailingIcon = trailingIcon,
                modifier = modifier.fillMaxWidth(),
                value = value,
                onValueChange = onValueChange
            )
        }
    }
}


@Composable
fun LoadingScreen(){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}