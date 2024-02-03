package fr.deuspheara.eshopapp.data.network.json

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * _Eshopapp_
 *
 * fr.deuspheara.eshopapp.data.network.json.GsonExtension
 *
 * ### Information
 * - __Author__ Deuspheara
 *
 * ### Description
 * Gson extensions
 *
 */

inline fun <reified T> Gson.parse(input: String): T {
    return this.fromJson(input, object : TypeToken<T>() {}.type)
}
