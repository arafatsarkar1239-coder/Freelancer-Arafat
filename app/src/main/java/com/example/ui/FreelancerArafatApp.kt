package com.example.ui

import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.FreelancerViewModel
import com.example.ui.components.AdminPasscodeDialog
import com.example.ui.components.FreelancerTopBar
import com.example.ui.screens.*
import com.example.ui.theme.*

data class NavItem(
    val route: String,
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

@Composable
fun FreelancerArafatApp(
    viewModel: FreelancerViewModel = viewModel()
) {
    val currentScreen by viewModel.currentScreen.collectAsStateWithLifecycle()
    val profile by viewModel.profile.collectAsStateWithLifecycle()
    val services by viewModel.services.collectAsStateWithLifecycle()
    val skills by viewModel.skills.collectAsStateWithLifecycle()
    val projects by viewModel.projects.collectAsStateWithLifecycle()
    val experiences by viewModel.experiences.collectAsStateWithLifecycle()
    val testimonials by viewModel.testimonials.collectAsStateWithLifecycle()
    val inquiries by viewModel.inquiries.collectAsStateWithLifecycle()
    val blogPosts by viewModel.blogPosts.collectAsStateWithLifecycle()

    val prefilledService by viewModel.prefilledService.collectAsStateWithLifecycle()
    val selectedProject by viewModel.selectedProject.collectAsStateWithLifecycle()
    val selectedBlogPost by viewModel.selectedBlogPost.collectAsStateWithLifecycle()
    val selectedPortfolioCategory by viewModel.selectedPortfolioCategory.collectAsStateWithLifecycle()

    val isAdminUnlocked by viewModel.isAdminUnlocked.collectAsStateWithLifecycle()
    val inquiryState by viewModel.inquiryState.collectAsStateWithLifecycle()
    val aiState by viewModel.aiState.collectAsStateWithLifecycle()

    var showAdminPasscodeDialog by remember { mutableStateOf(false) }

    // Intercept hardware / system back navigation
    BackHandler(enabled = currentScreen != "home") {
        if (currentScreen == "contact" && prefilledService != null) {
            viewModel.clearPrefilledService()
        }
        viewModel.navigateTo("home")
    }

    if (showAdminPasscodeDialog) {
        AdminPasscodeDialog(
            onDismiss = { showAdminPasscodeDialog = false },
            onSuccess = {
                viewModel.unlockAdmin("arafat2025")
                showAdminPasscodeDialog = false
                viewModel.navigateTo("admin")
            }
        )
    }

    val navItems = listOf(
        NavItem("home", "Home", Icons.Filled.Home, Icons.Outlined.Home),
        NavItem("about", "About", Icons.Filled.Person, Icons.Outlined.Person),
        NavItem("services", "Services", Icons.Filled.Category, Icons.Outlined.Category),
        NavItem("portfolio", "Portfolio", Icons.Filled.Work, Icons.Outlined.WorkOutline),
        NavItem("blog", "Blog", Icons.Filled.Article, Icons.Outlined.Article),
        NavItem("contact", "Contact", Icons.Filled.Send, Icons.Outlined.Send)
    )

    Scaffold(
        containerColor = CharcoalBlack,
        topBar = {
            Column {
                FreelancerTopBar(
                    currentScreen = currentScreen,
                    isAdminUnlocked = isAdminUnlocked,
                    availabilityStatus = profile.availabilityStatus,
                    onNavigate = { viewModel.navigateTo(it) },
                    onAdminClick = {
                        if (isAdminUnlocked) {
                            viewModel.navigateTo("admin")
                        } else {
                            showAdminPasscodeDialog = true
                        }
                    }
                )

                // Secondary quick sub-actions (AI Assistant & Admin shortcuts)
                Surface(
                    color = NavySurface,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 6.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // AI button
                        TextButton(
                            onClick = { viewModel.navigateTo("ai") },
                            colors = ButtonDefaults.textButtonColors(
                                contentColor = if (currentScreen == "ai") CyanAccent else TextSecondary
                            ),
                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            Icon(Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(16.dp), tint = CyanAccent)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Arafat AI Assistant", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                        }

                        // Admin Dashboard shortcut
                        TextButton(
                            onClick = {
                                if (isAdminUnlocked) {
                                    viewModel.navigateTo("admin")
                                } else {
                                    showAdminPasscodeDialog = true
                                }
                            },
                            colors = ButtonDefaults.textButtonColors(
                                contentColor = if (currentScreen == "admin") CyanAccent else TextSecondary
                            ),
                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            Icon(
                                imageVector = if (isAdminUnlocked) Icons.Default.Dashboard else Icons.Outlined.Lock,
                                contentDescription = null,
                                modifier = Modifier.size(15.dp),
                                tint = if (isAdminUnlocked) SuccessGreen else TextMuted
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = if (isAdminUnlocked) "Dashboard (Unlocked)" else "Admin Panel",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }
        },
        bottomBar = {
            NavigationBar(
                containerColor = DeepNavy,
                contentColor = PureWhite,
                tonalElevation = 8.dp,
                windowInsets = WindowInsets.navigationBars,
                modifier = Modifier.testTag("bottom_nav_bar")
            ) {
                navItems.forEach { item ->
                    val isSelected = currentScreen == item.route
                    NavigationBarItem(
                        selected = isSelected,
                        onClick = { viewModel.navigateTo(item.route) },
                        icon = {
                            Icon(
                                imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                                contentDescription = item.title
                            )
                        },
                        label = {
                            Text(
                                text = item.title,
                                fontSize = 10.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = CharcoalBlack,
                            selectedTextColor = CyanAccent,
                            indicatorColor = CyanAccent,
                            unselectedIconColor = TextSecondary,
                            unselectedTextColor = TextSecondary
                        ),
                        modifier = Modifier.testTag("nav_item_${item.route}")
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(CharcoalBlack)
        ) {
            when (currentScreen) {
                "home" -> HomeScreen(
                    profile = profile,
                    services = services,
                    projects = projects,
                    testimonials = testimonials,
                    onNavigate = { viewModel.navigateTo(it) },
                    onDiscussService = { viewModel.openServiceInquiry(it) },
                    onSelectProject = { viewModel.selectProject(it) }
                )
                "about" -> AboutScreen(
                    profile = profile,
                    skills = skills,
                    experiences = experiences,
                    onContactClick = { viewModel.navigateTo("contact") }
                )
                "services" -> ServicesScreen(
                    services = services,
                    onDiscussService = { viewModel.openServiceInquiry(it) }
                )
                "portfolio" -> PortfolioScreen(
                    projects = projects,
                    selectedCategory = selectedPortfolioCategory,
                    onCategorySelect = { viewModel.setPortfolioCategory(it) },
                    selectedProjectForDetail = selectedProject,
                    onSelectProject = { viewModel.selectProject(it) }
                )
                "blog" -> BlogScreen(
                    posts = blogPosts,
                    selectedPostForDetail = selectedBlogPost,
                    onSelectPost = { viewModel.selectBlogPost(it) }
                )
                "contact" -> ContactScreen(
                    profile = profile,
                    prefilledService = prefilledService,
                    inquiryState = inquiryState,
                    onSubmitInquiry = { name, email, company, service, budget, details, preferredContact ->
                        viewModel.submitClientInquiry(name, email, company, service, budget, details, preferredContact)
                    },
                    onResetInquiryState = { viewModel.resetInquiryState() }
                )
                "admin" -> AdminScreen(
                    viewModel = viewModel,
                    profile = profile,
                    services = services,
                    skills = skills,
                    projects = projects,
                    inquiries = inquiries,
                    blogPosts = blogPosts,
                    isAdminUnlocked = isAdminUnlocked,
                    onUnlockSuccess = { /* state handled in viewmodel */ }
                )
                "ai" -> AiAssistantScreen(
                    viewModel = viewModel,
                    aiState = aiState
                )
                else -> HomeScreen(
                    profile = profile,
                    services = services,
                    projects = projects,
                    testimonials = testimonials,
                    onNavigate = { viewModel.navigateTo(it) },
                    onDiscussService = { viewModel.openServiceInquiry(it) },
                    onSelectProject = { viewModel.selectProject(it) }
                )
            }
        }
    }
}
