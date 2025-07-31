package com.tecnocajas.myloginapp

import android.app.ProgressDialog
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.tecnocajas.myloginapp.databinding.ActivityEditarPerfilBinding
import com.tecnocajas.myloginapp.databinding.ActivityMainBinding
import com.tecnocajas.myloginapp.databinding.ActivityOpcionesLoginBinding

class EditarPerfil : AppCompatActivity() {

    private lateinit var binding: ActivityEditarPerfilBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityEditarPerfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Espere por favor")
        progressDialog.setCanceledOnTouchOutside(false)

        cargarInfo()
    }

    private fun cargarInfo() {
        val ref = FirebaseDatabase.getInstance().getReference("Usuarios")
        ref.child("${firebaseAuth.uid}")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val nombres = "${snapshot.child("nombres").value}"
                    val imagen = "${snapshot.child("urlImagenPerfil").value}"
                    val f_nac = "${snapshot.child("fecha_nac").value}"
                    val telefono = "${snapshot.child("telefono").value}"
                    val codTelefono = "${snapshot.child("codigoTelefono").value}"

                    binding.EtNombres.setText(nombres)
                    binding.EtFechaNac.setText(f_nac)
                    binding.EtTelefono.setText(telefono)

                    try {
                        Glide.with(applicationContext)
                            .load(R.drawable.perfil)
                            .into(binding.IvPerfil)
                    } catch (e: Exception) {
                        Toast.makeText(
                            this@EditarPerfil,
                            "${e.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    try {
                        val codigo = codTelefono.replace("+", "").toInt() //+51 - 51
                        binding.selectorCod.setCountryForPhoneCode(codigo)
                    } catch (e: Exception) {
                        Toast.makeText(
                            this@EditarPerfil,
                            "${e.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
    }
}