package com.example.santhe.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.example.santhe.navigation.Screen
import com.example.santhe.ui.viewmodel.StallViewModel
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

@Composable
fun MapScreen(navController: NavHostController, viewModel: StallViewModel) {
    val stalls by viewModel.allStalls.collectAsState(initial = emptyList())
    val categories = listOf("All", "Food", "Market", "Craft")
    var selectedCategory by remember { mutableStateOf("All") }
    
    val context = LocalContext.current
    val mapView = remember { MapView(context) }
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> mapView.onResume()
                Lifecycle.Event.ON_PAUSE -> mapView.onPause()
                else -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    val filteredStalls = if (selectedCategory == "All") {
        stalls
    } else {
        stalls.filter { it.category == selectedCategory }
    }

    val bengaluru = GeoPoint(12.9716, 77.5946)
    val selectedStall by viewModel.selectedStall.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate(Screen.AddStall.route) }) {
                Icon(Icons.Default.Add, contentDescription = "Add Location")
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            CategoryFilterBar(categories, selectedCategory) { selectedCategory = it }
            
            AndroidView(
                factory = { 
                    mapView.apply {
                        setTileSource(TileSourceFactory.MAPNIK)
                        setMultiTouchControls(true)
                        controller.setZoom(12.0)
                        
                        val centerPoint = selectedStall?.let { GeoPoint(it.latitude, it.longitude) } ?: bengaluru
                        controller.setCenter(centerPoint)
                        if (selectedStall != null) controller.setZoom(15.0)
                    }
                },
                modifier = Modifier.fillMaxSize(),
                update = { view ->
                    view.overlays.clear()
                    filteredStalls.forEach { stall ->
                        val marker = Marker(view)
                        marker.position = GeoPoint(stall.latitude, stall.longitude)
                        marker.title = stall.name
                        marker.subDescription = stall.specialtyTags
                        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                        marker.setOnMarkerClickListener { _, _ ->
                            viewModel.selectStall(stall)
                            navController.navigate(Screen.StallDetail.createRoute(stall.id))
                            true
                        }
                        view.overlays.add(marker)
                    }
                    view.invalidate()
                }
            )
        }
    }
}

@Composable
fun CategoryFilterBar(categories: List<String>, selected: String, onSelect: (String) -> Unit) {
    LazyRow(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(categories) { category ->
            FilterChip(
                selected = selected == category,
                onClick = { onSelect(category) },
                label = { Text(category) }
            )
        }
    }
}
