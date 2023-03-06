package com.example.fintech2023chupin.ui.movieslist.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fintech2023chupin.R
import com.example.fintech2023chupin.data.model.FilmTopResponse_films

class MoviesAdapter() : RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    private var moviesList: List<FilmTopResponse_films> = emptyList()
    private var listener: OnItemClickListener? = null

    fun setData(_moviesList: List<FilmTopResponse_films>) {
        moviesList = _moviesList
        notifyDataSetChanged()
    }

    fun setOnClickListener(_listener: OnItemClickListener) {
        listener = _listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.sample_card, parent, false)
        )
    }

    override fun getItemCount(): Int = moviesList.count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(model = moviesList[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var itemPoster: ImageView = itemView.findViewById(R.id.imPosterInCard)
        private var itemTitle: TextView = itemView.findViewById(R.id.tvTitleInCard)
        private var itemLittleInfo: TextView = itemView.findViewById(R.id.tvLittleInfoInCard)
        private var itemStar: ImageView = itemView.findViewById(R.id.ivInFavorite)

        fun bind(model: FilmTopResponse_films) {
            itemTitle.text = model.title
            itemLittleInfo.text = "${model.genres[0].genre}(${model.year})"
            if (model.stateInFavorite) {
                itemStar.setBackgroundResource(R.drawable.ic_star)
            } else {
                itemStar.setBackgroundResource(R.drawable.ic_star_border)
            }
            Glide.with(itemView.context)
                .load(model.poster)
                .into(itemPoster)

            itemView.setOnLongClickListener {
                listener?.onLongClick(model)
                if (!model.stateInFavorite) {
                    itemStar.setBackgroundResource(R.drawable.ic_star)
                } else {
                    itemStar.setBackgroundResource(R.drawable.ic_star_border)
                }
                true
            }

            itemView.setOnClickListener {
                listener?.onClick(model.id)
            }
        }

    }

    interface OnItemClickListener {
        fun onClick(modelId: Int)
        fun onLongClick(model: FilmTopResponse_films)
    }
}