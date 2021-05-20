package com.smic.testapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.smic.testapp.auth.Authorization
import com.smic.testapp.auth.User
import com.smic.testapp.auth.emptyUser
import com.smic.testapp.ui.IOnBackPressed
import com.smic.testapp.ui.github.GithubUserFragment
import com.smic.testapp.utils.getCurrentFragment
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {

    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var authorization: Authorization

    private lateinit var txtUserName: TextView
    private lateinit var txtUserEmail: TextView
    private lateinit var imgAvatar: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        sharedViewModel = ViewModelProvider(this).get(SharedViewModel::class.java)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.githubUserFragment
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val navHeader = navView.getHeaderView(0)
        txtUserName = navHeader.findViewById(R.id.txtUserName)
        txtUserEmail = navHeader.findViewById(R.id.txtUserEmail)
        imgAvatar = navHeader.findViewById(R.id.imgAvatar)
        val txtExit = navHeader.findViewById<TextView>(R.id.txtExit)

        sharedViewModel.authorizationLiveData.observe(this, {
            if (it != null) authorization = it
        })
        sharedViewModel.user.observe(this, {
            fillFields(it)
            if (it == emptyUser) {
                txtExit.visibility = View.INVISIBLE
                navController.navigate(R.id.startFragment)
            } else if (supportFragmentManager.getCurrentFragment !is GithubUserFragment){
                txtExit.visibility = View.VISIBLE
                navController.navigate(R.id.githubUserFragment)
            }
        })

        sharedViewModel.drawerState.observe(this, {
            if (it) drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            else drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        })

        txtExit.setOnClickListener {
            sharedViewModel.signOut()
        }
    }

    private fun fillFields(user: User) {
        txtUserName.text = user.userName
        txtUserEmail.text = user.userEmailOrId
        Picasso.get()
            .load(user.userPhoto)
            .fit()
            .error(R.drawable.ic_not_auth)
            .into(imgAvatar)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (data == null || !authorization.getResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data)
        } else {
            if (resultCode == -1) sharedViewModel.requestUser(data)

        }
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.getCurrentFragment
        if (fragment is IOnBackPressed) {//from start fragment we can finish app
            sharedViewModel.signOut()
            finish()
        } else
            super.onBackPressed()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            R.id.change_account -> {
                sharedViewModel.changeAccount()
            }

        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        sharedViewModel.authorizationLiveData.removeObservers(this)
        sharedViewModel.user.removeObservers(this)
        sharedViewModel.drawerState.removeObservers(this)
    }
}