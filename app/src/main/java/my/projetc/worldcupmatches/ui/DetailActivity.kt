package my.projetc.worldcupmatches.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import my.projetc.worldcupmatches.databinding.ActivityDetailBinding
import my.projetc.worldcupmatches.domain.Match

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    object Extras {
        const val MATCH = "EXTRA_MATCH"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        loadMatchFromExtra()
    }

    private fun loadMatchFromExtra() {
        intent?.extras?.getParcelable<Match>(Extras.MATCH)?.let {
            Glide.with(this).load(it.place.image).into(binding.ivPlace)
            supportActionBar?.title = it.place.name
            binding.tvDescription.text = it.description

            Glide.with(this).load(it.homeTeam.image).into(binding.ivHomeTeam)

            binding.tvHomeTeamName.text = it.homeTeam.name

            binding.rbHomeTeamStars.rating = it.homeTeam.stars.toFloat()

            if (it.homeTeam.score != null){
                binding.tvHomeTeamScore.text = it.homeTeam.score.toString()
            }

            //------------------------------------------------------------------------------//

            Glide.with(this).load(it.awayTeam.image).into(binding.ivAwayTeam)

            binding.tvAwayTeamName.text = it.awayTeam.name

            binding.rbAwayTeamStars.rating = it.awayTeam.stars.toFloat()


           if (it.awayTeam.score != null){
                binding.tvAwayTeamScore.text = it.awayTeam.score.toString()
            }

        }
    }
}