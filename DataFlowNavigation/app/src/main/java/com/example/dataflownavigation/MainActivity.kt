package com.example.dataflownavigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.dataflownavigation.ui.screens.*
import com.example.dataflownavigation.ui.theme.DataFlowNavigationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DataFlowNavigationTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "forgot") {

                    // 1️⃣ ForgotPasswordScreen → gửi email sang Verify
                    composable("forgot") {
                        ForgotPasswordScreen(
                            onNextClick = { email ->
                                navController.navigate("verify/$email")
                            }
                        )
                    }

                    // 2️⃣ VerifyCodeScreen → nhận email, gửi email + verifyCode sang Reset
                    composable(
                        route = "verify/{email}",
                        arguments = listOf(navArgument("email") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val email = backStackEntry.arguments?.getString("email") ?: ""
                        VerifyCodeScreen(
                            email = email,
                            onNextClick = { verifyCode ->
                                navController.navigate("reset/$email/$verifyCode")
                            },
                            onBackClick = { navController.popBackStack() }
                        )
                    }

                    // 3️⃣ ResetPasswordScreen → nhận email + verifyCode, gửi thêm password sang Confirm
                    composable(
                        route = "reset/{email}/{verifyCode}",
                        arguments = listOf(
                            navArgument("email") { type = NavType.StringType },
                            navArgument("verifyCode") { type = NavType.StringType }
                        )
                    ) { backStackEntry ->
                        val email = backStackEntry.arguments?.getString("email") ?: ""
                        val verifyCode = backStackEntry.arguments?.getString("verifyCode") ?: ""
                        ResetPasswordScreen(
                            email = email,
                            verifyCode = verifyCode,
                            onNextClick = { password ->
                                navController.navigate("confirm/$email/$verifyCode/$password")
                            },
                            onBackClick = { navController.popBackStack() }
                        )
                    }

                    // 4️⃣ ConfirmScreen → nhận đủ email + verifyCode + password
                    composable(
                        route = "confirm/{email}/{verifyCode}/{password}",
                        arguments = listOf(
                            navArgument("email") { type = NavType.StringType },
                            navArgument("verifyCode") { type = NavType.StringType },
                            navArgument("password") { type = NavType.StringType }
                        )
                    ) { backStackEntry ->
                        val email = backStackEntry.arguments?.getString("email") ?: ""
                        val verifyCode = backStackEntry.arguments?.getString("verifyCode") ?: ""
                        val password = backStackEntry.arguments?.getString("password") ?: ""

                        // 👇 Giữ nguyên ConfirmScreen layout của bạn
                        ConfirmScreen(
                            email = email,
                            verifyCode = verifyCode,
                            password = password,
                            onBackClick = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}
