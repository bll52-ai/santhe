package com.example.santhe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.santhe.data.local.AppDatabase
import com.example.santhe.data.repository.AuthRepository
import com.example.santhe.data.repository.StallRepository
import com.example.santhe.navigation.Screen
import com.example.santhe.ui.screens.*
import com.example.santhe.ui.theme.SantheTheme
import com.example.santhe.ui.viewmodel.AuthViewModel
import com.example.santhe.ui.viewmodel.StallViewModel
import com.example.santhe.ui.viewmodel.ViewModelFactory
import com.example.santhe.util.DataInitializer
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val db by lazy { AppDatabase.getDatabase(this) }
    private val authRepository by lazy { AuthRepository(db.userDao()) }
    private val stallRepository by lazy { StallRepository(db.stallDao(), db.reviewDao()) }
    private val authPrefs by lazy { getSharedPreferences("auth_prefs", MODE_PRIVATE) }

    private val authViewModel: AuthViewModel by viewModels { 
        ViewModelFactory(authRepository = authRepository, prefs = authPrefs) 
    }
    private val stallViewModel: StallViewModel by viewModels { 
        ViewModelFactory(stallRepository = stallRepository) 
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Initialize OSMdroid configuration
        org.osmdroid.config.Configuration.getInstance().load(
            this,
            getSharedPreferences("osmdroid", MODE_PRIVATE)
        )
        org.osmdroid.config.Configuration.getInstance().userAgentValue = packageName
        
        lifecycleScope.launch {
            val stalls = stallRepository.allStalls.first()
            val hasBengaluruData = authPrefs.getBoolean("has_bengaluru_data_v1", false)
            
            if (stalls.isEmpty() || !hasBengaluruData) {
                // Clear old small dataset if it exists to prevent duplicates
                if (stalls.size < 10) {
                    stallRepository.clearStalls()
                }
                DataInitializer.populateData(stallRepository)
                authPrefs.edit().putBoolean("has_bengaluru_data_v1", true).apply()
            }
        }

        setContent {
            SantheTheme {
                val navController = rememberNavController()
                val currentUser by authViewModel.currentUser.collectAsState()

                Scaffold(
                    bottomBar = {
                        if (currentUser != null) {
                            BottomNavigationBar(navController)
                        }
                    }
                ) { innerPadding ->
                    NavHostContainer(
                        navController = navController,
                        authViewModel = authViewModel,
                        stallViewModel = stallViewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun NavHostContainer(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    stallViewModel: StallViewModel,
    modifier: Modifier = Modifier
) {
    val currentUser by authViewModel.currentUser.collectAsState()
    val isSessionLoading by authViewModel.isSessionLoading.collectAsState()

    if (isSessionLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    val startDestination = if (currentUser == null) Screen.Login.route else Screen.Map.route

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(Screen.Login.route) { LoginScreen(navController, authViewModel) }
        composable(Screen.Register.route) { RegisterScreen(navController, authViewModel) }
        composable(Screen.Map.route) { MapScreen(navController, stallViewModel) }
        composable(Screen.SantheCalendar.route) { SantheCalendarScreen(navController, stallViewModel) }
        composable(Screen.ReviewWall.route) { ReviewWallScreen(navController, stallViewModel) }
        composable(Screen.Profile.route) { ProfileScreen(navController, authViewModel) }
        composable(Screen.AddStall.route) { AddStallScreen(navController, stallViewModel) }
        composable(Screen.StallDetail.route) { backStackEntry ->
            val stallId = backStackEntry.arguments?.getString("stallId")?.toIntOrNull()
            if (stallId != null) {
                StallDetailScreen(navController, stallViewModel, authViewModel, stallId)
            }
        }
    }
}
