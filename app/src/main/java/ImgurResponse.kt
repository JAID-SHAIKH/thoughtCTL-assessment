import com.example.thoughtctl.ImgurImage

data class ImgurResponse(
    val data: List<ImgurImage>,
    // Other relevant fields from the Imgur API response
)