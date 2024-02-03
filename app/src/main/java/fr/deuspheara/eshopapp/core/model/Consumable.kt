package fr.deuspheara.eshopapp.core.model

/**
 * _Eshopapp_
 *
 * fr.deuspheara.eshopapp.core.model.Consumable
 *
 * ### Information
 * - __Author__ Deuspheara
 *
 * ### Description
 * Consumable Implementation.
 *
 * This abstract class serves as a foundational type designed to ensure that a specific instance can be consumed only once. Once an instance has been consumed, any further attempts to consume it will return null.
 *
 * The primary purpose is to provide a mechanism for consuming the reference of any type, ensuring it can't be reused mistakenly. Subclasses can also define custom behavior when the object is consumed by overriding the `onConsume` method.
 *
 * ### Example Usage:
 * ```
 * class Apple : Consumable() {
 *     override fun onConsume() {
 *         println("Apple was eaten!")
 *     }
 * }
 *
 * val apple = Apple()
 * apple.consume()  // prints "Apple was eaten!"
 * apple.consume()  // Does nothing since it's already consumed.
 * ```
 */
abstract class Consumable {

    private var isConsumed = false

    /**
     * Consumes the item if it hasn't been consumed already.
     * Returns the item itself if it was consumed, otherwise returns null.
     */
    fun consumeIfNotAlready(): Consumable? {
        return if (!isConsumed) {
            isConsumed = true
            this
        } else {
            null
        }
    }

    /**
     * This method can be overridden by subclasses to define behavior when the item is consumed.
     */
    open fun onConsume() {
        // default does nothing, subclasses can override
    }
}

inline fun <reified T : Consumable> T.consume(): T? {
    val consumed = this.consumeIfNotAlready()
    if (consumed != null) {
        this.onConsume()
    }
    return consumed as T?
}