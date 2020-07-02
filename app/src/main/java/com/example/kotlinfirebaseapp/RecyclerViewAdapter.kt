package com.example.kotlinfirebaseapp
import android.util.Log
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.recycler_view_layout.view.*

class RecyclerViewAdapter(private val repoLists:ArrayList<RepoModel.Data>, private val activity: UserRepoActivity):
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.recycler_view_layout,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = repoLists.size;


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind()
    }

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        lateinit var model: RepoModel.Data
        fun onBind() {
            model = repoLists[adapterPosition]
            Glide.with(activity).load(model.owner.avatarUrl).into(
                itemView.ownerImageView
            )
            itemView.repoNameTextView.text = model.name
            itemView.stargazersCountTextView.text = "stars: " + model.stargazersCount.toString()


        }
    }
}