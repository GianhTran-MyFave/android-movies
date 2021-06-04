package com.madison.client.movies.data.model

enum class Category(val category: String) {
    RELEASE_DATE("primary_release_date.desc"),
    ALPHABETICAL(""),
    RATING("vote_average.desc")
}