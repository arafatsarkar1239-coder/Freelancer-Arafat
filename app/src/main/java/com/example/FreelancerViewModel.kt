package com.example

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AppDatabase
import com.example.data.model.*
import com.example.data.remote.AiResult
import com.example.data.remote.GeminiService
import com.example.data.repository.AppRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

sealed class InquirySubmissionState {
    object Idle : InquirySubmissionState()
    object Submitting : InquirySubmissionState()
    data class Success(val inquiryId: Long) : InquirySubmissionState()
    data class Error(val message: String) : InquirySubmissionState()
}

sealed class AiGenerationState {
    object Idle : AiGenerationState()
    object Loading : AiGenerationState()
    data class Success(val result: String) : AiGenerationState()
    data class Error(val message: String, val isKeyMissing: Boolean) : AiGenerationState()
}

class FreelancerViewModel(application: Application) : AndroidViewModel(application) {
    private val database = AppDatabase.getDatabase(application)
    private val repository = AppRepository(database.appDao())
    private val geminiService = GeminiService()

    // Screen navigation
    private val _currentScreen = MutableStateFlow("home")
    val currentScreen: StateFlow<String> = _currentScreen.asStateFlow()

    // Preselected service for contact inquiry
    private val _prefilledService = MutableStateFlow<String?>(null)
    val prefilledService: StateFlow<String?> = _prefilledService.asStateFlow()

    // Modals / Details
    private val _selectedProject = MutableStateFlow<PortfolioProject?>(null)
    val selectedProject: StateFlow<PortfolioProject?> = _selectedProject.asStateFlow()

    private val _selectedBlogPost = MutableStateFlow<BlogPost?>(null)
    val selectedBlogPost: StateFlow<BlogPost?> = _selectedBlogPost.asStateFlow()

    // Admin unlock state
    private val _isAdminUnlocked = MutableStateFlow(false)
    val isAdminUnlocked: StateFlow<Boolean> = _isAdminUnlocked.asStateFlow()

    // Form submission state
    private val _inquiryState = MutableStateFlow<InquirySubmissionState>(InquirySubmissionState.Idle)
    val inquiryState: StateFlow<InquirySubmissionState> = _inquiryState.asStateFlow()

    // AI Generation state
    private val _aiState = MutableStateFlow<AiGenerationState>(AiGenerationState.Idle)
    val aiState: StateFlow<AiGenerationState> = _aiState.asStateFlow()

