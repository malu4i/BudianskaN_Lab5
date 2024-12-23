package com.example.budianskan_lab5

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ParticipantAdapter(
    private val participants: List<Participant>,
    private val onItemClick: (Participant) -> Unit
) : RecyclerView.Adapter<ParticipantAdapter.ParticipantViewHolder>() {

    // ViewHolder для зберігання посилань на елементи макета
    class ParticipantViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.textName)
        val email: TextView = view.findViewById(R.id.textEmail)
        val phoneNumber: TextView = view.findViewById(R.id.textPhoneNumber)
    }

    // Створення ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParticipantViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_participant, parent, false)
        return ParticipantViewHolder(view)
    }

    // Прив'язка даних до ViewHolder
    override fun onBindViewHolder(holder: ParticipantViewHolder, position: Int) {
        val participant = participants[position]
        holder.name.text = participant.name
        holder.email.text = participant.email
        holder.phoneNumber.text = participant.phoneNumber

        // Обробка кліка по елементу
        holder.itemView.setOnClickListener {
            onItemClick(participant)
        }
    }

    // Кількість елементів у списку
    override fun getItemCount() = participants.size
}
