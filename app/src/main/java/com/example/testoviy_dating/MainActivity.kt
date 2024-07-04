package com.example.testoviy_dating

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.findNavController
import com.example.testoviy_dating.adapter.RegistrationAdapter
import com.example.testoviy_dating.databinding.ActivityMainBinding
import com.example.testoviy_dating.databinding.DialogLoginBinding
import com.example.testoviy_dating.models.Registration
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var firebaseFirestore: FirebaseFirestore
    lateinit var list: ArrayList<Registration>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseFirestore = FirebaseFirestore.getInstance()


        setSpinner()
        addToFirestore()
        login()





    }

    private fun login() {
        binding.next.setOnClickListener {
           ShowDialgue()
        }
    }

    private fun ShowDialgue() {
        val alertDialog = AlertDialog.Builder(binding.root.context)
        val dialog = alertDialog.create()
        val dialogView = DialogLoginBinding.inflate(
            LayoutInflater.from(binding.root.context),null,false
        )


        dialogView.buttonSave.setOnClickListener {
            val namecha = dialogView.editTextName.text.toString().trim()
            val password = dialogView.editTextPassword.text.toString().trim()
            passwordChecking(namecha,password)

        }





        dialog.setView(dialogView.root)
        dialog.show()
    }

    private fun passwordChecking(namecha: String, password: String) {




                            val intent = Intent(this, BottomActivity::class.java)
                            intent.putExtra("password",password)
                            intent.putExtra("gender",namecha)
                            startActivity(intent)


    }

    private fun addToFirestore() {
        binding.add.setOnClickListener {
            // Set the item selected listener
            val name = binding.name.text.toString().trim()
            val surname = binding.surname.text.toString().trim()
            val age = binding.age.text.toString().trim()
            val gender = binding.genderSpinner.selectedItem.toString()
            val password = binding.password.text.toString().trim()
            val passwordRecovery = binding.passwordrecovery.text.toString().trim()

            if (name.isNotEmpty() && surname.isNotEmpty() && age.isNotEmpty() && gender.isNotEmpty() && password.isNotEmpty() && passwordRecovery.isNotEmpty()) {
                val registration =
                    Registration(name, surname, age, gender, password, passwordRecovery)


                val intent = Intent(this, BottomActivity::class.java)
                intent.putExtra("log","registration")
                intent.putExtra("reg",registration)
                startActivity(intent)


//                firebaseFirestore.collection("registration")
//                    .add(registration)
//                    .addOnSuccessListener {
//                        Toast.makeText(
//                            binding.root.context,
//                            "Succesfully saved",
//                            Toast.LENGTH_SHORT
//                        )
//                            .show()
//
//                        val intent = Intent(this, BottomActivity::class.java)
//                        startActivity(intent)
//
//
//                    }.addOnFailureListener {
//                        Toast.makeText(binding.root.context, it.message, Toast.LENGTH_SHORT).show()
//                    }
//            } else {
//                Toast.makeText(
//                    binding.root.context,
//                    "Please add full information",
//                    Toast.LENGTH_SHORT
//                ).show()
//
            }


        }
    }

    private fun setSpinner() {


        val genderSpinner = binding.genderSpinner
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            binding.root.context,
            R.array.gender_options,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            genderSpinner.adapter = adapter
        }





    }
}