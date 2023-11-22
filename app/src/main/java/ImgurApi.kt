import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ImgurApi {

    @GET("gallery/search/top")
    suspend fun searchImages(
        @Query("q") query: String,
        @Query("time") time: String = "week"
    ): ImgurResponse
}
