package com.example.eonet.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.eonet.EonetApplication
import com.example.eonet.R
import com.example.eonet.entities.CategoryX
import com.example.eonet.entities.Event
import com.example.eonet.entities.Geometry
import com.example.eonet.entities.Source
import com.example.eonet.ui.theme.EONETTheme
import com.example.eonet.view.customcompose.HyperlinkText
import com.example.eonet.view.viewmodel.EventDetailViewModel
import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EventDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EventDetailFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val vm: EventDetailViewModel by viewModels { viewModelFactory }

    val args: EventDetailFragmentArgs by navArgs()

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
                    populateEventData(event = args.passedEventArg)
                }
            }
        }
    }
    
    @Preview
    @Composable
    fun previewEventData(){
        populateEventData(event = Event(listOf(CategoryX("wildfires","Wildfires")),null,"26 miles east of Weiser, ID",
            listOf(Geometry(listOf(-116.439, 44.322333),"2024-09-02T18:09:00Z","acres",1000.00,"Point")),"EONET_10763",
            "https://eonet.gsfc.nasa.gov/api/v3/events/EONET_10763",
            listOf(Source("IRWIN","https://irwin.doi.gov/observer/")),"BIGFLAT Wildfire, Washington, Idaho"
        ))
    }

    @Composable
    fun populateEventData(event: Event){
        val headerStyle = MaterialTheme.typography.headlineMedium
        val titleStyle = MaterialTheme.typography.bodyLarge
        val contentStyle = MaterialTheme.typography.titleMedium
        Column(
            modifier = Modifier
                .padding(all = 8.dp)
        ){
            Text(
                text = "GENERAL",
                modifier = Modifier.padding(all = 10.dp),
                style = headerStyle,
            )
            Row {
                Text(
                    text = "ID: ",
                    modifier = Modifier.padding(all = 10.dp),
                    style = titleStyle,
                )
                Text(
                    text = event.id?:getString(com.example.eonet.R.string.not_available),
                    modifier = Modifier.padding(all = 10.dp),
                    style = contentStyle,
                )
            }

            Row{
                Text(
                    text = "Title: ",
                    modifier = Modifier.padding(all = 10.dp),
                    style = titleStyle,
                )
                Text(
                    text = event.title?:getString(com.example.eonet.R.string.not_available),
                    modifier = Modifier.padding(all = 10.dp),
                    style = contentStyle,
                )
            }

            Row{
                Text(
                    text = "Description: ",
                    modifier = Modifier.padding(all = 10.dp),
                    style = titleStyle,
                )
                Text(
                    text = "${event.description?:getString(com.example.eonet.R.string.not_available)}",
                    modifier = Modifier.padding(all = 10.dp),
                    style = contentStyle,
                )
            }

            Row{
                Text(
                    text = "Closed: ",
                    modifier = Modifier.padding(all = 10.dp),
                    style = titleStyle,
                )
                Text(
                    text = "${event.closed?:getString(R.string.not_available)}",
                    modifier = Modifier.padding(all = 10.dp),
                    style = contentStyle,
                )
            }

            Text(
                text = "SOURCE",
                modifier = Modifier.padding(all = 10.dp),
                style = headerStyle,
            )
            Row{
                Text(
                    text = "ID: ",
                    modifier = Modifier.padding(all = 10.dp),
                    style = titleStyle,
                )
                Text(
                    text = "${event.sources[0].id?:getString(com.example.eonet.R.string.not_available)}",
                    modifier = Modifier.padding(all = 10.dp),
                    style = contentStyle,
                )
            }

            Row{
                Text(
                    text = "URL: ",
                    modifier = Modifier.padding(all = 10.dp),
                    style = titleStyle,
                )
//                Text(
//                    text = "${event.sources[0].url?:getString(com.example.eonet.R.string.not_available)}",
//                    modifier = Modifier.padding(all = 10.dp),
//                    style = contentStyle,
//                )
                HyperlinkText(fullText = event.sources[0].url!!,
                    linkText = listOf(event.sources[0].url!!),
                    hyperlinks = listOf(event.sources[0].url!!)
                )
            }

            Text(
                text = "GEOMETRY",
                modifier = Modifier.padding(all = 10.dp),
                style = headerStyle,
            )
            Row{
                Text(
                    text = "Magnitude: ",
                    modifier = Modifier.padding(all = 10.dp),
                    style = titleStyle,
                )
                Text(
                    text = "${event.geometry[0].magnitudeValue?:"NA"}  ${event.geometry[0].magnitudeUnit?:"NA"}",
                    modifier = Modifier.padding(all = 10.dp),
                    style = contentStyle,
                )
            }

            Row{
                Text(
                    text = "Date: ",
                    modifier = Modifier.padding(all = 10.dp),
                    style = titleStyle,
                )
                Text(
                    text = vm.formatDate(event.geometry[0].date)?:getString(com.example.eonet.R.string.not_available),
                    modifier = Modifier.padding(all = 10.dp),
                    style = contentStyle,
                )
            }

            Row{
                Text(
                    text = "Type: ",
                    modifier = Modifier.padding(all = 10.dp),
                    style = titleStyle,
                )
                Text(
                    text = vm.formatDate(event.geometry[0].type)?:getString(com.example.eonet.R.string.not_available),
                    modifier = Modifier.padding(all = 10.dp),
                    style = contentStyle,
                )
            }

            Row{
                Text(
                    text = "Coordinates: ",
                    modifier = Modifier.padding(all = 10.dp),
                    style = titleStyle,
                )
                Text(
                    text = "[ ${event.geometry[0].coordinates?.get(0) ?:"NA"},${event.geometry[0].coordinates?.get(1) ?:"NA"} ]",
                    modifier = Modifier.padding(all = 10.dp),
                    style = contentStyle,
                )
            }
        }
    }
}