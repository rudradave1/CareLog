package com.rudra.carelog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rudra.carelog.ui.theme.CareLogTheme
import com.rudra.tasks.ui.AddTaskScreen
import com.rudra.tasks.ui.TaskListScreen
import com.rudra.tasks.viewmodel.AddTaskViewModel
import com.rudra.tasks.viewmodel.AddTaskViewModelFactory
import com.rudra.tasks.viewmodel.TaskListViewModel
import com.rudra.tasks.viewmodel.TaskListViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        setContent {
            CareLogTheme {
                TaskList()
                //AddTask()
            }
        }
    }
}

@Composable
fun TaskList( ) {
    val context = LocalContext.current
    val appContainer =
        (context.applicationContext as CareLogApp)
            .appContainer

    val taskListViewModel: TaskListViewModel =
        viewModel(
            factory = TaskListViewModelFactory(
                appContainer.taskRepository
            )
        )

    TaskListScreen(taskListViewModel)


}


@Composable
fun AddTask( ) {
    val context = LocalContext.current

    val appContainer =
        (context.applicationContext as CareLogApp)
            .appContainer

    val addTaskViewModel: AddTaskViewModel =
        viewModel(
            factory = AddTaskViewModelFactory(
                appContainer.taskRepository
            )
        )

    AddTaskScreen(addTaskViewModel)
}
