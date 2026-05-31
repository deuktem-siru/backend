package com.deuktemsiru.application.port.out

data class PageQuery(val pageNumber: Int, val pageSize: Int, val newestFirst: Boolean = false) {
    init { require(pageNumber >= 0 && pageSize in 1..100) }
}
