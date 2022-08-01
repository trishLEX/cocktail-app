package ru.trishlex.cocktailapp

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import ru.trishlex.cocktailapp.cocktail.menu.MyCocktailsActivity
import ru.trishlex.cocktailapp.ingredient.menu.MyIngredientsActivity
import ru.trishlex.cocktailapp.ingredient.shoplist.ShopListActivity

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle

    companion object {
        private val TAB_NAMES = mapOf(0 to "Коктейли", 1 to "Ингредиенты")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawableLayout)
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)

        drawerLayout.addDrawerListener(toggle)

        val navView = findViewById<NavigationView>(R.id.navView)
        navView.setNavigationItemSelectedListener(this)

        toggle.isDrawerIndicatorEnabled = true

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toggle.syncState()

        val tabLayout = findViewById<TabLayout>(R.id.tabs)
        val viewPager = findViewById<ViewPager2>(R.id.view_pager)
        viewPager.isUserInputEnabled = false

        val viewPagerAdapter = ViewPagerAdapter(this)
        viewPager.adapter = viewPagerAdapter

        TabLayoutMediator(
            tabLayout,
            viewPager,
            TabLayoutMediator.TabConfigurationStrategy(this::tabStrategy)
        ).attach()

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPagerAdapter.onTabSelected(tab.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
            }

        })
    }

    private fun tabStrategy(tab: TabLayout.Tab, position: Int) {
        tab.text = TAB_NAMES[position]
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.myIngredients -> {
                startActivity(Intent(baseContext, MyIngredientsActivity::class.java))
                drawerLayout.close()
                true
            }
            R.id.myCocktails -> {
                startActivity(Intent(baseContext, MyCocktailsActivity::class.java))
                drawerLayout.close()
                true
            }
            R.id.myShopList -> {
                startActivity(Intent(baseContext, ShopListActivity::class.java))
                drawerLayout.close()
                true
            }
            else -> false
        }
    }
}