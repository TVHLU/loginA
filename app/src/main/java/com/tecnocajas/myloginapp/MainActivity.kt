package com.tecnocajas.myloginapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.tecnocajas.myloginapp.Fragmentos.FragmentConfiguracion
import com.tecnocajas.myloginapp.Fragmentos.FragmentInicio
import com.tecnocajas.myloginapp.Fragmentos.FragmentPerfil
import com.tecnocajas.myloginapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()
        comprobarSesion()

        verFragmentInicio()

        binding.BottomNV.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    verFragmentInicio()
                    true
                }
                R.id.navigation_profile -> {
                    verFragmentPerfil()
                    true
                }
                R.id.navigation_settings -> {
                    verFragmentConfiguracion()
                    true
                }
                else -> false
            }
        }
    }

    private fun comprobarSesion(){
        if (firebaseAuth.currentUser == null){
            startActivity(Intent(this, OpcionesLogin::class.java))
            finishAffinity()
        }
    }
    private fun verFragmentInicio(){
        binding.TituloRl.text="Inicio"
        val fragment = FragmentInicio()
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.FragmentL1.id, fragment, "FragmentInicio")
        fragmentTransaction.commit()
    }

    private fun verFragmentPerfil(){
        binding.TituloRl.text="Perfil"
        val fragment = FragmentPerfil()
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.FragmentL1.id, fragment, "FragmentPerfil")
        fragmentTransaction.commit()
    }
    private fun verFragmentConfiguracion(){
        binding.TituloRl.text="Configuracion"
        val fragment = FragmentConfiguracion()
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.FragmentL1.id, fragment, "FragmentConfiguracion")
        fragmentTransaction.commit()
    }

}