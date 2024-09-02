package com.example.eonet.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.eonet.EonetApplication
import com.example.eonet.entities.Category
import com.example.eonet.ui.theme.EONETTheme
import com.example.eonet.view.viewmodel.CategoriesViewModel
import javax.inject.Inject

class CategoriesFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val vm: CategoriesViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().application as EonetApplication).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                EONETTheme {
                    categoriesList()
                }
            }
        }
    }

    @Composable
    fun categoriesList() {
        // Observe errors from ViewModel
        val errorMessage by vm.errorState.observeAsState(null)

        // State to hold the list of categories
        val categories by vm.categories.observeAsState(emptyList())

        //State to hold progressIndicatorLoading
        val loading by vm.progressIndicatorLoading.observeAsState(false)

        //Display error message if present
        errorMessage?.let {
            errorScreen(it)
        } ?: run {
            // Display the  categories list if no error
            Column {
                IndeterminateProgressIndicator(loading)
                LazyColumn {
                    items(categories) { category ->
                        categoryCard(category = category, onClick = {
                            val action = CategoriesFragmentDirections.viewCategoryData(category.id)
                            findNavController().navigate(action)
                        })
                    }
                }
            }
        }
    }

    @Preview
    @Composable
    fun PreviewErrorScreen() {
        errorScreen(msg = "Network error. Please check your internet connection. Network error.")
    }

    @Composable
    private fun errorScreen(msg: String) {

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = msg,
                color = Color.Black,
                modifier = Modifier.padding(16.dp),
                textAlign = TextAlign.Center
            )
            Button(onClick = {
                vm.retry()
            }) {
                Text(text = "Retry")
            }
        }
    }

    @Composable
    fun IndeterminateProgressIndicator(loading: Boolean) {
        if (loading) {
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp),
                color = MaterialTheme.colorScheme.secondary,
                trackColor = MaterialTheme.colorScheme.surfaceTint,
            )
        }
    }

    @Composable
    fun categoryCard(category: Category, onClick: (String) -> Unit) {
        Column(
            modifier = Modifier
                .padding(all = 8.dp)
                .clickable { onClick(category.id) }
        )
        {
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 6.dp
                )
            ) {
                Text(
                    text = category.title,
                    modifier = Modifier.padding(all = 10.dp),
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Black
                )
                //Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = category.description,
                    modifier = Modifier.padding(all = 10.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.DarkGray

                )
            }
        }
    }
}