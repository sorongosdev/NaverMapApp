package com.sorongos.navermapapp

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**서버에서 받아와서 보여주기 위한 일종의 자료형
 * moshi로 매핑, json으로 만들어야 파싱해서 넣어줄 수 있음
 * gson의 SerializedName처럼*/
@JsonClass(generateAdapter = true)
data class SearchResult(
    @field:Json(name = "items") val items: List<SearchItem>
)

@JsonClass(generateAdapter = true)
data class SearchItem(
    @field:Json(name = "title") val title: String,
    @field:Json(name = "link") val link: String,
    @field:Json(name = "category") val category: String, // 한일중양
    @field:Json(name = "roadAddress") val roadAddress: String,
    @field:Json(name = "mapx") val mapx: Int,
    @field:Json(name = "mapy") val mapy: Int,
)
