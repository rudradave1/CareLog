package com.rudra.carelog.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rudra.carelog.CareLogApp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.platform.LocalContext
import com.rudra.designsystem.theme.CareLogScaffold
import com.rudra.settings.ui.SettingsScreen
import com.rudra.settings.viewmodel.SettingsViewModel
import com.rudra.settings.viewmodel.SettingsViewModelFactory
import com.rudra.tasks.ui.AddTaskScreen
import com.rudra.tasks.ui.TaskListScreen
import com.rudra.tasks.viewmodel.AddTaskViewModel
import com.rudra.tasks.viewmodel.AddTaskViewModelFactory
import com.rudra.tasks.viewmodel.TaskListViewModel
import com.rudra.tasks.viewmodel.TaskListViewModelFactory

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    val context = LocalContext.current
    val appContainer =
        (context.applicationContext as CareLogApp).appContainer

    NavHost(
        navController = navController,
        startDestination = Routes.TASK_LIST,
        modifier = modifier
    ) {

        composable(Routes.TASK_LIST) {
            val vm: TaskListViewModel =
                viewModel(
                    factory = TaskListViewModelFactory(
                        appContainer.taskRepository
                    )
                )

            CareLogScaffold(
                title = "Tasks",
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = {
                            navController.navigate(Routes.ADD_TASK)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add Task"
                        )
                    }
                }
            ) {
                TaskListScreen(viewModel = vm)
            }
        }


        composable(Routes.ADD_TASK) {
            val vm: AddTaskViewModel =
                viewModel(
                    factory = AddTaskViewModelFactory(
                        appContainer.taskRepository
                    )
                )

            AddTaskScreen(
                viewModel = vm,
                onTaskSaved = {
                    navController.popBackStack()
                }
            )
        }

        composable(Routes.SETTINGS) {
            val vm: SettingsViewModel =
                viewModel(
                    factory = SettingsViewModelFactory(
                        appContainer.userPreferences
                    )
                )

            CareLogScaffold(
                title = "Settings"
            ) {
                SettingsScreen(viewModel = vm)
            }
        }
    }
}
