package my.projetc.worldcupmatches.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import my.projetc.worldcupmatches.R
import my.projetc.worldcupmatches.data.MatchesApi
import my.projetc.worldcupmatches.databinding.ActivityMainBinding
import my.projetc.worldcupmatches.domain.Match
import my.projetc.worldcupmatches.ui.adapter.MatchesAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var matchesApi: MatchesApi
    private lateinit var binding: ActivityMainBinding
    private var matchesAdapter = MatchesAdapter(emptyList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupHttpClient()
        setupMatchesList()
        setupMatchesRefresh()
        setupFloatingActionButton()
    }

    private fun setupHttpClient() {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://eltonew.github.io/matches-simulator-api/")
            .build()
        matchesApi = retrofit.create(MatchesApi::class.java)
    }

    private fun setupMatchesList() {
        binding.recyclerViewMatches.setHasFixedSize(true)
        binding.recyclerViewMatches.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewMatches.adapter = matchesAdapter
        findMatchesFromApi()
    }

    private fun setupFloatingActionButton() {
        binding.fActionBtnSimulate.setOnClickListener{view: View ->
            view.animate().rotationBy(360f).setDuration(500)
                .setListener(object  : AnimatorListenerAdapter(){
                    override fun onAnimationEnd(animation: Animator?) {
                        val random = Random()
                        for (i in 0 until matchesAdapter.itemCount){
                            val match = matchesAdapter.matchesList[i]
                            match.homeTeam.score = random.nextInt(match.homeTeam.stars + 1)
                            match.awayTeam.score = random.nextInt(match.awayTeam.stars + 1)
                            matchesAdapter.notifyItemChanged(i)
                        }
                    }
                })
//            throw RuntimeException("Teste Crashlytics")

        }
    }

    private fun setupMatchesRefresh() {
       binding.sRefreshLayMatchesUpdate.setOnRefreshListener(this::findMatchesFromApi)
    }

    private fun showErrorMassage() {
        Snackbar.make(binding.fActionBtnSimulate, R.string.error_api, Snackbar.LENGTH_LONG).show()
    }

    private fun findMatchesFromApi() {
        binding.sRefreshLayMatchesUpdate.isRefreshing = true

        matchesApi.getMatches().enqueue(object : Callback<List<Match>> {

            override fun onResponse(call: Call<List<Match>>, response: Response<List<Match>>) {
                if (response.isSuccessful) {
                    val matchesList = response.body()!!
                    matchesAdapter = MatchesAdapter(matchesList)
                    binding.recyclerViewMatches.adapter = matchesAdapter
                } else {
                    showErrorMassage()
                }
                binding.sRefreshLayMatchesUpdate.isRefreshing = false
            }
            override fun onFailure(call: Call<List<Match>>, t: Throwable) {
                showErrorMassage()
                binding.sRefreshLayMatchesUpdate.isRefreshing = false
            }

        })
    }
}
