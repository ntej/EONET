package com.example.eonet.views

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.eonet.EonetApplication
import com.example.eonet.databinding.ActivityHostBinding
import com.example.eonet.viewmodel.HostViewModel
import javax.inject.Inject

class HostActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var vb:ActivityHostBinding

    private val vm: HostViewModel by viewModels { viewModelFactory }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as EonetApplication).appComponent.inject(this)
        vb = ActivityHostBinding.inflate(layoutInflater)
        setContentView(vb.root)
    }
}
