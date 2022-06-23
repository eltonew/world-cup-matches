package my.projetc.worldcupmatches.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import my.projetc.worldcupmatches.databinding.MatchItemBinding
import my.projetc.worldcupmatches.domain.Match
import my.projetc.worldcupmatches.ui.DetailActivity


class MatchesAdapter(matchesList: List<Match>) :
    RecyclerView.Adapter<MatchesAdapter.ViewHolder>() {

    var matchesList: List<Match>
    init {
        this.matchesList = matchesList
    }

    inner class ViewHolder(binding: MatchItemBinding) : RecyclerView.ViewHolder(binding.root) {
        var binding: MatchItemBinding

        init {
            this.binding = binding
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = MatchItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context = holder.itemView.context
        val match = matchesList[position]
        //- Adapta os dados da partida (recuperada da API) para o nosso layout
        //-------   HOMETEAM   -------//
        holder.binding.txtViewHomeTeamName.text = match.homeTeam.name
        Glide.with(context).load(match.homeTeam.image)
            .circleCrop()
            .into(holder.binding.imgViewHomeTeam)

        if (match.homeTeam.score != null)
            holder.binding.txtViewHomeTeamScore.text = match.homeTeam.score.toString()

        //-------   AWAYTEAM   -------//
        holder.binding.txtViewAwayTeamName.text = match.awayTeam.name
        Glide.with(context).load(match.awayTeam.image)
            .circleCrop()
            .into(holder.binding.imgViewAwayTeam)

        if (match.awayTeam.score != null)
            holder.binding.txtViewAwayTeamScore.text = match.awayTeam.score.toString()
        holder.itemView.setOnClickListener{
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(DetailActivity.Extras.MATCH, match)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return matchesList.size
    }


}