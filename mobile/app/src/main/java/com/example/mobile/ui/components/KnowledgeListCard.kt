package com.example.mobile.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mobile.data.KnowledgeItem
import com.example.mobile.ui.theme.NSyncBlue
import com.example.mobile.ui.theme.NSyncCardWhite
import com.example.mobile.ui.theme.NSyncMutedText
import com.example.mobile.ui.theme.ScreenBodyStyle
import com.example.mobile.ui.theme.ScreenCardBorder
import com.example.mobile.ui.theme.ScreenSectionStyle
import com.example.mobile.ui.theme.ScreenSmallBoldStyle
import com.example.mobile.ui.theme.ScreenSmallStyle
import com.example.mobile.ui.theme.ScreenTitle

@Composable
fun KnowledgeListCard(
    item: KnowledgeItem,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = NSyncCardWhite),
        shape = RoundedCornerShape(10.dp),
        border = ScreenCardBorder
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(item.collection, color = NSyncBlue, style = ScreenSmallBoldStyle)
            Text(item.title, color = ScreenTitle, style = ScreenSectionStyle)
            Text(item.source, color = NSyncMutedText, style = ScreenBodyStyle)
            Text(
                text = "${item.reviewCardCount} review cards • ${item.updatedLabel}",
                color = NSyncMutedText,
                style = ScreenSmallStyle
            )
        }
    }
}
