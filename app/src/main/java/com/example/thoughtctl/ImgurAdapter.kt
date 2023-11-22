import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.thoughtctl.ImgurImage
import com.example.thoughtctl.R
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*
import android.content.Context


class ImgurAdapter(private var imgurImages: List<ImgurImage>, private val viewType: Int, context: Context) : RecyclerView.Adapter<ImgurAdapter.ImgurViewHolder>() {
     val applicationContext = context.applicationContext

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImgurViewHolder {
        val layoutResId = if (viewType == VIEW_TYPE_GRID) {
            R.layout.item_imgur_grid
        } else {
            R.layout.item_imgur
        }

        val view = LayoutInflater.from(parent.context).inflate(layoutResId, parent, false)
        return ImgurViewHolder(view, viewType)
    }

    override fun onBindViewHolder(holder: ImgurViewHolder, position: Int) {
        val imgurImage = imgurImages[position]
        holder.bind(imgurImage)
    }

    override fun getItemCount(): Int {
        return imgurImages.size
    }

    override fun getItemViewType(position: Int): Int {
        return viewType
    }

    fun setImgurImages(images: List<ImgurImage>) {
        imgurImages = images
        notifyDataSetChanged()
    }

    inner class ImgurViewHolder(itemView: View, viewType: Int) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        private val dateTextView: TextView = itemView.findViewById(R.id.dateTextView)
        private val imageCountTextView: TextView = itemView.findViewById(R.id.imageCountTextView)
        private val imageView: ImageView = itemView.findViewById(R.id.imageView)

        init {
            if (viewType == VIEW_TYPE_GRID) {
                val params = itemView.layoutParams as StaggeredGridLayoutManager.LayoutParams
                params.isFullSpan = true
                itemView.layoutParams = params
            }
        }

        fun bind(imgurImage: ImgurImage) {
            titleTextView.text = imgurImage.title
            dateTextView.text = formatDate(imgurImage.datetime)
            imageCountTextView.text = "Image Count: ${imgurImage.images_count}"

            // Load the image using Picasso
            if (imgurImage.images.isNotEmpty()) {
                val imageUrl = imgurImage.images[0].link
                GlobalScope.launch(Dispatchers.Main) {
                    try {
                        val bitmap = withContext(Dispatchers.IO) {
                            Picasso.with(applicationContext)
                                .load(imageUrl)
                                .placeholder(R.drawable.ic_launcher_background)
                                .error(com.google.android.material.R.drawable.mtrl_ic_error)
                                .get()
                        }

                        // Update the UI on the main thread
                        withContext(Dispatchers.Main) {
                            imageView.setImageBitmap(bitmap)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }

        private fun formatDate(timestamp: Long): String {
            val sdf = SimpleDateFormat("dd/MM/yy hh:mm a", Locale.getDefault())
            return sdf.format(Date(timestamp * 1000))
        }
    }

    companion object {
        const val VIEW_TYPE_LIST = 1
        const val VIEW_TYPE_GRID = 2
    }
}
