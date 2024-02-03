package fr.deuspheara.eshopapp.core.model.auth

/**
 * _Eshopapp_
 *
 * fr.deuspheara.eshopapp.core.model.auth.Password
 *
 * ### Information
 * - __Author__ Deuspheara
 *
 * ### Description
 * ### Description
 * Password Implementation.
 *
 * This class is a value type that wraps around a string meant to represent a password.
 * It provides a utility to check if the password meets a basic 'strong' criteria.
 *
 * A strong password is defined as:
 * 1. At least 8 characters long.
 * 2. Contains at least one lowercase letter.
 * 3. Contains at least one uppercase letter.
 * 4. Contains at least one number.
 * 5. Contains at least one special character (e.g., !@#$%^&*).
 *
 * ### Example Usage:
 * ```
 * val password = Password("Example1!")
 * if (password.isStrong) {
 *     println("The password is strong.")
 * } else {
 *     println("The password is not strong.")
 * }
 * ```
 */
@JvmInline
value class Password(val value: String) {

    val hasMinimumLength: Boolean
        get() = value.length >= 8

    val hasLowerCase: Boolean
        get() = value.any { it.isLowerCase() }

    val hasUpperCase: Boolean
        get() = value.any { it.isUpperCase() }


    val hasSpecialChar: Boolean
        get() = value.any { it in SPECIAL_CHARS }

    val isStrong: Boolean
        get() = hasMinimumLength && hasLowerCase && hasUpperCase && hasSpecialChar


    companion object {
        private val SPECIAL_CHARS = setOf(
            '!',
            '@',
            '#',
            '$',
            '%',
            '^',
            '&',
            '*',
            '(',
            ')',
            '-',
            '+',
            '=',
            '<',
            '>',
            '?',
            '/',
            '\\',
            '|',
            '[',
            ']',
            '{',
            '}',
            '~',
            '`',
            ':',
            ';',
            '"',
            '\''
        )
    }
}