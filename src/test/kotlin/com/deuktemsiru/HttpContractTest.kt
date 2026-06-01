package com.deuktemsiru

import com.deuktemsiru.configuration.GlobalExceptionHandler
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.bind.annotation.*

class HttpContractTest {
    @RestController
    class Probe {
        @GetMapping("/probe/{id}")
        fun get(@PathVariable id: Long) = mapOf("id" to id)

        @PostMapping("/probe", consumes = [MediaType.APPLICATION_JSON_VALUE])
        fun create(@RequestBody body: Map<String, String>) = body
    }

    private val mvc = MockMvcBuilders.standaloneSetup(Probe())
        .setControllerAdvice(GlobalExceptionHandler()).build()

    @Test
    fun `HTTP client errors keep their status and response envelope`() {
        mvc.perform(delete("/probe/1")).andExpect(status().isMethodNotAllowed)
            .andExpect(header().string("Allow", org.hamcrest.Matchers.containsString("GET")))
            .andExpect(jsonPath("$.code").value(405))
        mvc.perform(post("/probe").contentType(MediaType.TEXT_PLAIN).content("{}"))
            .andExpect(status().isUnsupportedMediaType).andExpect(jsonPath("$.code").value(415))
        mvc.perform(post("/probe").contentType(MediaType.APPLICATION_JSON).content("{"))
            .andExpect(status().isBadRequest).andExpect(jsonPath("$.code").value(400))
        mvc.perform(get("/probe/not-a-number"))
            .andExpect(status().isBadRequest).andExpect(jsonPath("$.code").value(400))
    }
}
