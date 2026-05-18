package com.example.santhe.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object Map : Screen("map")
    object SantheCalendar : Screen("santhe_calendar")
    object ReviewWall : Screen("review_wall")
    object Profile : Screen("profile")
    object StallDetail : Screen("stall_detail/{stallId}") {
        fun createRoute(stallId: Int) = "stall_detail/$stallId"
    }
    object AddStall : Screen("add_stall")
}
