package com.example.santhe.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.santhe.ui.viewmodel.StallViewModel

@Composable
fun ReviewWallScreen(navController: NavHostController, viewModel: StallViewModel) {
    val reviews by viewModel.allReviews.collectAsState(initial = emptyList())
    val stalls by viewModel.allStalls.collectAsState(initial = emptyList())

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Traveler Review Wall", style = MaterialTheme.typography.headlineMedium, color = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.height(16.dp))

        if (reviews.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize()) {
                Text("No reviews yet. Start exploring!", modifier = Modifier.align(androidx.compose.ui.Alignment.Center))
            }
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(reviews) { review ->
                    val stall = stalls.find { it.id == review.stallId }
                    WallReviewItem(review, stall?.name ?: "Unknown Stall")
                }
            }
        }
    }
}

@Composable
fun WallReviewItem(review: com.example.santhe.data.local.entity.Review, stallName: String) {
    Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(stallName, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(4.dp))
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                Text("Rating: ${review.rating}/5", style = MaterialTheme.typography.labelLarge)
                Text(java.text.SimpleDateFormat("MMM dd, yyyy").format(java.util.Date(review.timestamp)), style = MaterialTheme.typography.labelSmall)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(review.comment, style = MaterialTheme.typography.bodyLarge)
        }
    }
}
