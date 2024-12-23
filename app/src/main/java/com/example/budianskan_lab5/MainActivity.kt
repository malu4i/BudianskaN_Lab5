package com.example.budianskan_lab5

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private val participants = mutableListOf(
        Participant(1, "Іван Іваненко", "ivan@example.com", "+380501234567", "Математика"),
        Participant(2, "Ольга Петрівна", "olga@example.com", "+380671234567", "Фізика"),
        Participant(3, "Сергій Коваленко", "serg@example.com", "+380981234567", "Хімія")
    )
    private lateinit var adapter: ParticipantAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Налаштування RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ParticipantAdapter(participants) { participant ->
            showParticipantOptions(participant)
        }
        recyclerView.adapter = adapter

        // Налаштування FloatingActionButton
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            addParticipant()
        }
    }

    private fun showParticipantOptions(participant: Participant) {
        // Опції для дій над учасником
        val options = arrayOf("Дзвонити", "Редагувати", "Видалити", "Докладно")
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Виберіть дію")
        builder.setItems(options) { _, which ->
            when (which) {
                0 -> callParticipant(participant)    // Дзвінок
                1 -> editParticipant(participant)    // Редагування
                2 -> deleteParticipant(participant)  // Видалення
                3 -> showDetails(participant)        // Докладно
            }
        }
        builder.show()
    }

    private fun callParticipant(participant: Participant) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:${participant.phoneNumber}")
        startActivity(intent)
    }

    private fun addParticipant() {
        // Додаємо нового учасника (статичні дані для прикладу)
        val newParticipant = Participant(
            id = participants.size + 1,
            name = "Новий Учасник",
            email = "new@example.com",
            phoneNumber = "+380991112233",
            competition = "Новий Конкурс"
        )
        participants.add(newParticipant)
        adapter.notifyItemInserted(participants.size - 1)
        Toast.makeText(this, "Учасника додано", Toast.LENGTH_SHORT).show()
    }

    private fun editParticipant(participant: Participant) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_edit_participant, null)

        // Заповнюємо поля поточними даними
        val editName = dialogView.findViewById<android.widget.EditText>(R.id.editName)
        val editEmail = dialogView.findViewById<android.widget.EditText>(R.id.editEmail)
        val editPhone = dialogView.findViewById<android.widget.EditText>(R.id.editPhone)
        val editCompetition = dialogView.findViewById<android.widget.EditText>(R.id.editCompetition)

        editName.setText(participant.name)
        editEmail.setText(participant.email)
        editPhone.setText(participant.phoneNumber)
        editCompetition.setText(participant.competition)

        // Відображення діалогу редагування
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Редагувати учасника")
        builder.setView(dialogView)
        builder.setPositiveButton("Зберегти") { _, _ ->
            val updatedParticipant = participant.copy(
                name = editName.text.toString(),
                email = editEmail.text.toString(),
                phoneNumber = editPhone.text.toString(),
                competition = editCompetition.text.toString()
            )
            val index = participants.indexOf(participant)
            if (index != -1) {
                participants[index] = updatedParticipant
                adapter.notifyItemChanged(index)
                Toast.makeText(this, "Дані оновлено", Toast.LENGTH_SHORT).show()
            }
        }
        builder.setNegativeButton("Скасувати") { dialog, _ ->
            dialog.dismiss()
        }
        builder.show()
    }

    private fun deleteParticipant(participant: Participant) {
        val index = participants.indexOf(participant)
        if (index != -1) {
            participants.removeAt(index)
            adapter.notifyItemRemoved(index)
            Toast.makeText(this, "Учасника видалено", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showDetails(participant: Participant) {
        val message = """
            Ім'я: ${participant.name}
            E-mail: ${participant.email}
            Телефон: ${participant.phoneNumber}
            Конкурс: ${participant.competition}
        """.trimIndent()
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Докладно")
        builder.setMessage(message)
        builder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }
        builder.show()
    }
}
