package com.tecnocajas.myloginapp.Fragmentos

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.tecnocajas.myloginapp.Constantes
import com.tecnocajas.myloginapp.EditarPerfil
import com.tecnocajas.myloginapp.OpcionesLogin
import com.tecnocajas.myloginapp.R
import com.tecnocajas.myloginapp.databinding.FragmentPerfilBinding

class FragmentPerfil : Fragment() {
    private lateinit var binding : FragmentPerfilBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var miContext : Context

    override fun onAttach(context: Context) {
        miContext =context
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =FragmentPerfilBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()
        leerInfo()
        binding.BtnEditarPerfil.setOnClickListener{
            startActivity(Intent(miContext, EditarPerfil::class.java))

        }



                binding.BtnCerrarSesion.setOnClickListener {
          firebaseAuth.signOut()
            startActivity(Intent(miContext, OpcionesLogin::class.java))
            activity?.finishAffinity()
        }
    }

    private fun leerInfo() {
        val ref = FirebaseDatabase.getInstance().getReference("Usuarios")
        ref.child("${firebaseAuth.currentUser!!.uid}")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val nombres = "${snapshot.child("nombres").value}"
                    val email = "${snapshot.child("email").value}"
                    val imagen = "${snapshot.child("urlImagenPerfil").value}"
                    val f_nac = "${snapshot.child("fecha_nac").value}"
                    var tiempo = "${snapshot.child("tiempo").value}"
                    val telefono = "${snapshot.child("telefono").value}"
                    val codTelefono = "${snapshot.child("codigoTelefono").value}"
                    val proveedor = "${snapshot.child("proveedor").value}"

                    val cod_tel = codTelefono + telefono

                    if (tiempo == "null") {
                        tiempo = "0"
                    }

                    val for_tiempo = Constantes.obtenerFecha(tiempo.toLong())
                    binding.TvEmail.text = email
                    binding.TvNombres.text = nombres
                    binding.TvNacimiento.text = f_nac
                    binding.TvTelefono.text = cod_tel
                    binding.TvMiembro.text = for_tiempo

                    try {
                        Glide.with(miContext)
                            .load(imagen)
                            .placeholder(R.drawable.perfil)
                            .into(binding.TvPerfil)
                    } catch (e: Exception) {
                        Toast.makeText(
                            miContext,
                            "Error al cargar la imagen: ${e.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    if (proveedor == "Email"){
                        val esVerificado = firebaseAuth.currentUser!!.isEmailVerified
                        if (esVerificado){
                            binding.TvEstadoCuenta.text = "Verificado"
                        }else{
                            binding.TvEstadoCuenta.text = "No verificado"
                        }
                    }else{
                        binding.TvEstadoCuenta.text = "Verificado"
                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    TODO(reason = "Not yet implemented")
                }
            })
    }
}