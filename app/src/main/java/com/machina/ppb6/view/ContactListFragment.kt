package com.machina.ppb6.view

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.machina.ppb6.R
import com.machina.ppb6.data.model.Contact
import com.machina.ppb6.data.source.ContactDbSource
import com.machina.ppb6.databinding.FragmentContactListBinding
import com.machina.ppb6.db.ContactDbHelper
import com.machina.ppb6.view.adapter.ContactListAdapter
import com.machina.ppb6.view.dialog.DialogContactForm


class ContactListFragment : Fragment() {

    private var _binding: FragmentContactListBinding? = null
    private val binding get() = _binding!!

    private lateinit var contactAdapter: ContactListAdapter

    private lateinit var dbHelper: SQLiteOpenHelper
    private lateinit var db: SQLiteDatabase
    private lateinit var dbSource: ContactDbSource


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentContactListBinding.inflate(inflater, container, false)

        dbHelper = ContactDbHelper(requireContext())
        db = dbHelper.writableDatabase
        dbSource = ContactDbSource(db)
        contactAdapter = ContactListAdapter(
            requireContext(),
            R.layout.item_contact,
            dbSource.getAllContactEntries(),
            this::onItemDelete,
            this::onItemEdit,
            this::navigateToContactDetail
        )
        binding.mainListView.adapter = contactAdapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.insertBtn.setOnClickListener {
            DialogContactForm("Add new contact", "Name", "Phone", "Add", "Cancel", true, object : DialogContactForm.DialogAddItemListener {
                override fun onDialogPositiveClick(dialog: DialogFragment, contact: Contact) {
                    val res = dbSource.insertContactEntries(contact)
                    resolveDbChange()
                }

                override fun onDialogNegativeClick(dialog: DialogFragment) {
                    dialog.dismiss()
                }
            }).show(parentFragmentManager, "insert dialog")
        }

        binding.searchBtn.setOnClickListener {
            DialogContactForm("Search contact by name", "Name", "Phone", "Search", "Cancel", false, object : DialogContactForm.DialogAddItemListener {
                override fun onDialogPositiveClick(dialog: DialogFragment, contact: Contact) {
                    contactAdapter = ContactListAdapter(
                        requireContext(),
                        R.layout.item_contact,
                        dbSource.searchEntries(contact.name),
                        this@ContactListFragment::onItemDelete,
                        this@ContactListFragment::onItemEdit,
                        this@ContactListFragment::navigateToContactDetail
                    )
                    binding.mainListView.adapter = contactAdapter
                }

                override fun onDialogNegativeClick(dialog: DialogFragment) {
                    resolveDbChange()
                    dialog.dismiss()
                }
            }).show(parentFragmentManager, "insert dialog")
        }
    }

    private fun onItemDelete(name: String) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Delete data with name: $name?")
            .setPositiveButton("Delete") { dialog, which ->
                val res = dbSource.deleteEntries(name)
                if (res) {
                    resolveDbChange()
                }
            }
            .setNegativeButton("Cancel") { dialog, which ->
                dialog.dismiss()
            }
            .show()
    }

    private fun onItemEdit(name: String) {
        DialogContactForm("Edit contact $name", "Name", "Phone", "Save", "Cancel", true,
            object : DialogContactForm.DialogAddItemListener {
                override fun onDialogPositiveClick(dialog: DialogFragment, contact: Contact) {
                    val res = dbSource.updateEntries(contact, name)
                    if (res) {
                        resolveDbChange()
                    }
                }

                override fun onDialogNegativeClick(dialog: DialogFragment) {
                    dialog.dismiss()
                }
            }).show(parentFragmentManager, "insert dialog")
    }

    private fun resolveDbChange() {
        contactAdapter = ContactListAdapter(
            requireContext(),
            R.layout.item_contact,
            dbSource.getAllContactEntries(),
            this@ContactListFragment::onItemDelete,
            this@ContactListFragment::onItemEdit,
            this@ContactListFragment::navigateToContactDetail
        )
        binding.mainListView.adapter = contactAdapter
    }

    private fun navigateToContactDetail(name: String, phone: String) {
        val activity = requireActivity()
        if (activity is MainActivity) {
            activity.navigateToContactDetails(name, phone)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ContactListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            ContactListFragment()
    }
}