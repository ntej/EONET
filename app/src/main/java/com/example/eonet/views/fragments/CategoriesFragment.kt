package com.example.eonet.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.solver.state.Dimension
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.eonet.EonetApplication
import com.example.eonet.retrofit.model.Category
import com.example.eonet.ui.theme.EONETTheme
import com.example.eonet.viewmodel.CategoriesViewModel
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
        // State to hold the list of categories
        val categories by vm.categories.observeAsState(emptyList())
        var loading by remember { mutableStateOf(true) }

        // Check if data is loaded and update loading state
        LaunchedEffect(categories) {
            loading = categories.isEmpty()
        }

        // Display each category
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

    @Composable
    fun IndeterminateProgressIndicator(loading: Boolean) {
        if(loading) {
            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth().height(6.dp),
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
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 10.dp
                ),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
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