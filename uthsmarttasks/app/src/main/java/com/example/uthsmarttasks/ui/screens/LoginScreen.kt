package com.example.uthsmarttasks.ui.screens

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.uthsmarttasks.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.launch
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource

@Composable
fun LoginScreen(navController: NavController) {
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()
    val scope = rememberCoroutineScope()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            scope.launch {
                auth.signInWithCredential(credential)
                    .addOnSuccessListener {
                        navController.navigate("profile") {
                            popUpTo("login") { inclusive = true }
                        }
                    }
                    .addOnFailureListener { e ->
                        Log.e("Login", "Sign-in failed: ${e.message}")
                    }
            }
        } catch (e: Exception) {
            Log.e("Login", "Google Sign-in error", e)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.img1),
            contentDescription = "App Logo",
            modifier = Modifier
                .size(280.dp)
                .padding(bottom = 24.dp)
        )

        Text("UTH SmartTasks", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(24.dp))
        Text("Welcome! Please sign in using your Google account.")
        Spacer(modifier = Modifier.height(40.dp))
        Button(
            onClick = {
                val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(context.getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build()
                val googleSignInClient = GoogleSignIn.getClient(context, gso)
                launcher.launch(googleSignInClient.signInIntent)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Sign in with Google")
        }
    }
}
