package com.machina.ppb6.view

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.machina.ppb6.ContactDetailFragment
import com.machina.ppb6.R
import com.machina.ppb6.data.model.Contact
import com.machina.ppb6.data.source.ContactDbSource
import com.machina.ppb6.databinding.ActivityMainBinding
import com.machina.ppb6.db.ContactDbHelper
import com.machina.ppb6.view.adapter.ContactListAdapter
import com.machina.ppb6.view.dialog.DialogContactForm
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
//    private lateinit var contactAdapter: ContactListAdapter
//
//    private lateinit var dbHelper: SQLiteOpenHelper
//    private lateinit var db: SQLiteDatabase
//    private lateinit var dbSource: ContactDbSource

    private val contacts = listOf(
        Contact("Moriarty", "+982131312313"),
        Contact("Ran", "+982131312313"),
        Contact("Yamato", "+982131312313"),
        Contact("Luffy", "+982131312313"),
        Contact("Kaido", "+982131312313"),
        Contact("Steelseries", "+982131312313"),
        Contact("Zephyrus", "+982131312313"),
        Contact("Gooju", "+982131312313")
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.activity_main_host_fragment, ContactListFragment())
        fragmentTransaction.commit()

//        dbHelper = ContactDbHelper(this)
//        db = dbHelper.writableDatabase
//        dbSource = ContactDbSource(db)

//        contactAdapter = ContactListAdapter(
//            this,
//            R.layout.item_contact,
//            dbSource.getAllContactEntries(),
//            this::onItemDelete,
//            this::onItemEdit
//        )
//        binding.mainListView.adapter = contactAdapter
//
//
//        binding.insertBtn.setOnClickListener {
//            DialogContactForm("Add new contact", "Name", "Phone", "Add", "Cancel", true, object : DialogContactForm.DialogAddItemListener {
//                override fun onDialogPositiveClick(dialog: DialogFragment, contact: Contact) {
//                    val res = dbSource.insertContactEntries(contact)
//                    resolveDbChange()
//                }
//
//                override fun onDialogNegativeClick(dialog: DialogFragment) {
//                    dialog.dismiss()
//                }
//            }).show(supportFragmentManager, "insert dialog")
//        }
//
//        binding.searchBtn.setOnClickListener {
//            DialogContactForm("Search contact by name", "Name", "Phone", "Search", "Cancel", false, object : DialogContactForm.DialogAddItemListener {
//                override fun onDialogPositiveClick(dialog: DialogFragment, contact: Contact) {
//                    contactAdapter = ContactListAdapter(
//                        this@MainActivity,
//                        R.layout.item_contact,
//                        dbSource.searchEntries(contact.name),
//                        this@MainActivity::onItemDelete,
//                        this@MainActivity::onItemEdit
//                    )
//                    binding.mainListView.adapter = contactAdapter
//                }
//
//                override fun onDialogNegativeClick(dialog: DialogFragment) {
//                    resolveDbChange()
//                    dialog.dismiss()
//                }
//            }).show(supportFragmentManager, "insert dialog")
//        }
    }

    fun navigateToContactDetails(name: String, phone: String) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.activity_main_host_fragment, ContactDetailFragment.newInstance(name, phone))
        fragmentTransaction.addToBackStack(DETAIL_CONTACT)
        fragmentTransaction.commit()
        Timber.d("backstack entry count ${supportFragmentManager.backStackEntryCount}")
    }

    override fun onBackPressed() {
        val fragmentManager = supportFragmentManager
        Timber.d("backstack entry count ${fragmentManager.backStackEntryCount}")

        super.onBackPressed()

    }

    private fun replaceFragment() {
//        val fragmentTransaction = supportFragmentManager.beginTransaction()

    }

    companion object {
        const val DETAIL_CONTACT = "detail_contact"
    }
}