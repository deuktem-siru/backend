package com.deuktemsiru

import com.deuktemsiru.adapter.out.security.JwtAuthenticationFilter
import com.deuktemsiru.adapter.out.security.JwtService
import com.deuktemsiru.configuration.SecurityConfig
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.servlet.config.annotation.EnableWebMvc

@ExtendWith(SpringExtension::class)
@WebAppConfiguration
@ContextConfiguration(classes = [SecurityConfig::class, AuthenticationHttpTest.Config::class])
class AuthenticationHttpTest {
    @Configuration
    @EnableWebMvc
    class Config {
        @Bean
        fun jwtFilter() = JwtAuthenticationFilter(mock(JwtService::class.java))
    }

    @Autowired
    private lateinit var context: WebApplicationContext

    @Test
    fun `missing credentials return an authentication challenge instead of forbidden`() {
        val mvc = MockMvcBuilders.webAppContextSetup(context).apply<org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder>(springSecurity()).build()
        mvc.perform(get("/api/v1/orders"))
            .andExpect(status().isUnauthorized)
            .andExpect(header().string("WWW-Authenticate", "Bearer"))
    }
}
