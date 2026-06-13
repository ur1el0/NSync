package com.example.mobile.ui.screens.knowledge

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile.navigation.Routes
import com.example.mobile.ui.components.BottomNavBar
import com.example.mobile.ui.theme.InterFontFamily
import com.example.mobile.ui.theme.NSyncBlue
import com.example.mobile.ui.theme.NSyncCardWhite
import com.example.mobile.ui.theme.NSyncLightBackground
import com.example.mobile.ui.theme.NSyncMutedText
import com.example.mobile.ui.theme.ScreenBodyStyle
import com.example.mobile.ui.theme.ScreenButtonStyle
import com.example.mobile.ui.theme.ScreenCardBorder
import com.example.mobile.ui.theme.ScreenSectionStyle
import com.example.mobile.ui.theme.ScreenSmallBoldStyle

@Composable
fun NewNoteScreen(
    onBackClick: () -> Unit,
    onSaveClick: () -> Unit,
    onRouteClick: (String) -> Unit
) {
    var title by remember { mutableStateOf("Building a Simple Monthly Budget") }
    var collection by remember { mutableStateOf("Personal Finance") }
    var source by remember { mutableStateOf("Money management") }
    var context by remember { mutableStateOf("Training") }
    var content by remember {
        mutableStateOf("Capture reusable ideas, definitions, steps, or explanations here...")
    }

    Scaffold(
        containerColor = NSyncLightBackground,
        bottomBar = {
            BottomNavBar(
                currentRoute = Routes.KNOWLEDGE_BASE,
                onRouteClick = onRouteClick
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp, vertical = 18.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(42.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "←",
                        modifier = Modifier.clickable(onClick = onBackClick),
                        color = NSyncBlue,
                        style = NewNoteTopActionStyle
                    )
                    Text("New Note", color = NSyncBlue, style = ScreenSectionStyle)
                    Text(
                        text = "Save",
                        modifier = Modifier.clickable(onClick = onSaveClick),
                        color = NSyncBlue,
                        style = ScreenSmallBoldStyle
                    )
                }
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = NSyncCardWhite),
                    shape = RoundedCornerShape(10.dp),
                    border = ScreenCardBorder
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        NewNoteField(
                            value = title,
                            onValueChange = { title = it },
                            placeholder = "Note title"
                        )
                        NewNoteLabel("Collection")
                        NewNoteField(
                            value = collection,
                            onValueChange = { collection = it },
                            placeholder = "Collection"
                        )
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Column(modifier = Modifier.weight(1f)) {
                                NewNoteLabel("Source")
                                NewNoteField(
                                    value = source,
                                    onValueChange = { source = it },
                                    placeholder = "Source"
                                )
                            }
                            Column(modifier = Modifier.weight(1f)) {
                                NewNoteLabel("Context")
                                NewNoteField(
                                    value = context,
                                    onValueChange = { context = it },
                                    placeholder = "Context"
                                )
                            }
                        }

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(240.dp),
                            colors = CardDefaults.cardColors(containerColor = NSyncCardWhite),
                            shape = RoundedCornerShape(8.dp),
                            border = ScreenCardBorder
                        ) {
                            Column(
                                modifier = Modifier.padding(12.dp),
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text("B   I   ≡   ◉   ▣", color = Color.Black, style = ScreenSmallBoldStyle)
                                    Box(
                                        modifier = Modifier
                                            .background(Color(0xFFF0F3FA), RoundedCornerShape(6.dp))
                                            .padding(horizontal = 10.dp, vertical = 6.dp)
                                    ) {
                                        Text("Last saved 2m ago", color = NSyncMutedText, style = NewNoteTinyStyle)
                                    }
                                }

                                OutlinedTextField(
                                    value = content,
                                    onValueChange = { content = it },
                                    modifier = Modifier.fillMaxSize(),
                                    textStyle = NewNoteFieldStyle,
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = Color.Transparent,
                                        unfocusedBorderColor = Color.Transparent,
                                        focusedContainerColor = NSyncCardWhite,
                                        unfocusedContainerColor = NSyncCardWhite
                                    )
                                )
                            }
                        }
                    }
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    OutlinedButton(
                        onClick = onBackClick,
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp),
                        shape = RoundedCornerShape(999.dp)
                    ) {
                        Text("Cancel", color = NSyncBlue, style = ScreenButtonStyle)
                    }
                    Button(
                        onClick = onSaveClick,
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp),
                        shape = RoundedCornerShape(999.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = NSyncBlue)
                    ) {
                        Text("Save Note", color = Color.White, style = ScreenButtonStyle)
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(72.dp))
            }
        }
    }
}

@Composable
private fun NewNoteLabel(text: String) {
    Text(text, color = Color.Black, style = ScreenSmallBoldStyle)
}

@Composable
private fun NewNoteField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        placeholder = {
            Text(placeholder, color = Color.Black, style = NewNoteFieldStyle)
        },
        singleLine = true,
        textStyle = NewNoteFieldStyle,
        shape = RoundedCornerShape(8.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            focusedContainerColor = Color(0xFFF8F9FF),
            unfocusedContainerColor = Color(0xFFF8F9FF),
            cursorColor = NSyncBlue
        )
    )
}

private val NewNoteTopActionStyle = TextStyle(
    fontFamily = InterFontFamily,
    fontSize = 20.sp,
    lineHeight = 24.sp,
    fontWeight = FontWeight.Bold
)

private val NewNoteTinyStyle = TextStyle(
    fontFamily = InterFontFamily,
    fontSize = 9.sp,
    lineHeight = 12.sp,
    fontWeight = FontWeight.Bold
)

private val NewNoteFieldStyle = TextStyle(
    fontFamily = InterFontFamily,
    fontSize = 14.sp,
    lineHeight = 20.sp,
    fontWeight = FontWeight.Normal,
    color = Color.Black
)
