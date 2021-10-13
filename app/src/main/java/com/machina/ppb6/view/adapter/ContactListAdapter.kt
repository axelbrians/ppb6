package com.machina.ppb6.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.machina.ppb6.data.model.Contact
import com.machina.ppb6.databinding.ItemContactBinding

class ContactListAdapter(
    context: Context,
    viewId: Int,
    dataSet: List<Contact>,
    private val onItemDelete: (String) -> Unit,
    private val onItemEdit: (String) -> Unit,
    private val onItemClick: (String, String) -> Unit
): ArrayAdapter<Contact>(context, viewId, dataSet) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val data = getItem(position)
        val binding = ItemContactBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        binding.apply {
            itemContactName.text = data?.name
            itemContactPhone.text = data?.phone

            itemContactContainer.setOnClickListener {
                getItem(position)?.let { contact -> onItemClick(contact.name, contact.phone) }
            }

            itemContactDelete.setOnClickListener {
                getItem(position)?.let { contact -> onItemDelete(contact.name) }
            }

            itemContactEdit.setOnClickListener {
                getItem(position)?.let { contact -> onItemEdit(contact.name) }
            }
        }

        return binding.root
    }
}