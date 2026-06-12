package com.example.mobile.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile.ui.components.AuthTextField
import com.example.mobile.ui.theme.NSyncBlue
import com.example.mobile.ui.theme.NSyncLightBackground
import com.example.mobile.ui.theme.NSyncMutedText

@Composable
fun RegisterScreen(
    onRegisterClick: (String, String, String) -> Unit,
    onLoginClick: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(NSyncLightBackground)
            .padding(horizontal = 24.dp, vertical = 36.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(28.dp))

        Text(
            text = "NSync",
            color = NSyncBlue,
            style = MaterialTheme.typography.bodyLarge,
            fontSize =  35.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(42.dp))

        Text(
            text = "Create Account",
            modifier = Modifier.fillMaxWidth()
                .padding(top = 10.dp, bottom = 10.dp),
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Start building your personal knowledge base.",
            modifier = Modifier.fillMaxWidth(),
            color = NSyncMutedText,
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(30.dp))

        RegisterLabel("Full Name")
        AuthTextField(
            value = name,
            onValueChange = { name = it },
            placeholder = "Enter your name"
        )

        Spacer(modifier = Modifier.height(14.dp))

        RegisterLabel("Email Address")
        AuthTextField(
            value = email,
            onValueChange = { email = it },
            placeholder = "name@example.com"
        )

        Spacer(modifier = Modifier.height(14.dp))

        RegisterLabel("Password")
        AuthTextField(
            value = password,
            onValueChange = { password = it },
            placeholder = "Password",
            isPassword = true
        )

        Spacer(modifier = Modifier.height(28.dp))

        Button(
            onClick = { onRegisterClick(name, email, password) },
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            shape = RoundedCornerShape(999.dp),
            colors = ButtonDefaults.buttonColors(containerColor = NSyncBlue)
        ) {
            Text(
                text = "Create Account  →",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(28.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Already have an account?",
                color = NSyncMutedText,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center
            )

            TextButton(onClick = onLoginClick) {
                Text(
                    text = "Login",
                    color = NSyncBlue,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun RegisterLabel(text: String) {
    Text(
        text = text,
        modifier = Modifier.fillMaxWidth(),
        color = NSyncMutedText,
        style = MaterialTheme.typography.labelMedium,
        fontWeight = FontWeight.SemiBold
    )
}
