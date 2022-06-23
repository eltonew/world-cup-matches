package my.projetc.worldcupmatches.data

import my.projetc.worldcupmatches.domain.Match
import retrofit2.Call
import retrofit2.http.GET

interface MatchesApi {
    @GET("matches.json")
    fun getMatches() : Call<List<Match>>
}