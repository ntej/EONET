package com.example.eonet.views

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import com.example.eonet.EonetApplication
import com.example.eonet.utils.observe
import com.example.eonet.ui.theme.EONETTheme
import com.example.eonet.viewmodel.HomeViewModel
import javax.inject.Inject

class HomeActivity : ComponentActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val vm: HomeViewModel by viewModels { viewModelFactory }
    override fun onCreate(savedInstanceState: Bundle?) {
        (application as EonetApplication).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContent {
            EONETTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }

        vm.error.observe(this){
            //TODO Show some Error Screen with retry
        }
        vm.eoCategories.observe(this){
            //TODO populate the Categories
            Log.i(javaClass.simpleName, "Categories: $it")
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    EONETTheme {
        Greeting("Android")
    }
}