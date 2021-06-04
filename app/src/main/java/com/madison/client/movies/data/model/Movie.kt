package com.madison.client.movies.data.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    @SerializedName("vote_count") @Expose val voteCount: Int?,

    @SerializedName("id") @Expose val id: Int?,

    @SerializedName("video") @Expose val video: Boolean?,

    @SerializedName("vote_average") @Expose val voteAverage: Float?,

    @SerializedName("title") @Expose val title: String?,

    @SerializedName("popularity") @Expose val popularity: Float?,

    @SerializedName("poster_path") @Expose val posterPath: String?,

    @SerializedName("original_language") @Expose val originalLanguage: String?,

    @SerializedName("original_title") @Expose val originalTitle: String?,

    @SerializedName("genre_ids") @Expose val genreIds: List<Int>?,

    @SerializedName("backdrop_path") @Expose val backdropPath: String?,

    @SerializedName("adult") @Expose val adult: Boolean?,

    @SerializedName("overview") @Expose val overview: String?,

    @SerializedName("release_date") @Expose val releaseDate: String?,

    @SerializedName("runtime") @Expose val runtime: Int?,

    @SerializedName("genres") @Expose val genres: List<Genres>?
) : Parcelable {

    companion object {
        fun newInstance(voteAverage: Float? = null, releaseDate: String? = null): Movie {
            return Movie(
                null,
                null,
                null,
                voteAverage,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                releaseDate,
                null,
                null
            )
        }
    }
}
