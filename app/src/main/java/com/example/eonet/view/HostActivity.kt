package com.example.eonet.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.example.eonet.EonetApplication
import com.example.eonet.databinding.ActivityHostBinding
import com.example.eonet.view.viewmodel.HostViewModel
import javax.inject.Inject

/**
 * Key Differences Between ComponentActivity and AppCompatActivity/FragmentActivity:
 * ComponentActivity:
 *
 * Lightweight, specifically designed to work with Jetpack Compose.
 * It provides lifecycle-aware components like ViewModel and SavedStateRegistry, but it does not include built-in support for Fragments or AppCompat features.
 * You would use this class primarily when working with Compose-only UIs, or if you do not need Fragments in your app.
 * FragmentActivity:
 *
 * Extends ComponentActivity and adds full support for Fragments.
 * This class is needed if you are using the Fragment API in your app (e.g., FragmentContainerView or NavHostFragment).
 * AppCompatActivity:
 *
 * Extends FragmentActivity and includes backward compatibility support for various features, including the AppCompat library's modern UI components (e.g., Toolbar, ActionBar).
 * This is the most commonly used base class for activities that need to support a wide range of UI features, fragments, and backward compatibility.
 * **/
class HostActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var vb:ActivityHostBinding

    private val vm: HostViewModel by viewModels { viewModelFactory }
    override fun onCreate(savedInstanceState: Bundle?) {
        // Handle the splash screen transition.
        installSplashScreen()

        super.onCreate(savedInstanceState)
        (application as EonetApplication).appComponent.inject(this)
        vb = ActivityHostBinding.inflate(layoutInflater)
        setContentView(vb.root)
    }
}
