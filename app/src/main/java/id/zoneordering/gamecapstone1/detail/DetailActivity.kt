package id.zoneordering.gamecapstone1.detail

import android.graphics.PorterDuff
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import id.zoneordering.core.domain.model.Digimon
import id.zoneordering.core.utils.emptyDigimon
import id.zoneordering.core.utils.parcelable
import id.zoneordering.gamecapstone1.R
import id.zoneordering.gamecapstone1.databinding.ActivityDetailBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {

    private val detailViewModel: DetailViewModel by viewModel()

    private val binding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }

    private lateinit var detailDigimon: Digimon

    companion object {
        const val EXTRA_DATA = "extra_data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        detailDigimon = intent.parcelable<Digimon>(EXTRA_DATA) ?: emptyDigimon

        title = "${detailDigimon.name} - ${detailDigimon.level}"

        detailViewModel.getDigimonByName(detailDigimon.name)

        with(binding) {
            Glide.with(this@DetailActivity)
                .load(detailDigimon.photo)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .into(photoDetail)

            namaValue.text = detailDigimon.name
            levelValue.text = detailDigimon.level

            var isFav = detailDigimon.isFavorite

            fabLove.setOnClickListener {
                isFav = !isFav
                detailViewModel.setFavoriteDigimon(detailDigimon, isFav)
            }
        }

        detailViewModel.digimon.observe(this){
            if(it != null){
                setFavFab(it.isFavorite)
            }
        }
    }

    private fun setFavFab(isFav: Boolean){
        binding.fabLove.setColorFilter(
            ContextCompat.getColor(
                this@DetailActivity,
                if (isFav)
                    R.color.red_700
                else
                    R.color.white
            ), PorterDuff.Mode.SRC_IN
        )
    }
}