package com.example.santhe.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.santhe.data.local.entity.Review
import com.example.santhe.navigation.Screen
import com.example.santhe.ui.viewmodel.AuthViewModel
import com.example.santhe.ui.viewmodel.StallViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StallDetailScreen(
    navController: NavHostController,
    viewModel: StallViewModel,
    authViewModel: AuthViewModel,
    stallId: Int
) {
    val stalls by viewModel.allStalls.collectAsState(initial = emptyList())
    val stall = stalls.find { it.id == stallId }
    val reviews by viewModel.getReviewsForStall(stallId).collectAsState(initial = emptyList())
    val currentUser by authViewModel.currentUser.collectAsState()

    var reviewComment by remember { mutableStateOf("") }
    var reviewRating by remember { mutableIntStateOf(5) }

    if (stall == null) {
        Box(modifier = Modifier.fillMaxSize()) {
            Text("Stall not found", modifier = Modifier.align(androidx.compose.ui.Alignment.Center))
        }
        return
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stall.name) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        // Navigate to map and focus on this stall (implied by logic in MapScreen)
                        navController.navigate(Screen.Map.route)
                    }) {
                        Icon(Icons.Default.LocationOn, contentDescription = "Show on Map")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding).padding(16.dp)) {
            item {
                if (stall.imageUrl != null) {
                    AsyncImage(
                        model = stall.imageUrl,
                        contentDescription = stall.name,
                        modifier = Modifier.fillMaxWidth().height(200.dp),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
                Text(stall.category, style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.secondary)
                Text(stall.description, style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(8.dp))
                
                Button(
                    onClick = { navController.navigate(Screen.Map.route) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.LocationOn, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Show on Map")
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                Text("Specialties:", style = MaterialTheme.typography.titleMedium)
                Text(stall.specialtyTags, style = MaterialTheme.typography.bodyMedium)
                if (stall.dayOfWeek != null) {
                    Text("Days: ${stall.dayOfWeek}", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.primary)
                }
                Spacer(modifier = Modifier.height(24.dp))
                
                // Add Review Section
                Text("Add a Review", style = MaterialTheme.typography.titleLarge)
                Card(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                            Text("Rating: ")
                            (1..5).forEach { rating ->
                                FilterChip(
                                    selected = reviewRating == rating,
                                    onClick = { reviewRating = rating },
                                    label = { Text(rating.toString()) },
                                    modifier = Modifier.padding(horizontal = 2.dp)
                                )
                            }
                        }
                        OutlinedTextField(
                            value = reviewComment,
                            onValueChange = { reviewComment = it },
                            label = { Text("Comment") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Button(
                            onClick = {
                                currentUser?.let { user ->
                                    viewModel.addReview(
                                        Review(
                                            stallId = stallId,
                                            userId = user.id,
                                            rating = reviewRating,
                                            comment = reviewComment
                                        )
                                    )
                                    reviewComment = ""
                                    reviewRating = 5
                                }
                            },
                            enabled = reviewComment.isNotBlank() && currentUser != null,
                            modifier = Modifier.align(androidx.compose.ui.Alignment.End).padding(top = 8.dp)
                        ) {
                            Text("Submit Review")
                        }
                        if (currentUser == null) {
                            Text("Log in to leave a review", color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.labelSmall)
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                Text("Reviews", style = MaterialTheme.typography.headlineSmall)
                Divider()
                Spacer(modifier = Modifier.height(8.dp))
            }

            if (reviews.isEmpty()) {
                item {
                    Text("No reviews yet. Be the first to leave one!", style = MaterialTheme.typography.bodyMedium)
                }
            } else {
                items(reviews) { review ->
                    ReviewItem(review)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
fun ReviewItem(review: com.example.santhe.data.local.entity.Review) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                Text("Rating: ${review.rating}/5", fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                Text(java.text.SimpleDateFormat("MMM dd, yyyy").format(java.util.Date(review.timestamp)), style = MaterialTheme.typography.labelSmall)
            }
            Text(review.comment, style = MaterialTheme.typography.bodyMedium)
        }
    }
}
