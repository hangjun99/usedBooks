package com.example.booksale

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject


class ItemActivity : RecyclerView.Adapter<ItemActivity.ViewHolder>() {
    private var items = ArrayList<Product>()
    var listener: OnProductItemClicklistener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflate: LayoutInflater = LayoutInflater.from(parent.context)
        val itemView: View = inflate.inflate(R.layout.item_article, parent, false)
        return ViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: Product = items[position]
        holder.setItem(item)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun addItem(item: Product) {
        items.add(item)
    }

    fun getItem(position: Int):Product {
        return items[position]
    }

    fun onItemClick(holder: ViewHolder?, view: View?, position: Int) {
        if (listener != null) {
            listener!!.onItemClick(holder, view, position)
        }
    }

    fun setOnItemClickListener(listener: OnProductItemClicklistener?) {
        this.listener = listener
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        private val writerText: TextView = itemView.findViewById(R.id.writerText)
        private val publisherText: TextView = itemView.findViewById(R.id.publisherText)
        private val priceTextView: TextView = itemView.findViewById(R.id.priceTextView)
        private val uploaderTextView: TextView = itemView.findViewById(R.id.uploaderTextView)
        val chatBtn: Button = itemView.findViewById(R.id.chatBtn)
        lateinit var detailBook: Array<String>
        fun setItem(item: Product){
            titleTextView.text = item.bookName
            writerText.text = item.author
            publisherText.text = item.publisher
            priceTextView.text = item.hopePrice
            detailBook = item.detailBook
        }

        init {
            chatBtn.setOnClickListener {
                Toast.makeText(itemView.context, "성공", Toast.LENGTH_SHORT).show()
                val intent = Intent(itemView.context, DetailBookDataActivity::class.java)
                intent.putExtra("detailBook", detailBook)
                itemView.context.startActivity(intent)
            }
        }


    }
    data class Product(
        val bookName: String,
        val author: String,
        val publisher: String,
        val hopePrice: String,
        val detailBook: Array<String>
    )
    open class OnProductItemClicklistener {
        open fun onItemClick(holder: ViewHolder?, view: View?, position: Int) {}

    }
}
