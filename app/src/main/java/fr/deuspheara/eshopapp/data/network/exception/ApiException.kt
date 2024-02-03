package fr.deuspheara.eshopapp.data.network.exception

/**
 * _Eshopapp_
 *
 * fr.deuspheara.eshopapp.data.network.exception.ApiException
 *
 * ### Information
 * - __Author__ Deuspheara
 *
 * ### Description
 * Api exception
 *
 */
data class ApiException(
    val code: Int,
    val error: String?,
) : Exception(error)