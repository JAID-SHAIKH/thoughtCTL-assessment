package com.example.thoughtctl
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

class ImgurAdapter(private val imgurImages: List<ImgurImage>) : RecyclerView.Adapter<ImgurViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImgurViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_imgur, parent, false)
        return ImgurViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImgurViewHolder, position: Int) {
        val imgurImage = imgurImages[position]
        holder.bind(imgurImage)
    }

    override fun getItemCount(): Int {
        return imgurImages.size
    }
}

class ImgurViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
    private val dateTextView: TextView = itemView.findViewById(R.id.dateTextView)
    private val imageCountTextView: TextView = itemView.findViewById(R.id.imageCountTextView)
    private val imageView: ImageView = itemView.findViewById(R.id.imageView)

    fun bind(imgurImage: ImgurImage) {
        titleTextView.text = imgurImage.title
        dateTextView.text = formatDate(imgurImage.datetime)
        imageCountTextView.text = "Image Count: ${imgurImage.images_count}"

        // Assuming the first image is the main image
        if (imgurImage.images.isNotEmpty()) {
            val imageUrl = imgurImage.images[0].link
            // Load the image using your preferred image loading library (Glide, Picasso, etc.)
            // For simplicity, you can use the following (consider using a proper library in production):
            Picasso.get()
                .load(imageUrl)
                .placeholder(R.drawable.ic_launcher_background) // Add a placeholder drawable if needed
                .error(com.google.android.material.R.drawable.mtrl_ic_error) // Add an error drawable if the image fails to load
                .into(imageView)
        }
    }

    private fun formatDate(timestamp: Long): String {
        val sdf = SimpleDateFormat("dd/MM/yy hh:mm a", Locale.getDefault())
        return sdf.format(Date(timestamp * 1000))
    }
}
