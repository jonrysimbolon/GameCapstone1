package id.zoneordering.gamecapstone1.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import id.zoneordering.core.data.Resource
import id.zoneordering.core.ui.DigimonAdapter
import id.zoneordering.gamecapstone1.R
import id.zoneordering.gamecapstone1.databinding.ActivityMainBinding
import id.zoneordering.gamecapstone1.detail.DetailActivity
import id.zoneordering.gamecapstone1.setting.SettingActivity
import id.zoneordering.gamecapstone1.setting.SettingViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private val adapter by lazy {
        DigimonAdapter()
    }

    private val mainViewModel: MainViewModel by viewModel()

    private val settingViewModel: SettingViewModel by viewModel()

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.rvDigimon.adapter = adapter

        settingViewModel.getThemeSettings()
            .observe(this) { isDarkModeActive: Boolean ->
                AppCompatDelegate.setDefaultNightMode(
                    if (isDarkModeActive)
                        AppCompatDelegate.MODE_NIGHT_YES
                    else
                        AppCompatDelegate.MODE_NIGHT_NO
                )
            }

        mainViewModel.getDigimon()

        binding.apply {
            fabLove.setOnClickListener {
                startActivity(Intent(this@MainActivity, Class.forName(FAV_PACKAGE_NAME)))
            }

            loadingErrorReloadLayout.reloadBtn.setOnClickListener {
                mainViewModel.getDigimon()
            }
        }

        mainViewModel.listOfDigimon.observe(this){ digimon ->
            if(digimon != null){
                when (digimon) {
                    is Resource.Loading -> binding.loadingErrorReloadLayout.progressBar.visibility = View.VISIBLE
                    is Resource.Success -> {
                        binding.loadingErrorReloadLayout.progressBar.visibility = View.GONE
                        adapter.setData(digimon.data)
                    }

                    is Resource.Error -> {
                        binding.loadingErrorReloadLayout.progressBar.visibility = View.GONE
                        Timber.d("data: ${digimon.message}")
                    }
                }
            }
            adapter.onItemClick = { item ->
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_DATA, item)
                startActivity(intent)
            }
            binding.rvDigimon.adapter = adapter
        }

        addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.setting -> {
                        startActivity(Intent(this@MainActivity, SettingActivity::class.java))
                        false
                    }

                    else -> true
                }
            }
        }, this, Lifecycle.State.RESUMED)
    }

    companion object{
        const val FAV_PACKAGE_NAME = "id.zoneordering.favorite.FavoriteActivity"
    }
}