package com.madison.client.movies.data.repository.remote.api.response

import com.google.gson.annotations.Expose

data class ServerErrorResponse(
    @Expose
    val ok: Boolean?,
    @Expose
    val message: String?
)
