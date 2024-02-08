package fr.deuspheara.eshopapp.core.model.auth

import android.util.Patterns

/**
 * _Eshopapp_
 *
 * fr.deuspheara.eshopapp.core.model.auth.Email
 *
 * ### Information
 * - __Author__ Deuspheara
 *
 * ### Description
 * Email
 *
 */
@JvmInline
value class Email(val value: String){
    fun isValid(): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(value).matches()
    }
}