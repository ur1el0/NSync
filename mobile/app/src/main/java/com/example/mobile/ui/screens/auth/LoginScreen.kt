package com.example.mobile.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.clickable
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile.R
import com.example.mobile.ui.components.AuthTextField
import com.example.mobile.ui.theme.InterFontFamily
import com.example.mobile.ui.theme.NSyncBlue
import com.example.mobile.ui.theme.NSyncCardWhite

@Composable
fun LoginScreen(
    onLoginClick: (String, String) -> Unit,
    onRegisterClick: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LoginBackground)
            .padding(horizontal = 20.dp, vertical = 46.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(0.dp))

        AppLogo()

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "NSync",
            color = NSyncBlue,
            fontWeight = FontWeight.ExtraBold,
            style = TextStyle(
                fontFamily = InterFontFamily,
                fontSize = 29.sp,
                lineHeight = 34.sp,
                letterSpacing = 0.sp
            )
        )

        Text(
            text = "Capture knowledge. Review smarter. Level up.",
            modifier = Modifier.padding(top = 10.dp),
            color = LoginBody,
            style = TextStyle(
                fontFamily = InterFontFamily,
                fontSize = 13.sp,
                lineHeight = 18.sp,
                fontWeight = FontWeight.Normal,
                letterSpacing = 0.sp
            ),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(28.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = NSyncCardWhite),
            shape = RoundedCornerShape(26.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 28.dp, vertical = 28.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "Email Address",
                    color = LoginBody,
                    style = LoginLabelStyle
                )

                AuthTextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = "name@example.com",
                    leadingIcon = R.drawable.ic_mail_outline
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Password",
                    color = LoginBody,
                    style = LoginLabelStyle
                )

                AuthTextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = "••••••••",
                    leadingIcon = R.drawable.ic_lock_outline,
                    isPassword = true
                )

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = { onLoginClick(email, password) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .shadow(15.dp, RoundedCornerShape(22.dp)),
                    shape = RoundedCornerShape(999.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = NSyncBlue)
                ) {
                    Text(
                        text = "Login",
                        style = TextStyle(
                            fontFamily = InterFontFamily,
                            color = Color.White,
                            fontSize = 20.sp,
                            lineHeight = 18.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.sp
                        )
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "New to NSync?",
            color = LoginBody,
            style = LoginFooterStyle,
        )

        Text(
            text = "Create account",
            modifier = Modifier
                .padding(top = 8.dp)
                .clickable(onClick = onRegisterClick),
            color = NSyncBlue,
            style = LoginCreateAccountStyle
        )
    }
}

@Composable
private fun AppLogo() {
    Box(
        modifier = Modifier
            .size(84.dp)
            .offset(y = 4.dp)
            .shadow(12.dp, RoundedCornerShape(24.dp))
            .clip(RoundedCornerShape(24.dp))
            .background(NSyncCardWhite)
            .border(2.dp, NSyncBlue, RoundedCornerShape(24.dp)),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.nsync_logo),
            contentDescription = "NSync logo",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Fit
        )
    }
}

private val LoginBackground = Color(0xFFEFF4FF)
private val LoginBody = Color(0xFF424858)

private val LoginLabelStyle = TextStyle(
    fontFamily = InterFontFamily,
    fontSize = 18.sp,
    lineHeight = 15.sp,
    fontWeight = FontWeight.Bold,
    letterSpacing = 0.2.sp
)

private val LoginFooterStyle = TextStyle(
    fontFamily = InterFontFamily,
    fontSize = 15.sp,
    lineHeight = 11.sp,
    fontWeight = FontWeight.Bold,
    letterSpacing = 0.sp
)

private val LoginCreateAccountStyle = TextStyle(
    fontFamily = InterFontFamily,
    fontSize = 15.sp,
    lineHeight = 11.sp,
    fontWeight = FontWeight.Normal,
    letterSpacing = 0.sp
)
