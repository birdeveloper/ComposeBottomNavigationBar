package com.birdeveloper.composebottomnavigationbar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.birdeveloper.composebottomnavigationbar.data.BottomNavigationItem
import com.birdeveloper.composebottomnavigationbar.ui.theme.ComposeBottomNavigationBarTheme
import com.birdeveloper.composebottomnavigationbar.ui.theme.primaryColor

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeBottomNavigationBarTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {

    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val route = navBackStackEntry?.destination?.route ?: "home"
    Scaffold(
        content = ({
            NavHost(
                navController = navController,
                startDestination = "home",
                modifier = modifier
            ) {
                composable("home") {
                    HomeScreen()
                }
                composable("analytics") {
                    AnalyticsScreen()
                }
                composable("search") {
                    SearchScreen()
                }
                composable("profile") {
                    ProfileScreen()
                }
            }
        }),
        bottomBar = {
            BottomNavigationBar(route = route) {
                navController.navigate(it)
            }
        })
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeBottomNavigationBarTheme {
        Greeting("Android")
    }
}

@Composable
fun HomeScreen() {
    Text(text = "HomeScreen", fontSize = 20.sp)
}

@Composable
fun AnalyticsScreen() {
    Text(text = "AnalyticsScreen", fontSize = 20.sp)
}

@Composable
fun SearchScreen() {
    Text(text = "SearchScreen", fontSize = 20.sp)
}

@Composable
fun ProfileScreen() {
    Text(text = "ProfileScreen", fontSize = 20.sp)
}

@Composable
fun BottomNavigationBar(route: String, onNavigate: (String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.18f)
            .clip(
                RoundedCornerShape(
                    topEnd = 10.dp,
                    topStart = 10.dp,
                    bottomEnd = 0.dp,
                    bottomStart = 0.dp
                )
            )
            .offset(y = 40.dp),
        colors = CardDefaults.cardColors(containerColor = primaryColor),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(Alignment.Center)
                .padding(vertical = 15.dp, horizontal = 15.dp)
        ) {
            Row {
                getItems().forEach {
                    AddBottomNavigationBarItem(route, it, onNavigate)
                }
            }
        }
    }
}

@Composable
fun RowScope.AddBottomNavigationBarItem(
    selectedRoute: String,
    item: BottomNavigationItem,
    onNavigate: (String) -> Unit,
) {
    Spacer(Modifier.width(10.dp))
    Box(
        modifier = Modifier
            .height(50.dp)
            .clip(
                RoundedCornerShape(
                    topEnd = 10.dp,
                    topStart = 10.dp,
                    bottomEnd = 0.dp,
                    bottomStart = 0.dp
                )
            )
            .weight(if (selectedRoute == item.routeId) 1f else 0.5f)
            .background(if (selectedRoute == item.routeId) Color(0xFF65A6F1) else Color.Transparent)

    ) {
        Row(Modifier
                .align(Alignment.Center)
                .clickable {
                    onNavigate(item.routeId)
                }) {
                    val iconModifier = Modifier.size(25.dp)
                    if (selectedRoute != item.routeId)
                        iconModifier.alpha(0.5f)
                    Icon(
                        painter = painterResource(id = item.iconId),
                        contentDescription = null,
                        modifier = iconModifier,
                        tint = Color.White
                    )
                    if (selectedRoute == item.routeId)
                        Text(
                            text = item.name, fontSize = 12.sp, modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .padding(start = 5.dp),
                            color = Color.White
                        )
                }
    }
    Spacer(Modifier.width(10.dp))
}

fun getItems(): MutableList<BottomNavigationItem>{
    return mutableListOf<BottomNavigationItem>(
        BottomNavigationItem("home", "Home", R.drawable.baseline_home_24),
        BottomNavigationItem("analytics", "Analytics", R.drawable.baseline_analytics_24),
        BottomNavigationItem("search", "Search", R.drawable.baseline_search_24),
        BottomNavigationItem("profile", "Profile", R.drawable.baseline_person_24))
}