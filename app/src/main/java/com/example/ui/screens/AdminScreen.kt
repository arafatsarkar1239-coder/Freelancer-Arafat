package com.example.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.FreelancerViewModel
import com.example.data.model.*
import com.example.ui.components.GlassCard
import com.example.ui.components.SectionHeader
import com.example.ui.theme.*

@Composable
fun AdminScreen(
    viewModel: FreelancerViewModel,
    profile: Profile,
    services: List<ServiceItem>,
    skills: List<SkillItem>,
    projects: List<PortfolioProject>,
    inquiries: List<ClientInquiry>,
    blogPosts: List<BlogPost>,
    isAdminUnlocked: Boolean,
    onUnlockSuccess: () -> Unit
) {
    val context = LocalContext.current
    var pinInput by remember { mutableStateOf("") }
    var pinError by remember { mutableStateOf<String?>(null) }
    var selectedAdminTab by remember { mutableIntStateOf(0) } // 0: Overview, 1: Inquiries, 2: Profile & Services, 3: Projects & Posts

    if (!isAdminUnlocked) {
        // Locked State
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .testTag("admin_locked_view"),
            contentAlignment = Alignment.Center
        ) {
            GlassCard {
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape)
                            .background(NavySurfaceElevated),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Lock, contentDescription = null, tint = CyanAccent, modifier = Modifier.size(32.dp))
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "Administrator Access",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = PureWhite
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Sign in to manage profile details, services, client inquiries, case studies, and insights.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondary,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    OutlinedTextField(
                        value = pinInput,
                        onValueChange = {
                            pinInput = it
                            pinError = null
                        },
                        label = { Text("Passcode (Default: arafat2025)") },
                        singleLine = true,
                        colors = contactTextFieldColors(),
                        modifier = Modifier.fillMaxWidth().testTag("admin_passcode_field")
                    )

                    if (pinError != null) {
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(text = pinError ?: "", color = ErrorRed, style = MaterialTheme.typography.bodySmall)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            if (viewModel.unlockAdmin(pinInput)) {
                                onUnlockSuccess()
                            } else {
                                pinError = "Incorrect passcode. Use 'arafat2025' or 'admin'"
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = ElectricBlue, contentColor = PureWhite),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth().height(48.dp).testTag("admin_login_btn")
                    ) {
                        Text("Unlock Dashboard", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    } else {
        // Unlocked Dashboard
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .testTag("admin_unlocked_view"),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 20.dp)
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SectionHeader(
                        tag = "Verified Admin",
                        title = "Brand Management",
                        modifier = Modifier.weight(1f)
                    )

                    IconButton(
                        onClick = { viewModel.lockAdmin() },
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(NavySurface)
                    ) {
                        Icon(Icons.Default.Lock, contentDescription = "Lock Admin", tint = CyanAccent)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Admin Navigation Tabs
                ScrollableTabRow(
                    selectedTabIndex = selectedAdminTab,
                    containerColor = NavySurface,
                    contentColor = PureWhite,
                    edgePadding = 0.dp,
                    indicator = { tabPositions ->
                        TabRowDefaults.SecondaryIndicator(
                            modifier = Modifier.tabIndicatorOffset(tabPositions[selectedAdminTab]),
                            color = CyanAccent
                        )
                    },
                    modifier = Modifier.clip(RoundedCornerShape(10.dp))
                ) {
                    Tab(
                        selected = selectedAdminTab == 0,
                        onClick = { selectedAdminTab = 0 },
                        text = { Text("Overview", fontSize = 12.sp, fontWeight = FontWeight.Bold) }
                    )
                    Tab(
                        selected = selectedAdminTab == 1,
                        onClick = { selectedAdminTab = 1 },
                        text = {
                            val newCount = inquiries.count { it.status == "New" }
                            Text(
                                text = if (newCount > 0) "Inquiries ($newCount)" else "Inquiries",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (newCount > 0) CyanAccent else PureWhite
                            )
                        }
                    )
                    Tab(
                        selected = selectedAdminTab == 2,
                        onClick = { selectedAdminTab = 2 },
                        text = { Text("Profile & Services", fontSize = 12.sp, fontWeight = FontWeight.Bold) }
                    )
                    Tab(
                        selected = selectedAdminTab == 3,
                        onClick = { selectedAdminTab = 3 },
                        text = { Text("Projects & Blog", fontSize = 12.sp, fontWeight = FontWeight.Bold) }
                    )
                }

                Spacer(modifier = Modifier.height(18.dp))
            }

            when (selectedAdminTab) {
                0 -> {
                    // TAB 0: OVERVIEW & ANALYTICS
                    item {
                        Text("Business Health & Metrics", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = PureWhite)
                        Spacer(modifier = Modifier.height(12.dp))

                        // Stats Grid
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            AdminStatCard(
                                title = "Total Inquiries",
                                count = inquiries.size.toString(),
                                icon = Icons.Default.Inbox,
                                accentColor = CyanAccent,
                                modifier = Modifier.weight(1f)
                            )
                            AdminStatCard(
                                title = "New Leads",
                                count = inquiries.count { it.status == "New" }.toString(),
                                icon = Icons.Default.NewReleases,
                                accentColor = SuccessGreen,
                                modifier = Modifier.weight(1f)
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            AdminStatCard(
                                title = "Active Services",
                                count = services.count { it.isActive }.toString(),
                                icon = Icons.Default.Category,
                                accentColor = ElectricBlue,
                                modifier = Modifier.weight(1f)
                            )
                            AdminStatCard(
                                title = "Portfolio Items",
                                count = projects.size.toString(),
                                icon = Icons.Default.Work,
                                accentColor = CyanAccentVariant,
                                modifier = Modifier.weight(1f)
                            )
                        }

                        Spacer(modifier = Modifier.height(18.dp))

                        // Service Inquiries Breakdown
                        GlassCard {
                            Text("Service Inquiry Demand Distribution", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = PureWhite)
                            Spacer(modifier = Modifier.height(12.dp))

                            val serviceCounts = inquiries.groupBy { it.requestedService }.mapValues { it.value.size }
                            if (serviceCounts.isEmpty()) {
                                Text("No incoming inquiries logged yet.", color = TextSecondary, fontSize = 13.sp)
                            } else {
                                serviceCounts.forEach { (srv, count) ->
                                    val percent = (count.toFloat() / inquiries.size.coerceAtLeast(1)) * 100
                                    Column(modifier = Modifier.padding(vertical = 4.dp)) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text(srv, style = MaterialTheme.typography.bodySmall, color = PureWhite)
                                            Text("$count leads (${percent.toInt()}%)", style = MaterialTheme.typography.labelSmall, color = CyanAccent)
                                        }
                                        Spacer(modifier = Modifier.height(4.dp))
                                        LinearProgressIndicator(
                                            progress = { percent / 100f },
                                            modifier = Modifier.fillMaxWidth().height(6.dp).clip(RoundedCornerShape(3.dp)),
                                            color = CyanAccent,
                                            trackColor = NavySurfaceElevated
                                        )
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Quick AI Brand Assistant Link
                        GlassCard {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = CyanAccent, modifier = Modifier.size(28.dp))
                                Spacer(modifier = Modifier.width(12.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text("Arafat AI Assistant", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = PureWhite)
                                    Text("Generate high-converting proposals, copy, and SEO meta with Gemini.", style = MaterialTheme.typography.bodySmall, color = TextSecondary)
                                }
                                Button(
                                    onClick = { viewModel.navigateTo("ai") },
                                    colors = ButtonDefaults.buttonColors(containerColor = ElectricBlue)
                                ) {
                                    Text("Open AI")
                                }
                            }
                        }
                    }
                }

                1 -> {
                    // TAB 1: INQUIRIES MANAGEMENT
                    if (inquiries.isEmpty()) {
                        item {
                            GlassCard {
                                Text("No client inquiries received yet.", color = TextSecondary)
                            }
                        }
                    } else {
                        items(inquiries) { inquiry ->
                            AdminInquiryCard(
                                inquiry = inquiry,
                                onUpdateStatus = { newStatus, notes ->
                                    viewModel.updateInquiryStatus(inquiry, newStatus, notes)
                                    Toast.makeText(context, "Status updated to $newStatus", Toast.LENGTH_SHORT).show()
                                },
                                onDelete = {
                                    viewModel.deleteInquiry(inquiry.id)
                                    Toast.makeText(context, "Inquiry deleted", Toast.LENGTH_SHORT).show()
                                }
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                    }
                }

                2 -> {
                    // TAB 2: PROFILE & SERVICES
                    item {
                        AdminProfileEditor(
                            profile = profile,
                            onSave = { updated ->
                                viewModel.updateProfile(updated)
                                Toast.makeText(context, "Profile updated successfully!", Toast.LENGTH_SHORT).show()
                            }
                        )

                        Spacer(modifier = Modifier.height(18.dp))

                        Text("Manage Services (${services.size})", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = PureWhite)
                        Spacer(modifier = Modifier.height(10.dp))
                    }

                    items(services) { srv ->
                        GlassCard {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(srv.title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = PureWhite)
                                    Text(srv.category, style = MaterialTheme.typography.labelSmall, color = CyanAccent)
                                }
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Switch(
                                        checked = srv.isActive,
                                        onCheckedChange = { checked ->
                                            viewModel.updateService(srv.copy(isActive = checked))
                                        },
                                        colors = SwitchDefaults.colors(
                                            checkedThumbColor = CyanAccent,
                                            checkedTrackColor = ElectricBlueContainer
                                        )
                                    )
                                    IconButton(onClick = { viewModel.deleteService(srv.id) }) {
                                        Icon(Icons.Default.Delete, contentDescription = "Delete", tint = TextMuted)
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(srv.description, style = MaterialTheme.typography.bodySmall, color = TextSecondary, maxLines = 2)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }

                3 -> {
                    // TAB 3: PROJECTS & BLOG POSTS
                    item {
                        Text("Add New Portfolio Case Study", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = PureWhite)
                        Spacer(modifier = Modifier.height(10.dp))

                        AdminProjectAdder(
                            onAddProject = { proj ->
                                viewModel.addProject(proj)
                                Toast.makeText(context, "Project added to portfolio!", Toast.LENGTH_SHORT).show()
                            }
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        Text("Existing Case Studies (${projects.size})", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = PureWhite)
                        Spacer(modifier = Modifier.height(10.dp))
                    }

                    items(projects) { proj ->
                        GlassCard {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(proj.title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = PureWhite)
                                    Text("Client: ${proj.clientName} • ${proj.category}", style = MaterialTheme.typography.labelSmall, color = CyanAccent)
                                }
                                IconButton(onClick = { viewModel.deleteProject(proj.id) }) {
                                    Icon(Icons.Default.Delete, contentDescription = "Delete", tint = TextMuted)
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    item {
                        Spacer(modifier = Modifier.height(18.dp))
                        Text("Publish New Blog Post", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = PureWhite)
                        Spacer(modifier = Modifier.height(10.dp))

                        AdminBlogAdder(
                            onAddPost = { post ->
                                viewModel.addBlogPost(post)
                                Toast.makeText(context, "Article published to blog!", Toast.LENGTH_SHORT).show()
                            }
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
}

@Composable
fun AdminStatCard(
    title: String,
    count: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    accentColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.border(1.dp, NavyBorder, RoundedCornerShape(14.dp)),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = DeepNavy)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = count, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.ExtraBold, color = PureWhite)
                Icon(imageVector = icon, contentDescription = null, tint = accentColor, modifier = Modifier.size(24.dp))
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = title, style = MaterialTheme.typography.labelMedium, color = TextSecondary)
        }
    }
}

@Composable
fun AdminInquiryCard(
    inquiry: ClientInquiry,
    onUpdateStatus: (String, String) -> Unit,
    onDelete: () -> Unit
) {
    var notesText by remember { mutableStateOf(inquiry.adminNotes) }

    GlassCard {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(inquiry.clientName, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = PureWhite)
                Text(inquiry.clientEmail, style = MaterialTheme.typography.bodySmall, color = CyanAccent)
                if (inquiry.companyName.isNotBlank()) {
                    Text(inquiry.companyName, style = MaterialTheme.typography.labelSmall, color = TextMuted)
                }
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(
                        when (inquiry.status) {
                            "New" -> SuccessGreen.copy(alpha = 0.2f)
                            "In Progress" -> WarningAmber.copy(alpha = 0.2f)
                            "Completed" -> ElectricBlueContainer
                            else -> NavySurfaceElevated
                        }
                    )
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = inquiry.status,
                    style = MaterialTheme.typography.labelSmall,
                    color = when (inquiry.status) {
                        "New" -> SuccessGreen
                        "In Progress" -> WarningAmber
                        "Completed" -> CyanAccent
                        else -> TextPrimary
                    },
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text("Service: ${inquiry.requestedService} • Budget: ${inquiry.estimatedBudget}", style = MaterialTheme.typography.labelMedium, color = PureWhite)
        Spacer(modifier = Modifier.height(4.dp))
        Text(inquiry.projectDetails, style = MaterialTheme.typography.bodyMedium, color = TextSecondary)

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = notesText,
            onValueChange = { notesText = it },
            label = { Text("Internal Admin Notes") },
            colors = contactTextFieldColors(),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Button(
                    onClick = { onUpdateStatus("In Progress", notesText) },
                    colors = ButtonDefaults.buttonColors(containerColor = NavySurface),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text("In Progress", fontSize = 11.sp, color = CyanAccent)
                }

                Button(
                    onClick = { onUpdateStatus("Completed", notesText) },
                    colors = ButtonDefaults.buttonColors(containerColor = NavySurface),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text("Done", fontSize = 11.sp, color = SuccessGreen)
                }
            }

            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Delete", tint = TextMuted)
            }
        }
    }
}

@Composable
fun AdminProfileEditor(
    profile: Profile,
    onSave: (Profile) -> Unit
) {
    var title by remember { mutableStateOf(profile.title) }
    var headline by remember { mutableStateOf(profile.headline) }
    var subheadline by remember { mutableStateOf(profile.subheadline) }
    var availability by remember { mutableStateOf(profile.availabilityStatus) }
    var bio by remember { mutableStateOf(profile.aboutBio) }

    GlassCard {
        Text("Edit Personal Brand Information", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = PureWhite)
        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Professional Title") },
            colors = contactTextFieldColors(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = availability,
            onValueChange = { availability = it },
            label = { Text("Availability Status") },
            colors = contactTextFieldColors(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = headline,
            onValueChange = { headline = it },
            label = { Text("Homepage Headline") },
            colors = contactTextFieldColors(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = subheadline,
            onValueChange = { subheadline = it },
            label = { Text("Subheadline") },
            colors = contactTextFieldColors(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = bio,
            onValueChange = { bio = it },
            label = { Text("Biography") },
            minLines = 3,
            colors = contactTextFieldColors(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(14.dp))

        Button(
            onClick = {
                onSave(
                    profile.copy(
                        title = title,
                        headline = headline,
                        subheadline = subheadline,
                        availabilityStatus = availability,
                        aboutBio = bio
                    )
                )
            },
            colors = ButtonDefaults.buttonColors(containerColor = ElectricBlue),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Profile Changes", fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun AdminProjectAdder(
    onAddProject: (PortfolioProject) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Lead Generation") }
    var clientName by remember { mutableStateOf("") }
    var shortDesc by remember { mutableStateOf("") }
    var objectives by remember { mutableStateOf("") }
    var workPerformed by remember { mutableStateOf("") }
    var results by remember { mutableStateOf("") }

    GlassCard {
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Project Title *") },
            colors = contactTextFieldColors(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = clientName,
            onValueChange = { clientName = it },
            label = { Text("Client / Business Name") },
            colors = contactTextFieldColors(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = shortDesc,
            onValueChange = { shortDesc = it },
            label = { Text("Short Description") },
            colors = contactTextFieldColors(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = objectives,
            onValueChange = { objectives = it },
            label = { Text("Objectives") },
            colors = contactTextFieldColors(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = workPerformed,
            onValueChange = { workPerformed = it },
            label = { Text("Work Performed") },
            colors = contactTextFieldColors(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = results,
            onValueChange = { results = it },
            label = { Text("Verified Results (e.g. 40+ Leads, 120% ROI)") },
            colors = contactTextFieldColors(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(14.dp))

        Button(
            onClick = {
                if (title.isNotBlank()) {
                    onAddProject(
                        PortfolioProject(
                            id = 0,
                            title = title,
                            category = category,
                            shortDescription = shortDesc,
                            clientName = if (clientName.isBlank()) "Authorized Client" else clientName,
                            objectives = objectives,
                            workPerformed = workPerformed,
                            results = results,
                            toolsUsed = listOf("LinkedIn", "Google Analytics", "Apollo"),
                            projectUrl = "https://arafatsarkarniloy.blogspot.com",
                            completionDate = "Recently Delivered",
                            isFeatured = true
                        )
                    )
                    title = ""
                    clientName = ""
                    shortDesc = ""
                    objectives = ""
                    workPerformed = ""
                    results = ""
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = CyanAccent, contentColor = CharcoalBlack),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Case Study", fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun AdminBlogAdder(
    onAddPost: (BlogPost) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Digital Marketing") }
    var summary by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    GlassCard {
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Article Title *") },
            colors = contactTextFieldColors(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = summary,
            onValueChange = { summary = it },
            label = { Text("Meta Summary") },
            colors = contactTextFieldColors(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = content,
            onValueChange = { content = it },
            label = { Text("Full Article Content") },
            minLines = 4,
            colors = contactTextFieldColors(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(14.dp))

        Button(
            onClick = {
                if (title.isNotBlank() && content.isNotBlank()) {
                    onAddPost(
                        BlogPost(
                            id = 0,
                            title = title,
                            category = category,
                            readingTime = "4 min read",
                            publishDate = "Just published",
                            summary = summary,
                            content = content,
                            tags = listOf("Digital Growth", "Marketing Strategy"),
                            isPublished = true
                        )
                    )
                    title = ""
                    summary = ""
                    content = ""
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = ElectricBlue),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Publish Article", fontWeight = FontWeight.Bold)
        }
    }
}
