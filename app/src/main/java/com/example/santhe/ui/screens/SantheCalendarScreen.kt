package com.example.santhe.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.santhe.navigation.Screen
import com.example.santhe.ui.viewmodel.StallViewModel

@Composable
fun SantheCalendarScreen(navController: NavHostController, viewModel: StallViewModel) {
    val stalls by viewModel.allStalls.collectAsState(initial = emptyList())
    val santhes = stalls.filter { it.category == "Market" && it.dayOfWeek != null }
    val days = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Santhe Calendar", style = MaterialTheme.typography.headlineMedium, color = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            items(days) { day ->
                val marketsForDay = santhes.filter { it.dayOfWeek?.contains(day) == true }
                DaySection(day, marketsForDay) { stall ->
                    viewModel.selectStall(stall)
                    navController.navigate(Screen.StallDetail.createRoute(stall.id))
                }
            }
        }
    }
}

@Composable
fun DaySection(day: String, markets: List<com.example.santhe.data.local.entity.Stall>, onClick: (com.example.santhe.data.local.entity.Stall) -> Unit) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(day, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.secondary)
            if (markets.isEmpty()) {
                Text("No markets scheduled", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(top = 8.dp))
            } else {
                markets.forEach { stall ->
                    TextButton(onClick = { onClick(stall) }) {
                        Text("• ${stall.name} (${stall.specialtyTags})")
                    }
                }
            }
        }
    }
}
