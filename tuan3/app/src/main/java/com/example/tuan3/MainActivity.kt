package com.example.tuan3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.tuan3.ui.theme.Tuan3Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Tuan3Theme {
                val navController = rememberNavController()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = "welcome"
                    ) {
                        composable("welcome") {
                            WelcomeScreen(
                                onNextClick = { navController.navigate("ui_list") }
                            )
                        }
                        composable("ui_list") {
                            UIComponentsListScreen(
                                onItemClick = { title ->
                                    navController.navigate("detail/$title")
                                }
                            )
                        }
                        composable(
                            "detail/{title}",
                            arguments = listOf(navArgument("title") { type = NavType.StringType })
                        ) { backStackEntry ->
                            val title = backStackEntry.arguments?.getString("title") ?: ""
                            DetailScreen(title) { navController.popBackStack() }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun WelcomeScreen(onNextClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Jetpack Compose Logo",
            modifier = Modifier.size(150.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Jetpack Compose",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "Jetpack Compose is the modern toolkit for building native Android apps using a declarative approach.",
            fontSize = 16.sp,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(40.dp))
        Button(
            onClick = onNextClick,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF007BFF)),
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
        ) {
            Text(
                text = "I’m ready",
                color = Color.White,
                fontSize = 18.sp
            )
        }
    }
}

@Composable
fun UIComponentsListScreen(onItemClick: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "UI Components List",
            fontSize = 20.sp,
            color = Color(0xFF007BFF),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(16.dp))

        Section(title = "Display") {
            ComponentItem("Text", "Displays text", onItemClick)
            ComponentItem("Image", "Displays an image", onItemClick)
        }

        Section(title = "Input") {
            ComponentItem("TextField", "Input field for text", onItemClick)
            ComponentItem("PasswordField", "Input field for passwords", onItemClick)
        }

        Section(title = "Layout") {
            ComponentItem("Column", "Arranges elements vertically", onItemClick)
            ComponentItem("Row", "Arranges elements horizontally", onItemClick)
        }
    }
}

@Composable
fun Section(title: String, content: @Composable ColumnScope.() -> Unit) {
    Text(
        text = title,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(vertical = 8.dp)
    )
    Column(content = content)
}

@Composable
fun ComponentItem(title: String, description: String, onClick: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .background(Color(0xFFBDE0FE), shape = RoundedCornerShape(8.dp))
            .clickable { onClick(title) }
            .padding(12.dp)
    ) {
        Text(text = title, fontWeight = FontWeight.Bold)
        Text(text = description)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(title: String, onBackClick: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF007BFF),
                    titleContentColor = Color.White
                )
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            when (title) {
                // 🔹 TEXT DEMO
                "Text" -> Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("This is a Text component!", fontSize = 20.sp, color = Color(0xFF007BFF))
                    Spacer(modifier = Modifier.height(10.dp))
                    Text("You can style it freely 💫", fontWeight = FontWeight.Bold)
                }

                // 🔹 IMAGE DEMO
                "Image" -> Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = painterResource(id = R.drawable.truong),
                        contentDescription = "Sample Image",
                        modifier = Modifier.size(500.dp)
                    )
                    Text("This is an Image component!", fontSize = 20.sp, color = Color(0xFF007BFF))
                    Spacer(modifier = Modifier.height(20.dp))
                }

                // 🔹 TEXTFIELD DEMO
                "TextField" -> Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("nhập thông tin:", fontSize = 18.sp)
                    Spacer(modifier = Modifier.height(10.dp))
                    TextField(value = "", onValueChange = {}, placeholder = { Text("Type here...") })
                }

                // 🔹 PASSWORD FIELD DEMO
                "PasswordField" -> Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Enter your password:", fontSize = 18.sp)
                    Spacer(modifier = Modifier.height(10.dp))
                    TextField(
                        value = "",
                        onValueChange = {},
                        placeholder = { Text("••••••••") },
                        visualTransformation = PasswordVisualTransformation()
                    )
                }

                // 🔹 COLUMN DEMO
                "Column" -> Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Column arranges elements vertically ↓", fontSize = 18.sp, color = Color(0xFF007BFF))
                    Spacer(modifier = Modifier.height(16.dp))
                    Column(
                        modifier = Modifier
                            .background(Color(0xFFDFF6FF), RoundedCornerShape(8.dp))
                            .padding(16.dp)
                    ) {
                        Text("🍎 Apple")
                        Text("🍌 Banana")
                        Text("🍇 Grape")
                    }
                }

                // 🔹 ROW DEMO
                "Row" -> Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Row arranges elements horizontally →", fontSize = 18.sp, color = Color(0xFF007BFF))
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier
                            .background(Color(0xFFDFF6FF), RoundedCornerShape(8.dp))
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text("🍎")
                        Text("🍌")
                        Text("🍇")
                    }
                }

                // 🔹 DEFAULT CASE
                else -> Text(
                    text = "This is the $title screen!",
                    fontSize = 20.sp,
                    color = Color(0xFF007BFF),
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}
