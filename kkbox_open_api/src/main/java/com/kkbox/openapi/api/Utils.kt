package com.kkbox.openapi.api

import com.kkbox.openapi.model.Territory

fun parseTerritory(territories: List<String>): List<Territory> {
  return territories.map {
    when (it) {
      "JP" -> Territory.JP
      "HK" -> Territory.HK
      "MY" -> Territory.MY
      "SG" -> Territory.SG
      else -> Territory.TW
    }
  }
}