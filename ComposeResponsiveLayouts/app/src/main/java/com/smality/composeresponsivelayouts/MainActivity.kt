package com.smality.composeresponsivelayouts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import com.smality.composeresponsivelayouts.ui.theme.ComposeResponsiveLayoutsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeResponsiveLayoutsTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val cardData = remember { generateCards() }
                    BoxWithConstraints {
                        AdaptiveLayoutCardList(cardData)
                    }
                }
            }
        }
    }
}
//maxWidth ve maxHeight kontrolu ile yatay/dikey moda göre card tanımlama
@Composable
fun BoxWithConstraintsScope.AdaptiveLayoutCardList(cardData: List<Pair<String, String>>) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        contentPadding = PaddingValues(24.dp)
    ) {
        items(cardData) { card ->
            //landscape mode (Yatay mod)
            if (maxWidth > maxHeight) {
                //4 tane card gösterimi
                val cardWidth = maxWidth / 4
                MyCard(
                    title = card.first,
                    subtitle = card.second,
                    height = maxHeight / 3,
                    width = cardWidth - cardWidth * 0.15f
                )
            } else {
                //portrait mode (dikey mod)
                val cardWidth = maxWidth / 2
                MyCard(
                    title = card.first,
                    subtitle = card.second,
                    height = maxHeight / 4,
                    width = cardWidth - cardWidth * 0.2f
                )
            }
        }
    }
}
//Card oluşturma
@Composable
fun MyCard(
    title: String,
    subtitle: String,
    height: Dp,
    width: Dp
) {
    Card(modifier = Modifier.height(height).width(width), shape = RoundedCornerShape(12.dp),
    ) {
        Column(
            modifier = Modifier.padding(25.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            //Card view içine başlık ve kategori Text'lerini tanımlıyoruz
            Text(text = title, fontSize = 30.sp, fontWeight = FontWeight.Bold)
            Text(text =subtitle, fontSize = 23.sp)
        }
    }
}

//20 elemanlı card içeriğinin belirtildiği dizi oluşturma
private fun generateCards(): List<Pair<String, String>> {
    return MutableList(20) { index ->
        val cardNumber = index + 1
        "Title $cardNumber" to "Subtitle $cardNumber"
    }
}
