package fr.deuspheara.eshopapp.data.network.model

import com.google.gson.annotations.SerializedName

/**
 * _Eshopapp_
 *
 * fr.deuspheara.eshopapp.data.network.model.ResponseContainer
 *
 * ### Information
 * - __Author__ Deuspheara
 *
 * ### Description
 * Response container
 *
 */

data class ResponseContainer<out Remote>(
    @SerializedName("results")
    val results: List<Remote>
)