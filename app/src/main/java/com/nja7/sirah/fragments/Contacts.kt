package com.nja7.sirah.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nja7.sirah.R
import com.nja7.sirah.SharedViewModel
import com.nja7.sirah.adapters.ContactsAdapter
import com.nja7.sirah.databinding.FragmentContactsBinding
import com.nja7.sirah.model.Person
class Contacts : Fragment() {
    private lateinit var navControl: NavController
    private lateinit var binding: FragmentContactsBinding
    private lateinit var contactsList: MutableList<Person>
    private lateinit var adapter: ContactsAdapter
    companion object {
        private const val CONTACTS_PERMISSION_REQUEST = 1
    }
    private val sharedViewModel: SharedViewModel by activityViewModels()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        contactsList = mutableListOf()


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactsBinding.inflate(inflater, container, false)
        val recyclerView: RecyclerView = binding.recyclerViewContents

        adapter = ContactsAdapter(contactsList)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(binding.root.context)

// Request contacts permission if not granted
        if (ContextCompat.checkSelfPermission(
                binding.root.context,
                Manifest.permission.READ_CONTACTS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            readContacts()
        } else {
            requestPermissions(
                arrayOf(Manifest.permission.READ_CONTACTS),
                CONTACTS_PERMISSION_REQUEST
            )
        }

        // Inflate the layout for this fragment
        return binding.root

    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inti(view)


    }

    private fun inti(view: View) {
        navControl = Navigation.findNavController(view)
        sharedViewModel.sharedData.observe(viewLifecycleOwner, Observer { data ->
            adapter.filter(data)
        })
        binding.floatingActionButton.setOnClickListener {
            sendSelectedContacts()
            Toast.makeText(context, adapter.getSelectedContacts().size.toString(), Toast.LENGTH_SHORT).show()
            val selectedContacts = adapter.getSelectedContacts()
            sharedViewModel.selectedContacts.value = selectedContacts

        }



    }

    private fun readContacts() {
        val cursor: Cursor? = activity?.contentResolver?.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            null
        )

        cursor?.use {
            while (it.moveToNext()) {
                val name =
                    it.getString(it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                val phoneNumber =
                    it.getString(it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                contactsList.add(Person(name, phoneNumber))
            }
        }

        adapter.notifyDataSetChanged()
    }

    // Handle permission result
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            CONTACTS_PERMISSION_REQUEST -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    readContacts()
                } else {
                    // Handle permission denied
                    Toast.makeText(
                        requireContext(),
                        "Contacts permission denied",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun sendSelectedContacts() {
        val selectedContacts = adapter.getSelectedContacts()
        // Pass the selected contacts to another fragment
        val bundle = Bundle().apply {
            putParcelableArrayList("selected_contacts", ArrayList(selectedContacts))
        }
        val receiverFragment = Home()
        receiverFragment.arguments = bundle

        navControl.navigate(R.id.action_contacts2_to_home22)
    }
}

