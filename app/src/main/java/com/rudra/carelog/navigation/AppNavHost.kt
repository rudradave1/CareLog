package com.rudra.carelog.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rudra.carelog.CareLogApp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.platform.LocalContext
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

            TaskListScreen(
                viewModel = vm,
                onAddClick = {
                    navController.navigate(Routes.ADD_TASK)
                }
            )
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
    }
}
