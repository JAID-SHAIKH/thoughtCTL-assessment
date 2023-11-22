import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thoughtctl.ImgurImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ImgurViewModel(private val repository: ImgurRepository) : ViewModel() {

    private val _imgurImages = MutableLiveData<List<ImgurImage>>()
    val imgurImages: LiveData<List<ImgurImage>> get() = _imgurImages

    fun searchImages(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val images = repository.searchImages(query)
                _imgurImages.postValue(images)
            } catch (e: Exception) {
                // Handle the exception
                e.printStackTrace()
            }
        }
    }
}
