package com.example

import com.example.data.repository.DefaultData
import org.junit.Assert.*
import org.junit.Test

class ExampleUnitTest {
    @Test
    fun verifyDefaultBrandProfile() {
        val profile = DefaultData.initialProfile
        assertEquals("Freelancer Arafat", profile.brandName)
        assertEquals("Md. Arafat Sarkar Niloy", profile.name)
        assertEquals("Bangladesh", profile.location)
    }

    @Test
    fun verifyCoreServicesList() {
        val services = DefaultData.initialServices
        assertTrue("Must have at least 7 core services", services.size >= 7)
        assertTrue(services.any { it.title.contains("Lead Generation") })
        assertTrue(services.any { it.title.contains("SEO") })
        assertTrue(services.any { it.title.contains("Google Ads") })
    }
}
