package com.example.afternoonfirebasedatabaseapp

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase

class UserupdateActivity : AppCompatActivity() {
    lateinit var edtName : EditText
    lateinit var edtEmail : EditText
    lateinit var edtIdNumber : EditText
    lateinit var btnUpdate: Button
    lateinit var progressDialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_userupdate)
        edtName = findViewById(R.id.mEdtName)
        edtEmail = findViewById(R.id.mEdtEmail)
        edtIdNumber = findViewById(R.id.mEdtIdNumber)
        btnUpdate = findViewById(R.id.mBtnUpdateUser)
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Updating")
        progressDialog.setMessage("Please wait")

        // Receive data from the intent
        var receivedName = intent.getStringExtra("name")
        var receivedEmail = intent.getStringExtra("email")
        var receivedIdNumber = intent.getStringExtra("idNumber")
        var receivedId = intent.getStringExtra("id")

        // Display the received data on the EditTexts
        edtName.setText(receivedName)
        edtEmail.setText(receivedEmail)
        edtIdNumber.setText(receivedIdNumber)

        // Set an onClick listener to button update
        btnUpdate.setOnClickListener {
            var name = edtName.text.toString().trim()
            var email = edtEmail.text.toString().trim()
            var idNumber = edtIdNumber.text.toString().trim()
            var id = System.currentTimeMillis().toString()
            // Check if the user is submitting empty fields
            if(name.isEmpty()){
                edtName.setError("Please fill this field")
                edtName.requestFocus()
            }else if (email.isEmpty()){
                edtEmail.setError("Please fill this field")
                edtEmail.requestFocus()
            }else if (idNumber.isEmpty()){
                edtIdNumber.setError("Please fill this field")
                edtIdNumber.requestFocus()
            }else{
                // Proceed to save data
                var user = User(name, email, idNumber, id)
                // Create a reference to FirebaseDatabase
                var ref = FirebaseDatabase.getInstance().
                getReference().child("Users/"+id)
                //Start saving
                progressDialog.show()
                ref.setValue(user).addOnCompleteListener{
                    progressDialog.dismiss()
                    if (it.isSuccessful){
                        Toast.makeText(this,"User updated successfully",
                            Toast.LENGTH_LONG).show()
                        startActivity(Intent(this@UserupdateActivity,
                            UsersActivity::class.java))
                        finish()
                    }else{
                        Toast.makeText(this,"User Updating failed",
                            Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}