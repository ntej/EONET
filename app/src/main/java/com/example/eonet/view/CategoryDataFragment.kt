package com.example.eonet.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.eonet.EonetApplication
import com.example.eonet.entities.Event
import com.example.eonet.ui.theme.EONETTheme
import com.example.eonet.view.viewmodel.CategoriesDataViewModel
import javax.inject.Inject

class CategoryDataFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val vm: CategoriesDataViewModel by viewModels { viewModelFactory }

    val args: CategoryDataFragmentArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().application as EonetApplication).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = ComposeView(requireContext())
        view.apply {
            setContent {
                EONETTheme {
                  //  slider()
                    eventsScreen()
                }
            }
        }
        return view
    }

    @Composable
    fun eventsScreen(){

        // Observe errors from ViewModel
        val errorMessage by vm.errorState.observeAsState(null)

        //Display error message if present
        errorMessage?.let {
            errorScreen(msg = it)

        }?:run {
            listOfEvents()
        }
    }

    @Composable
    private fun listOfEvents() {
        //Observe events from ViewModel
        val events by vm.events.observeAsState(emptyList())

        var sliderPosition by remember { mutableIntStateOf(360) }

        //State to hold progressIndicatorLoading
        val loading by vm.progressIndicatorLoading.observeAsState(false)

        var sliderEnabled by remember { mutableStateOf(false) }

        // API call
        LaunchedEffect(Unit) {
            vm.getCategoryEvents(args.passedCategoryArg)
        }

        LaunchedEffect(events){
            sliderEnabled = events.isNotEmpty()
        }

        Column {
            IndeterminateProgressIndicator(loading)
            Slider(
                modifier = Modifier.padding(8.dp),
                enabled = sliderEnabled,
                value = sliderPosition.toFloat(),
                onValueChange = {
                    sliderPosition = it.toInt()
                },
                colors = SliderDefaults.colors(
                    thumbColor = MaterialTheme.colorScheme.secondary,
                    activeTrackColor = MaterialTheme.colorScheme.secondary,
                    inactiveTrackColor = MaterialTheme.colorScheme.secondaryContainer,
                ),
                steps = 36,
                valueRange = 10f..360f,
                onValueChangeFinished = {
                    vm.daysSelected.onNext(sliderPosition)
                },
            )

            Spacer(modifier = Modifier.width(1.dp))

            Text(
                modifier = Modifier.padding(8.dp),
                text = "Events occurred in last ${sliderPosition} days",
                color = Color.Blue
            )

            // Display each events
            LazyColumn {
                items(events) { event ->
                    eventCard(event = event, onClick = {})
                }
            }
        }
    }

    @Preview
    @Composable
    fun PreviewErrorScreen(){
        errorScreen(msg = "Network error. Please check your internet connection. Network error.")
    }

    @Composable
    fun errorScreen(msg:String){
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
                vm.retry(args.passedCategoryArg)
            }) {
                Text(text = "Retry")
            }

        }
    }

    @Composable
    fun IndeterminateProgressIndicator(loading: Boolean) {
        if(loading) {
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
    fun eventCard(event: Event, onClick: (Event) -> Unit) {
        Column(
            modifier = Modifier
                .padding(all = 8.dp)
                .clickable { onClick(event) }
        )
        {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(5f),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 6.dp
                )
            ) {
                Text(
                    text = event.title,
                    modifier = Modifier.padding(all = 10.dp),
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Black
                )
                //Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Date: ${vm.formatDate(event.geometry[0].date)}",
                    modifier = Modifier.padding(all = 10.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.DarkGray

                )
            }
        }
    }
}