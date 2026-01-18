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
import com.rudra.carelog.data.RepositoryProvider
import com.rudra.carelog.ui.theme.CareLogTheme
import com.rudra.tasks.ui.TaskListScreen
import com.rudra.tasks.viewmodel.TaskListViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        setContent {
            CareLogTheme {
                Greeting()
            }
        }
    }
}

@Composable
fun Greeting( ) {
    val context = LocalContext.current

    val repository =
        RepositoryProvider.provideTaskRepository(context)

    val viewModel =
        remember {
            TaskListViewModel(repository)
        }

    TaskListScreen(viewModel)

}
