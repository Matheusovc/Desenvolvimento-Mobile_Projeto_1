package com.fieldservice.app.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.fieldservice.app.presentation.home.HomeScreen
import com.fieldservice.app.presentation.login.LoginScreen
import com.fieldservice.app.presentation.profile.ProfileScreen
import com.fieldservice.app.presentation.ticketdetails.TicketDetailsScreen
import com.fieldservice.app.presentation.tickets.TicketsScreen

private data class BottomNavItem(val route: String, val label: String, val icon: ImageVector)

private val bottomNavItems = listOf(
    BottomNavItem(Routes.HOME, "Início", Icons.Filled.Home),
    BottomNavItem(Routes.TICKETS, "Chamados", Icons.AutoMirrored.Filled.List),
    BottomNavItem(Routes.PROFILE, "Perfil", Icons.Filled.Person)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FieldServiceApp() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route
    val isTicketDetails = currentRoute == Routes.TICKET_DETAILS
    val showBottomBar = bottomNavItems.any { it.route == currentRoute }

    Scaffold(
        topBar = {
            when {
                currentRoute == Routes.HOME -> TopAppBar(title = { Text("FieldService") })
                currentRoute == Routes.TICKETS -> TopAppBar(title = { Text("Chamados") })
                currentRoute == Routes.PROFILE -> TopAppBar(title = { Text("Perfil") })
                isTicketDetails -> TopAppBar(
                    title = { Text("Detalhes do chamado") },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                        }
                    }
                )
            }
        },
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    bottomNavItems.forEach { item ->
                        NavigationBarItem(
                            selected = currentRoute == item.route,
                            onClick = {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = { Icon(item.icon, contentDescription = item.label) },
                            label = { Text(item.label) }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Routes.LOGIN,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            composable(Routes.LOGIN) {
                LoginScreen(
                    onLoginSuccess = {
                        navController.navigate(Routes.HOME) {
                            popUpTo(Routes.LOGIN) { inclusive = true }
                        }
                    }
                )
            }
            composable(Routes.HOME) {
                HomeScreen(onTicketClick = { id -> navController.navigate(Routes.ticketDetails(id)) })
            }
            composable(Routes.TICKETS) {
                TicketsScreen(onTicketClick = { id -> navController.navigate(Routes.ticketDetails(id)) })
            }
            composable(
                route = Routes.TICKET_DETAILS,
                arguments = listOf(navArgument(Routes.TICKET_ID_ARG) { type = NavType.StringType })
            ) { entry ->
                val ticketId = entry.arguments?.getString(Routes.TICKET_ID_ARG).orEmpty()
                TicketDetailsScreen(ticketId = ticketId)
            }
            composable(Routes.PROFILE) {
                ProfileScreen(
                    onLogout = {
                        navController.navigate(Routes.LOGIN) {
                            popUpTo(0)
                        }
                    }
                )
            }
        }
    }
}
