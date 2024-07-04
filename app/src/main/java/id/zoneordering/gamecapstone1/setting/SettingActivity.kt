package id.zoneordering.gamecapstone1.setting

import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import id.zoneordering.gamecapstone1.databinding.ActivitySettingBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingActivity : AppCompatActivity() {

    private val settingViewModel: SettingViewModel by viewModel()

    private val binding by lazy {
        ActivitySettingBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.apply {
            settingViewModel.getThemeSettings()
                .observe(this@SettingActivity) { isDarkModeActive: Boolean ->
                    AppCompatDelegate.setDefaultNightMode(
                        if (isDarkModeActive)
                            AppCompatDelegate.MODE_NIGHT_YES
                        else
                            AppCompatDelegate.MODE_NIGHT_NO
                    )
                    switchTheme.isChecked = isDarkModeActive
                }

            switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
                settingViewModel.saveThemeSetting(isChecked)
            }
        }
    }
}