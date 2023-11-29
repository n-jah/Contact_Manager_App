package com.nja7.sirah
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.nja7.sirah.adapters.homeAdapter
import com.nja7.sirah.databinding.ActivityMainBinding
import com.nja7.sirah.model.Person

class SharedViewModel : ViewModel() {
    val sharedData = MutableLiveData<String>()
    val selectedContacts = MutableLiveData<List<Person>>()

}
class MainActivity : AppCompatActivity() {
    private lateinit var navControl: NavController
    lateinit var binding: ActivityMainBinding
    lateinit var adapter: homeAdapter
    private lateinit var searchBar: SearchView
    private lateinit var toolbar: Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        navControl = Navigation.findNavController(this, R.id.nav_host_fragment)
        searchBar = findViewById(R.id.search_bar_home)
        val title: TextView = binding.textviewHome
        val addPerson: ImageView = binding.addPerson
        val report: ImageView = binding.reportDrawn

        addPerson.setOnClickListener {
            navControl.navigate(R.id.action_home2_to_contacts2)

        }
        searchBar.setOnQueryTextFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                title.visibility = View.GONE
                addPerson.visibility = View.GONE
                report.visibility = View.GONE
                searchBar.setBackgroundResource(R.drawable.search_viewbg)
            } else {
                title.visibility = View.VISIBLE
                addPerson.visibility = View.VISIBLE
                report.visibility = View.VISIBLE
                searchBar.setBackgroundResource(android.R.color.transparent)
            }
        }

        
        val sharedViewModel = ViewModelProvider(this).get(SharedViewModel::class.java)

        searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                sharedViewModel.sharedData.value = newText
                return true
            }
        })

    }
}

