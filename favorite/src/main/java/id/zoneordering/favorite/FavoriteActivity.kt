package id.zoneordering.favorite

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import id.zoneordering.core.ui.DigimonAdapter
import id.zoneordering.favorite.databinding.ActivityFavoriteBinding
import id.zoneordering.favorite.di.favoriteModule
import id.zoneordering.gamecapstone1.detail.DetailActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class FavoriteActivity : AppCompatActivity() {

    private val adapter by lazy {
        DigimonAdapter()
    }

    private val favoriteViewModel: FavoriteViewModel by viewModel()

    private val binding by lazy {
        ActivityFavoriteBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        loadKoinModules(favoriteModule)

        binding.rvDigimon.adapter = adapter

        favoriteViewModel.favoriteDigimon.observe(this) { digimon ->

            binding.emptyDataLayout.root.visibility = if (digimon.isEmpty())
                View.VISIBLE else View.GONE

            adapter.apply {
                setData(digimon)
                onItemClick = { item ->
                    val intent = Intent(this@FavoriteActivity, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_DATA, item)
                    startActivity(intent)
                }
            }.also {
                binding.rvDigimon.adapter = it
            }
        }
    }
}