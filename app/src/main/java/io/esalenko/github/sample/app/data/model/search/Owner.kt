package io.esalenko.github.sample.app.data.model.search

import com.google.gson.annotations.SerializedName

data class Owner(
    @SerializedName("login") val login: String,
    @SerializedName("id") val id: Int,
    @SerializedName("node_id") val node_id: String,
    @SerializedName("avatar_url") val avatar_url: String,
    @SerializedName("gravatar_id") val gravatar_id: String,
    @SerializedName("url") val url: String,
    @SerializedName("received_events_url") val received_events_url: String,
    @SerializedName("type") val type: String
)