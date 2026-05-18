package com.example.santhe.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.Alignment
import androidx.compose.foundation.border
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.example.santhe.data.local.entity.Stall
import com.example.santhe.ui.viewmodel.StallViewModel
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddStallScreen(navController: NavHostController, viewModel: StallViewModel) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Food") }
    var specialtyTags by remember { mutableStateOf("") }
    var selectedDays by remember { mutableStateOf(setOf<String>()) }
    
    // Default to Bengaluru
    var selectedLocation by remember { mutableStateOf(GeoPoint(12.9716, 77.5946)) }
    
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    
    val categories = listOf("Food", "Market", "Craft")
    val days = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add New Location") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Name") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(value = description, onValueChange = { description = it }, label = { Text("Description") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))
            
            Text("Category", style = MaterialTheme.typography.titleMedium)
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                categories.forEach { cat ->
                    FilterChip(
                        selected = category == cat,
                        onClick = { category = cat },
                        label = { Text(cat) }
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            Text("Select Location (Drag map to center pointer)", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .clip(RectangleShape)
                    .border(1.dp, MaterialTheme.colorScheme.outline, RectangleShape)
            ) {
                AndroidView(
                    factory = { ctx ->
                        MapView(ctx).apply {
                            setMultiTouchControls(true)
                            controller.setZoom(15.0)
                            controller.setCenter(selectedLocation)
                            
                            addMapListener(object : org.osmdroid.events.MapListener {
                                override fun onScroll(event: org.osmdroid.events.ScrollEvent?): Boolean {
                                    selectedLocation = mapCenter as GeoPoint
                                    return true
                                }
                                override fun onZoom(event: org.osmdroid.events.ZoomEvent?): Boolean {
                                    selectedLocation = mapCenter as GeoPoint
                                    return true
                                }
                            })
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                )
                // Center Pointer (Crosshair)
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Picker",
                    modifier = Modifier.size(40.dp).align(Alignment.Center),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text("Lat: ${"%.4f".format(selectedLocation.latitude)}, Lng: ${"%.4f".format(selectedLocation.longitude)}", style = MaterialTheme.typography.labelSmall)
            
            Spacer(modifier = Modifier.height(16.dp))
            
            OutlinedTextField(value = specialtyTags, onValueChange = { specialtyTags = it }, label = { Text("Specialty Tags (comma separated)") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))
            
            if (category == "Market") {
                Text("Market Days", style = MaterialTheme.typography.titleMedium)
                days.forEach { day ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = selectedDays.contains(day),
                            onCheckedChange = { checked ->
                                if (checked) selectedDays = selectedDays + day
                                else selectedDays = selectedDays - day
                            }
                        )
                        Text(day, modifier = Modifier.padding(start = 8.dp))
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Button(
                onClick = {
                    val newStall = Stall(
                        name = name,
                        description = description,
                        category = category,
                        latitude = selectedLocation.latitude,
                        longitude = selectedLocation.longitude,
                        dayOfWeek = if (category == "Market") selectedDays.joinToString(", ") else null,
                        specialtyTags = specialtyTags
                    )
                    viewModel.addStall(newStall)
                    navController.navigateUp()
                },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                enabled = name.isNotBlank() && description.isNotBlank() && (category != "Market" || selectedDays.isNotEmpty())
            ) {
                Text("Save Location")
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