    // Repository Data Streams
    val profile: StateFlow<Profile> = repository.profile
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), Profile())

    val services: StateFlow<List<ServiceItem>> = repository.services
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val skills: StateFlow<List<SkillItem>> = repository.skills
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val projects: StateFlow<List<PortfolioProject>> = repository.projects
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val experiences: StateFlow<List<ExperienceItem>> = repository.experiences
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val testimonials: StateFlow<List<TestimonialItem>> = repository.testimonials
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val inquiries: StateFlow<List<ClientInquiry>> = repository.inquiries
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val blogPosts: StateFlow<List<BlogPost>> = repository.blogPosts
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Filter categories
    private val _selectedPortfolioCategory = MutableStateFlow("All")
    val selectedPortfolioCategory: StateFlow<String> = _selectedPortfolioCategory.asStateFlow()

    private val _blogSearchQuery = MutableStateFlow("")
    val blogSearchQuery: StateFlow<String> = _blogSearchQuery.asStateFlow()

    init {
        viewModelScope.launch {
            repository.checkAndSeedDatabase()
        }
    }

    fun navigateTo(screen: String) {
        _currentScreen.value = screen
    }

    fun openServiceInquiry(serviceTitle: String) {
        _prefilledService.value = serviceTitle
        _currentScreen.value = "contact"
    }

    fun clearPrefilledService() {
        _prefilledService.value = null
    }

    fun selectProject(project: PortfolioProject?) {
        _selectedProject.value = project
    }

    fun selectBlogPost(post: BlogPost?) {
        _selectedBlogPost.value = post
    }

    fun setPortfolioCategory(category: String) {
        _selectedPortfolioCategory.value = category
    }

    fun setBlogSearchQuery(query: String) {
        _blogSearchQuery.value = query
    }

    fun unlockAdmin(pin: String): Boolean {
        // Master admin PIN or simple match
        if (pin.trim() == "arafat2025" || pin.trim() == "admin") {
            _isAdminUnlocked.value = true
            return true
        }
        return false
    }

    fun lockAdmin() {
        _isAdminUnlocked.value = false
    }

    fun submitClientInquiry(
        name: String,
        email: String,
        company: String,
        service: String,
        budget: String,
        details: String,
        preferredContact: String
    ) {
        if (name.isBlank() || email.isBlank() || details.isBlank()) {
            _inquiryState.value = InquirySubmissionState.Error("Please fill out your name, email, and project details.")
            return
        }

        viewModelScope.launch {
            _inquiryState.value = InquirySubmissionState.Submitting
            try {
                val newInquiry = ClientInquiry(
                    clientName = name.trim(),
                    clientEmail = email.trim(),
                    companyName = company.trim(),
                    requestedService = if (service.isBlank()) "Digital Marketing" else service,
                    estimatedBudget = if (budget.isBlank()) "$1,000 - $3,000" else budget,
                    projectDetails = details.trim(),
                    preferredContactMethod = if (preferredContact.isBlank()) "Email" else preferredContact,
                    timestamp = System.currentTimeMillis(),
                    status = "New"
                )
                val id = repository.submitInquiry(newInquiry)
                _inquiryState.value = InquirySubmissionState.Success(id)
            } catch (e: Exception) {
                _inquiryState.value = InquirySubmissionState.Error(e.localizedMessage ?: "Failed to save inquiry")
            }
        }
    }

    fun resetInquiryState() {
        _inquiryState.value = InquirySubmissionState.Idle
    }

    // Admin updates
    fun updateProfile(profile: Profile) {
        viewModelScope.launch {
            repository.updateProfile(profile)
        }
    }

    fun addService(service: ServiceItem) {
        viewModelScope.launch {
            repository.insertService(service)
        }
    }

    fun updateService(service: ServiceItem) {
        viewModelScope.launch {
            repository.updateService(service)
        }
    }

    fun deleteService(id: Long) {
        viewModelScope.launch {
            repository.deleteService(id)
        }
    }

    fun addProject(project: PortfolioProject) {
        viewModelScope.launch {
            repository.insertProject(project)
        }
    }

    fun updateProject(project: PortfolioProject) {
        viewModelScope.launch {
            repository.updateProject(project)
        }
    }

    fun deleteProject(id: Long) {
        viewModelScope.launch {
            repository.deleteProject(id)
        }
    }

    fun addSkill(skill: SkillItem) {
        viewModelScope.launch {
            repository.insertSkill(skill)
        }
    }

    fun deleteSkill(skill: SkillItem) {
        viewModelScope.launch {
            repository.deleteSkill(skill)
        }
    }

    fun updateInquiryStatus(inquiry: ClientInquiry, newStatus: String, notes: String) {
        viewModelScope.launch {
            repository.updateFullInquiry(
                inquiry.copy(status = newStatus, adminNotes = notes)
            )
        }
    }

    fun deleteInquiry(id: Long) {
        viewModelScope.launch {
            repository.deleteInquiry(id)
        }
    }

    fun addBlogPost(post: BlogPost) {
        viewModelScope.launch {
            repository.insertBlogPost(post)
        }
    }

    fun updateBlogPost(post: BlogPost) {
        viewModelScope.launch {
            repository.updateBlogPost(post)
        }
    }

    fun deleteBlogPost(id: Long) {
        viewModelScope.launch {
            repository.deleteBlogPost(id)
        }
    }

    // AI Generation
    fun generateAiContent(taskType: String, prompt: String) {
        viewModelScope.launch {
            _aiState.value = AiGenerationState.Loading
            when (val result = geminiService.generateBrandingContent(prompt, taskType)) {
                is AiResult.Success -> {
                    _aiState.value = AiGenerationState.Success(result.outputText)
                }
                is AiResult.Error -> {
                    _aiState.value = AiGenerationState.Error(result.message, result.isKeyMissing)
                }
            }
        }
    }

    fun clearAiState() {
        _aiState.value = AiGenerationState.Idle
    }
}
