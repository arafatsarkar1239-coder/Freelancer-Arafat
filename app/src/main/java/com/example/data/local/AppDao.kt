package com.example.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {
    // Profile
    @Query("SELECT * FROM profile_table WHERE id = 1 LIMIT 1")
    fun getProfile(): Flow<ProfileEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateProfile(profile: ProfileEntity)

    // Services
    @Query("SELECT * FROM services_table ORDER BY id ASC")
    fun getAllServices(): Flow<List<ServiceEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertService(service: ServiceEntity): Long

    @Update
    suspend fun updateService(service: ServiceEntity)

    @Query("DELETE FROM services_table WHERE id = :id")
    suspend fun deleteService(id: Long)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertServices(services: List<ServiceEntity>)

    // Skills
    @Query("SELECT * FROM skills_table ORDER BY id ASC")
    fun getAllSkills(): Flow<List<SkillEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSkill(skill: SkillEntity): Long

    @Delete
    suspend fun deleteSkill(skill: SkillEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSkills(skills: List<SkillEntity>)

    // Projects
    @Query("SELECT * FROM projects_table ORDER BY id DESC")
    fun getAllProjects(): Flow<List<ProjectEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProject(project: ProjectEntity): Long

    @Update
    suspend fun updateProject(project: ProjectEntity)

    @Query("DELETE FROM projects_table WHERE id = :id")
    suspend fun deleteProject(id: Long)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProjects(projects: List<ProjectEntity>)

    // Experiences
    @Query("SELECT * FROM experiences_table ORDER BY id ASC")
    fun getAllExperiences(): Flow<List<ExperienceEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExperience(experience: ExperienceEntity): Long

    @Delete
    suspend fun deleteExperience(experience: ExperienceEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExperiences(experiences: List<ExperienceEntity>)

    // Testimonials
    @Query("SELECT * FROM testimonials_table ORDER BY id ASC")
    fun getAllTestimonials(): Flow<List<TestimonialEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTestimonial(testimonial: TestimonialEntity): Long

    @Delete
    suspend fun deleteTestimonial(testimonial: TestimonialEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTestimonials(testimonials: List<TestimonialEntity>)

    // Inquiries
    @Query("SELECT * FROM inquiries_table ORDER BY timestamp DESC")
    fun getAllInquiries(): Flow<List<InquiryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInquiry(inquiry: InquiryEntity): Long

    @Update
    suspend fun updateInquiry(inquiry: InquiryEntity)

    @Query("DELETE FROM inquiries_table WHERE id = :id")
    suspend fun deleteInquiry(id: Long)

    // Blog Posts
    @Query("SELECT * FROM blog_posts_table ORDER BY id DESC")
    fun getAllBlogPosts(): Flow<List<BlogPostEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBlogPost(post: BlogPostEntity): Long

    @Update
    suspend fun updateBlogPost(post: BlogPostEntity)

    @Query("DELETE FROM blog_posts_table WHERE id = :id")
    suspend fun deleteBlogPost(id: Long)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBlogPosts(posts: List<BlogPostEntity>)

    // Counters for initial seeding check
    @Query("SELECT COUNT(*) FROM services_table")
    suspend fun getServicesCount(): Int
}
