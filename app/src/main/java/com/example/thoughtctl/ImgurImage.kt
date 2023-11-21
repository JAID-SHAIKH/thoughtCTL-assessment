package com.example.thoughtctl


data class ImgurImage(
    val title: String,
    val datetime: Long,
    val images_count: Int,
    val images: List<ImageItem>
)

data class ImageItem(
    val link: String
)