package fr.deuspheara.eshopapp.core.model.auth

import fr.deuspheara.eshopapp.data.network.model.auth.UserRemote

/**
 * _Eshopapp_
 *
 * fr.deuspheara.eshopapp.core.model.auth.UserFullModel
 *
 * ### Information
 * - __Author__ Deuspheara
 *
 * ### Description
 *
 *
 */
data class UserFullModel(
    val id: String,
    val username: String?,
    val email: String?,
    val zipCode: String?,
    val address: String?,
    val city: String?,
    val country: String?,
    val firstName: String?,
    val lastName: String?
){
    constructor(user: UserRemote): this(
        user.id,
        user.username,
        user.email,
        user.zipCode.toString(),
        user.address,
        user.city,
        user.country,
        user.firstName,
        user.lastName
    )
}