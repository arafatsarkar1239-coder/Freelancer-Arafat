package com.example.data.repository

import com.example.data.local.*
import com.example.data.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class AppRepository(private val appDao: AppDao) {

    suspend fun checkAndSeedDatabase() = withContext(Dispatchers.IO) {
        val count = appDao.getServicesCount()
        if (count == 0) {
            // Seed profile
            appDao.insertOrUpdateProfile(DefaultData.initialProfile)
            // Seed services
            appDao.insertServices(DefaultData.initialServices)
            // Seed skills
            appDao.insertSkills(DefaultData.initialSkills)
            // Seed projects
            appDao.insertProjects(DefaultData.initialProjects)
            // Seed experiences
            appDao.insertExperiences(DefaultData.initialExperiences)
            // Seed testimonials
            appDao.insertTestimonials(DefaultData.initialTestimonials)
            // Seed blog posts
            appDao.insertBlogPosts(DefaultData.initialBlogPosts)

            // Seed a sample completed inquiry so analytics and dashboard have immediate realistic preview
            appDao.insertInquiry(
                InquiryEntity(
                    clientName = "Sarah Jenkins",
                    clientEmail = "sarah@bloom-ecommerce.com",
                    companyName = "Bloom E-Commerce",
                    requestedService = "Facebook & Instagram Ads",
                    estimatedBudget = "$1,000 - $3,000",
                    projectDetails = "Looking to scale our seasonal product line with targeted paid acquisition and lead generation.",
                    preferredContactMethod = "Email",
                    timestamp = System.currentTimeMillis() - 86400000L * 3,
                    status = "Completed",
                    adminNotes = "Campaign delivered with great ROAS. Client agreed to be a testimonial."
                )
            )
            appDao.insertInquiry(
                InquiryEntity(
                    clientName = "Marcus Vance",
                    clientEmail = "marcus@apextech.io",
                    companyName = "Apex Technology Partners",
                    requestedService = "Lead Generation",
                    estimatedBudget = "$3,000+",
                    projectDetails = "Need 1,000 verified VP/Director level tech decision makers in SaaS.",
                    preferredContactMethod = "LinkedIn",
                    timestamp = System.currentTimeMillis() - 86400000L * 1,
                    status = "In Progress",
                    adminNotes = "Initial sample list sent for review."
                )
            )
        }
    }

    val profile: Flow<Profile> = appDao.getProfile().map { entity ->
        entity?.toModel() ?: DefaultData.initialProfile.toModel()
    }

    val services: Flow<List<ServiceItem>> = appDao.getAllServices().map { list ->
        list.map { it.toModel() }
    }

    val skills: Flow<List<SkillItem>> = appDao.getAllSkills().map { list ->
        list.map { it.toModel() }
    }

    val projects: Flow<List<PortfolioProject>> = appDao.getAllProjects().map { list ->
        list.map { it.toModel() }
    }

    val experiences: Flow<List<ExperienceItem>> = appDao.getAllExperiences().map { list ->
        list.map { it.toModel() }
    }

    val testimonials: Flow<List<TestimonialItem>> = appDao.getAllTestimonials().map { list ->
        list.map { it.toModel() }
    }

    val inquiries: Flow<List<ClientInquiry>> = appDao.getAllInquiries().map { list ->
        list.map { it.toModel() }
    }

    val blogPosts: Flow<List<BlogPost>> = appDao.getAllBlogPosts().map { list ->
        list.map { it.toModel() }
    }

    // Mutators
    suspend fun updateProfile(profile: Profile) = withContext(Dispatchers.IO) {
        appDao.insertOrUpdateProfile(ProfileEntity.fromModel(profile))
    }

    suspend fun insertService(service: ServiceItem) = withContext(Dispatchers.IO) {
        appDao.insertService(
            ServiceEntity(
                id = service.id,
                title = service.title,
                category = service.category,
                description = service.description,
                deliverables = service.deliverables,
                iconKey = service.iconKey,
                isActive = service.isActive
            )
        )
    }

    suspend fun updateService(service: ServiceItem) = withContext(Dispatchers.IO) {
        appDao.updateService(
            ServiceEntity(
                id = service.id,
                title = service.title,
                category = service.category,
                description = service.description,
                deliverables = service.deliverables,
                iconKey = service.iconKey,
                isActive = service.isActive
            )
        )
    }

    suspend fun deleteService(id: Long) = withContext(Dispatchers.IO) {
        appDao.deleteService(id)
    }

    suspend fun insertSkill(skill: SkillItem) = withContext(Dispatchers.IO) {
        appDao.insertSkill(
            SkillEntity(
                id = skill.id,
                name = skill.name,
                category = skill.category,
                proficiencyLabel = skill.proficiencyLabel
            )
        )
    }

    suspend fun deleteSkill(skill: SkillItem) = withContext(Dispatchers.IO) {
        appDao.deleteSkill(
            SkillEntity(
                id = skill.id,
                name = skill.name,
                category = skill.category,
                proficiencyLabel = skill.proficiencyLabel
            )
        )
    }

    suspend fun insertProject(project: PortfolioProject) = withContext(Dispatchers.IO) {
        appDao.insertProject(
            ProjectEntity(
                id = project.id,
                title = project.title,
                category = project.category,
                shortDescription = project.shortDescription,
                clientName = project.clientName,
                objectives = project.objectives,
                workPerformed = project.workPerformed,
                results = project.results,
                toolsUsed = project.toolsUsed,
                projectUrl = project.projectUrl,
                completionDate = project.completionDate,
                isFeatured = project.isFeatured
            )
        )
    }

    suspend fun updateProject(project: PortfolioProject) = withContext(Dispatchers.IO) {
        appDao.updateProject(
            ProjectEntity(
                id = project.id,
                title = project.title,
                category = project.category,
                shortDescription = project.shortDescription,
                clientName = project.clientName,
                objectives = project.objectives,
                workPerformed = project.workPerformed,
                results = project.results,
                toolsUsed = project.toolsUsed,
                projectUrl = project.projectUrl,
                completionDate = project.completionDate,
                isFeatured = project.isFeatured
            )
        )
    }

    suspend fun deleteProject(id: Long) = withContext(Dispatchers.IO) {
        appDao.deleteProject(id)
    }

    suspend fun submitInquiry(inquiry: ClientInquiry): Long = withContext(Dispatchers.IO) {
        appDao.insertInquiry(
            InquiryEntity(
                id = 0,
                clientName = inquiry.clientName,
                clientEmail = inquiry.clientEmail,
                companyName = inquiry.companyName,
                requestedService = inquiry.requestedService,
                estimatedBudget = inquiry.estimatedBudget,
                projectDetails = inquiry.projectDetails,
                preferredContactMethod = inquiry.preferredContactMethod,
                timestamp = System.currentTimeMillis(),
                status = "New",
                adminNotes = ""
            )
        )
    }

    suspend fun updateInquiryStatus(id: Long, newStatus: String, notes: String) = withContext(Dispatchers.IO) {
        appDao.updateInquiry(
            InquiryEntity(
                id = id,
                clientName = "",
                clientEmail = "",
                companyName = "",
                requestedService = "",
                estimatedBudget = "",
                projectDetails = "",
                preferredContactMethod = "",
                status = newStatus,
                adminNotes = notes
            )
        )
    }

    suspend fun updateFullInquiry(inquiry: ClientInquiry) = withContext(Dispatchers.IO) {
        appDao.updateInquiry(
            InquiryEntity(
                id = inquiry.id,
                clientName = inquiry.clientName,
                clientEmail = inquiry.clientEmail,
                companyName = inquiry.companyName,
                requestedService = inquiry.requestedService,
                estimatedBudget = inquiry.estimatedBudget,
                projectDetails = inquiry.projectDetails,
                preferredContactMethod = inquiry.preferredContactMethod,
                timestamp = inquiry.timestamp,
                status = inquiry.status,
                adminNotes = inquiry.adminNotes
            )
        )
    }

    suspend fun deleteInquiry(id: Long) = withContext(Dispatchers.IO) {
        appDao.deleteInquiry(id)
    }

    suspend fun insertBlogPost(post: BlogPost) = withContext(Dispatchers.IO) {
        appDao.insertBlogPost(
            BlogPostEntity(
                id = post.id,
                title = post.title,
                category = post.category,
                readingTime = post.readingTime,
                publishDate = post.publishDate,
                summary = post.summary,
                content = post.content,
                tags = post.tags,
                isPublished = post.isPublished
            )
        )
    }

    suspend fun updateBlogPost(post: BlogPost) = withContext(Dispatchers.IO) {
        appDao.updateBlogPost(
            BlogPostEntity(
                id = post.id,
                title = post.title,
                category = post.category,
                readingTime = post.readingTime,
                publishDate = post.publishDate,
                summary = post.summary,
                content = post.content,
                tags = post.tags,
                isPublished = post.isPublished
            )
        )
    }

    suspend fun deleteBlogPost(id: Long) = withContext(Dispatchers.IO) {
        appDao.deleteBlogPost(id)
    }
}
