package fr.deuspheara.eshopapp.data.network.model.auth

import com.google.gson.annotations.SerializedName

/**
 * _Eshopapp_
 *
 * fr.deuspheara.eshopapp.data.network.model.auth.UserRemote
 *
 * ### Information
 * - __Author__ Deuspheara
 *
 * ### Description
 *
 *
 */
data class UserRemote(
    /** The user id */
    @SerializedName("id")
    val id: String,

    @SerializedName("username")
    val username: String?,

    @SerializedName("email")
    val email: String?,

    @SerializedName("zip_code")
    val zipCode: Int?,

    @SerializedName("address")
    val address: String?,

    @SerializedName("city")
    val city: String?,

    @SerializedName("country")
    val country: String?,

    @SerializedName("first_name")
    val firstName: String?,

    @SerializedName("last_name")
    val lastName: String?
)