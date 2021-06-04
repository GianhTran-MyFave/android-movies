package com.madison.client.movies.data.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class MovieResponse(
    @SerializedName("page")
    @Expose
    val page: Int?,

    @SerializedName("total_results")
    @Expose
    val totalResults: Int?,

    @SerializedName("total_pages")
    @Expose
    val totalPages: Int?,

    @SerializedName("results")
    @Expose
    val results: List<Movie>?
) : Parcelable