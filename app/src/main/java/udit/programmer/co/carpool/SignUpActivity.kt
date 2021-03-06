package udit.programmer.co.carpool

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_sign_up.*
import udit.programmer.co.carpool.Models.User

class SignUpActivity : AppCompatActivity() {

    val auth by lazy {
        FirebaseAuth.getInstance()
    }
    val db by lazy {
        FirebaseDatabase.getInstance()
    }
    val users by lazy {
        db.getReference("Users")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        register_btn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {

                auth.createUserWithEmailAndPassword(
                    email_et.text.toString(),
                    password_et.text.toString()
                )
                    .addOnSuccessListener {
                        var user = User(
                            name_et.text.toString(),
                            email_et.text.toString(),
                            password_et.text.toString(),
                            phone_et.text.toString()
                        )
                        users.child(FirebaseAuth.getInstance().currentUser!!.uid)
                            .setValue(user)
                            .addOnSuccessListener {
                                Snackbar.make(
                                    signup_activity_layout,
                                    "Registered Successfully",
                                    Snackbar.LENGTH_LONG
                                ).show()
                            }
                            .addOnFailureListener {
                                Snackbar.make(
                                    signup_activity_layout,
                                    "FAILED : " + it.toString(),
                                    Snackbar.LENGTH_LONG
                                ).show()
                            }
                    }
                    .addOnFailureListener {
                        Snackbar.make(
                            signup_activity_layout,
                            "FAILED : " + it.toString(),
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                finish()
            }

        })
    }
}
