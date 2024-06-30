package com.example.testoviy_dating

import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.testoviy_dating.databinding.ActivityBottomBinding
import me.ibrahimsn.lib.SmoothBottomBar


class BottomActivity : AppCompatActivity() {
    lateinit var binding: ActivityBottomBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBottomBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navController = findNavController(R.id.main_fragment)
//        setupActionBarWithNavController(navController)
        setupSmoothBottomMenu()

    }

    private fun setupSmoothBottomMenu() {
        val popupMenu = PopupMenu(this, null)
        popupMenu.inflate(R.menu.bottom_menu)
        val menu = popupMenu.menu
        //binding.bottomBar.setupWithNavController(menu, navController)
        binding.bottomBar.setupWithNavController( menu,navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }


    fun showBottomNavigation()
    {
        binding.bottomBar.visibility = View.VISIBLE




    }

    fun hideBottomNavigation()
    {
        binding.bottomBar.visibility = View.GONE

    }

}