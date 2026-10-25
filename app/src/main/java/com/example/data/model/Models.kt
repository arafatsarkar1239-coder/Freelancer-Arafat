package com.example.data.model

data class Profile(
    val id: Int = 1,
    val name: String = "Md. Arafat Sarkar Niloy",
    val brandName: String = "Freelancer Arafat",
    val title: String = "Digital Marketing Specialist & Lead Generation Expert",
    val location: String = "Bangladesh",
    val portfolioUrl: String = "https://arafatsarkarniloy.blogspot.com",
    val linkedInUrl: String = "https://www.linkedin.com/in/arafat-sarkar-niloy-374160428/",
    val email: String = "arafatsarkar1239@gmail.com",
    val availabilityStatus: String = "Available for New Projects",
    val headline: String = "Grow Your Business with Smart Digital Marketing",
    val subheadline: String = "I'm Md. Arafat Sarkar Niloy, a Digital Marketing Specialist & Lead Generation Expert helping businesses strengthen their online presence, reach the right audience, and generate potential customers.",
    val aboutBio: String = "I am a dedicated Digital Marketing Specialist and Lead Generation Expert based in Bangladesh. My focus is on helping businesses build dependable, predictable online growth engines. I combine deep audience research, targeted B2B/B2C prospect list building, data verification, technical and on-page SEO, and high-ROI advertising campaigns across Google and Meta. My goal is to deliver measurable business impact rather than vanity metrics.",
    val approachText: String = "My approach centers on precision and authenticity. Every campaign begins with in-depth industry and competitor research, followed by pinpoint audience profiling. Whether executing a targeted outreach pipeline or optimizing search visibility, I ensure every touchpoint is designed to convert visitors into loyal clients."
)

data class ServiceItem(
    val id: Long = 0,
    val title: String,
    val category: String,
    val description: String,
    val deliverables: List<String>,
    val iconKey: String,
    val isActive: Boolean = true
)

data class SkillItem(
    val id: Long = 0,
    val name: String,
    val category: String, // "Digital Marketing", "Lead Generation", "SEO", "Advertising & Analytics"
    val proficiencyLabel: String = "Specialist"
)

data class PortfolioProject(
    val id: Long = 0,
    val title: String,
    val category: String, // "Lead Generation", "SEO", "Digital Marketing", "Social Media Marketing", "Paid Advertising", "Website Solutions"
    val shortDescription: String,
    val clientName: String,
    val objectives: String,
    val workPerformed: String,
    val results: String,
    val toolsUsed: List<String>,
    val projectUrl: String,
    val completionDate: String,
    val isFeatured: Boolean = false
)

data class ExperienceItem(
    val id: Long = 0,
    val organization: String,
    val position: String,
    val period: String,
    val roleType: String, // "Full-time", "Freelance / Remote", "Contract", "Certification"
    val isEducationOrCert: Boolean = false,
    val description: String,
    val keyContributions: List<String>
)

data class TestimonialItem(
    val id: Long = 0,
    val clientName: String,
    val clientDesignation: String,
    val companyName: String,
    val feedback: String,
    val relatedProject: String
)

data class ClientInquiry(
    val id: Long = 0,
    val clientName: String,
    val clientEmail: String,
    val companyName: String,
    val requestedService: String,
    val estimatedBudget: String,
    val projectDetails: String,
    val preferredContactMethod: String,
    val timestamp: Long = System.currentTimeMillis(),
    val status: String = "New", // "New", "In Progress", "Contacted", "Completed"
    val adminNotes: String = ""
)

data class BlogPost(
    val id: Long = 0,
    val title: String,
    val category: String,
    val readingTime: String,
    val publishDate: String,
    val summary: String,
    val content: String,
    val tags: List<String>,
    val isPublished: Boolean = true
)

data class DashboardAnalytics(
    val totalProjects: Int,
    val activeServices: Int,
    val publishedPosts: Int,
    val totalInquiries: Int,
    val newInquiries: Int,
    val inProgressInquiries: Int,
    val completedInquiries: Int,
    val inquiriesByService: Map<String, Int>
)
