package com.nja7.sirah.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.nja7.sirah.SharedViewModel
import com.nja7.sirah.adapters.homeAdapter
import com.nja7.sirah.databinding.FragmentHomeBinding
import com.nja7.sirah.model.Person

class Home : Fragment() {
        private lateinit var navControl: NavController
        private lateinit var binding: FragmentHomeBinding
    private lateinit var mutableList: MutableList<Person>
    private lateinit var adapter: homeAdapter

//    private var listener: OnElementSelectedListener? = null
    private val sharedViewModel: SharedViewModel by activityViewModels()


    var nameList: MutableList<Person> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {




        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
             inti(view)

    }


    private fun inti(view: View) {
        navControl = Navigation.findNavController(view)

        adapter = homeAdapter(nameList)
        binding.recyclerViewHome.adapter = adapter
        binding.recyclerViewHome.layoutManager = LinearLayoutManager(view.context)

        // Observe changes in selectedContacts
        sharedViewModel.selectedContacts.observe(viewLifecycleOwner) { contacts ->
            nameList.clear() // Clear existing data
            nameList.addAll(contacts) // Add new data
            adapter.notifyDataSetChanged()
        }

        // Observe changes in sharedData (assuming sharedData is another LiveData in your ViewModel)
        sharedViewModel.sharedData.observe(viewLifecycleOwner, Observer { data ->
            adapter.filter(data)
        })
    }

}



