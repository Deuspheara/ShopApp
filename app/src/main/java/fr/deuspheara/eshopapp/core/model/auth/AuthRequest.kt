package fr.deuspheara.eshopapp.core.model.auth

/**
 * _Eshopapp_
 *
 * fr.deuspheara.eshopapp.core.model.auth.AuthRequest
 *
 * ### Information
 * - __Author__ Deuspheara
 *
 * ### Description
 * Auth request
 *
 */
data class AuthRequest(
    val username: String,
    val password: String
)