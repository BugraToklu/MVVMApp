package com.example.mvvmnewsapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mvvmnewsapp.R
import com.example.mvvmnewsapp.databinding.ItemArticlePreviewBinding
import com.example.mvvmnewsapp.models.Article


class NewsAdapter : RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {

    private lateinit var binding: ItemArticlePreviewBinding

    inner class ArticleViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)

    private val differCallBack = object : DiffUtil.ItemCallback<Article>(){
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_article_preview, parent,false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener : ((Article) -> Unit)? = null

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this).load(article.urlToImage).into(binding.ivArticleImage)
            binding.tvSource.text = article.source?.name
            binding.tvTitle.text = article.title
            binding.tvDescription.text = article.description
            binding.tvPublishedAt.text = article.publishedAt

            setOnClickListener {
                onItemClickListener?.let { it(article) }
            }
        }
    }


    fun setOnItemClickListener(listener : (Article) -> Unit){
        onItemClickListener = listener
    }
}
