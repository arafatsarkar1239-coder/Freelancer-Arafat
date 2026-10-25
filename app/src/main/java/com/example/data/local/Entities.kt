package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.example.data.model.*

class StringListConverters {
    @TypeConverter
    fun fromString(value: String?): List<String> {
        if (value.isNullOrBlank()) return emptyList()
        return value.split("||").map { it.trim() }.filter { it.isNotEmpty() }
    }

    @TypeConverter
    fun toString(list: List<String>?): String {
        return list?.joinToString("||") ?: ""
    }
}

@Entity(tableName = "profile_table")
data class ProfileEntity(
    @PrimaryKey val id: Int = 1,
    val name: String,
    val brandName: String,
    val title: String,
    val location: String,
    val portfolioUrl: String,
    val linkedInUrl: String,
    val email: String,
    val availabilityStatus: String,
    val headline: String,
    val subheadline: String,
    val aboutBio: String,
    val approachText: String
) {
    fun toModel(): Profile = Profile(
        id = id,
        name = name,
        brandName = brandName,
        title = title,
        location = location,
        portfolioUrl = portfolioUrl,
        linkedInUrl = linkedInUrl,
        email = email,
        availabilityStatus = availabilityStatus,
        headline = headline,
        subheadline = subheadline,
        aboutBio = aboutBio,
        approachText = approachText
    )

    companion object {
        fun fromModel(p: Profile): ProfileEntity = ProfileEntity(
            id = p.id,
            name = p.name,
            brandName = p.brandName,
            title = p.title,
            location = p.location,
            portfolioUrl = p.portfolioUrl,
            linkedInUrl = p.linkedInUrl,
            email = p.email,
            availabilityStatus = p.availabilityStatus,
            headline = p.headline,
            subheadline = p.subheadline,
            aboutBio = p.aboutBio,
            approachText = p.approachText
        )
    }
}

@Entity(tableName = "services_table")
data class ServiceEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val category: String,
    val description: String,
    val deliverables: List<String>,
    val iconKey: String,
    val isActive: Boolean = true
) {
    fun toModel(): ServiceItem = ServiceItem(
        id = id,
        title = title,
        category = category,
        description = description,
        deliverables = deliverables,
        iconKey = iconKey,
        isActive = isActive
    )
}

@Entity(tableName = "skills_table")
data class SkillEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val category: String,
    val proficiencyLabel: String
) {
    fun toModel(): SkillItem = SkillItem(
        id = id,
        name = name,
        category = category,
        proficiencyLabel = proficiencyLabel
    )
}

@Entity(tableName = "projects_table")
data class ProjectEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val category: String,
    val shortDescription: String,
    val clientName: String,
    val objectives: String,
    val workPerformed: String,
    val results: String,
    val toolsUsed: List<String>,
    val projectUrl: String,
    val completionDate: String,
    val isFeatured: Boolean = false
) {
    fun toModel(): PortfolioProject = PortfolioProject(
        id = id,
        title = title,
        category = category,
        shortDescription = shortDescription,
        clientName = clientName,
        objectives = objectives,
        workPerformed = workPerformed,
        results = results,
        toolsUsed = toolsUsed,
        projectUrl = projectUrl,
        completionDate = completionDate,
        isFeatured = isFeatured
    )
}

@Entity(tableName = "experiences_table")
data class ExperienceEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val organization: String,
    val position: String,
    val period: String,
    val roleType: String,
    val isEducationOrCert: Boolean = false,
    val description: String,
    val keyContributions: List<String>
) {
    fun toModel(): ExperienceItem = ExperienceItem(
        id = id,
        organization = organization,
        position = position,
        period = period,
        roleType = roleType,
        isEducationOrCert = isEducationOrCert,
        description = description,
        keyContributions = keyContributions
    )
}

@Entity(tableName = "testimonials_table")
data class TestimonialEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val clientName: String,
    val clientDesignation: String,
    val companyName: String,
    val feedback: String,
    val relatedProject: String
) {
    fun toModel(): TestimonialItem = TestimonialItem(
        id = id,
        clientName = clientName,
        clientDesignation = clientDesignation,
        companyName = companyName,
        feedback = feedback,
        relatedProject = relatedProject
    )
}

@Entity(tableName = "inquiries_table")
data class InquiryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val clientName: String,
    val clientEmail: String,
    val companyName: String,
    val requestedService: String,
    val estimatedBudget: String,
    val projectDetails: String,
    val preferredContactMethod: String,
    val timestamp: Long = System.currentTimeMillis(),
    val status: String = "New",
    val adminNotes: String = ""
) {
    fun toModel(): ClientInquiry = ClientInquiry(
        id = id,
        clientName = clientName,
        clientEmail = clientEmail,
        companyName = companyName,
        requestedService = requestedService,
        estimatedBudget = estimatedBudget,
        projectDetails = projectDetails,
        preferredContactMethod = preferredContactMethod,
        timestamp = timestamp,
        status = status,
        adminNotes = adminNotes
    )
}

@Entity(tableName = "blog_posts_table")
data class BlogPostEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val category: String,
    val readingTime: String,
    val publishDate: String,
    val summary: String,
    val content: String,
    val tags: List<String>,
    val isPublished: Boolean = true
) {
    fun toModel(): BlogPost = BlogPost(
        id = id,
        title = title,
        category = category,
        readingTime = readingTime,
        publishDate = publishDate,
        summary = summary,
        content = content,
        tags = tags,
        isPublished = isPublished
    )
}
