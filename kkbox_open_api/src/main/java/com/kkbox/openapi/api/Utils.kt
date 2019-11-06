package com.kkbox.openapi.api

import com.kkbox.openapi.model.Territory

/**
 * Converts a territory to a string.
 *
 * @param territory the given territory.
 * @return a string.
 */
fun territoryToString(territory: Territory): String =
        when (territory) {
            Territory.TW -> "TW"
            Territory.HK -> "HK"
            Territory.MY -> "MY"
            Territory.SG -> "SG"
            Territory.JP -> "JP"
        }

/**
 * Converts a string to a territory.
 *
 * @param string the given string.
 * @return a territory code.
 */
fun stringToTerritory(string: String): Territory =
        when (string) {
            "JP" -> Territory.JP
            "HK" -> Territory.HK
            "MY" -> Territory.MY
            "SG" -> Territory.SG
            else -> Territory.TW
        }

/**
 * Converts a list of string to a list of territory.
 */
fun parseTerritory(territories: List<String>): List<Territory> = territories.map {
    stringToTerritory(it)
}
