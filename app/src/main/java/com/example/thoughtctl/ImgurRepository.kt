import com.example.thoughtctl.ImgurImage
import retrofit2.HttpException
import java.io.IOException

class ImgurRepository(private val imgurService: ImgurService) {

    suspend fun searchImages(query: String): List<ImgurImage> {
        try {
            val response = imgurService.api.searchImages(query)
            return response.data
        } catch (e: HttpException) {
            // Handle HTTP errors
            e.printStackTrace()
        } catch (e: IOException) {
            // Handle IO errors
            e.printStackTrace()
        }

        return emptyList()
    }
}
