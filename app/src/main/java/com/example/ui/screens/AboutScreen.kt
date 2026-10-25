package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.ExperienceItem
import com.example.data.model.Profile
import com.example.data.model.SkillItem
import com.example.ui.components.GlassCard
import com.example.ui.components.SectionHeader
import com.example.ui.components.openExternalUrl
import com.example.ui.theme.*

@Composable
fun AboutScreen(
    profile: Profile,
    skills: List<SkillItem>,
    experiences: List<ExperienceItem>,
    onContactClick: () -> Unit
) {
    val context = LocalContext.current
    var selectedJourneyTab by remember { mutableIntStateOf(0) } // 0: Experience, 1: Education & Certifications

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("about_screen_content"),
        contentPadding = PaddingValues(bottom = 96.dp)
    ) {
        // Profile Header Card
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(listOf(DeepNavy, CharcoalBlack))
                    )
                    .padding(20.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(110.dp)
                            .clip(CircleShape)
                            .background(Brush.sweepGradient(listOf(ElectricBlue, CyanAccent, ElectricBlue)))
                            .padding(3.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.arafat_avatar),
                            contentDescription = profile.name,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize().clip(CircleShape)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = profile.name,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = PureWhite
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = profile.title,
                        style = MaterialTheme.typography.bodyMedium,
                        color = CyanAccent,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.LocationOn, contentDescription = null, tint = TextMuted, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = profile.location,
                            style = MaterialTheme.typography.bodySmall,
                            color = TextSecondary
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        FilledTonalButton(
                            onClick = { openExternalUrl(context, profile.linkedInUrl) },
                            colors = ButtonDefaults.filledTonalButtonColors(containerColor = NavySurface, contentColor = TextPrimary),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Icon(Icons.Default.Work, contentDescription = null, modifier = Modifier.size(14.dp), tint = CyanAccent)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("LinkedIn", fontSize = 12.sp)
                        }

                        FilledTonalButton(
                            onClick = { openExternalUrl(context, profile.portfolioUrl) },
                            colors = ButtonDefaults.filledTonalButtonColors(containerColor = NavySurface, contentColor = TextPrimary),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Icon(Icons.Default.Public, contentDescription = null, modifier = Modifier.size(14.dp), tint = ElectricBlue)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Portfolio Site", fontSize = 12.sp)
                        }
                    }
                }
            }
        }

        // About Biography
        item {
            Column(modifier = Modifier.padding(20.dp)) {
                SectionHeader(
                    tag = "Background & Vision",
                    title = "About Me"
                )

                Spacer(modifier = Modifier.height(12.dp))

                GlassCard {
                    Text(
                        text = profile.aboutBio,
                        style = MaterialTheme.typography.bodyLarge,
                        color = TextPrimary,
                        lineHeight = 24.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                SectionHeader(
                    tag = "Methodology",
                    title = "My Approach to Growth"
                )

                Spacer(modifier = Modifier.height(12.dp))

                GlassCard {
                    Text(
                        text = profile.approachText,
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondary,
                        lineHeight = 22.sp
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        ApproachBadge(label = "Verified Data", icon = Icons.Default.CheckCircle, modifier = Modifier.weight(1f))
                        ApproachBadge(label = "Targeted ICP", icon = Icons.Default.Group, modifier = Modifier.weight(1f))
                        ApproachBadge(label = "Predictable ROI", icon = Icons.Default.TrendingUp, modifier = Modifier.weight(1f))
                    }
                }
            }
        }

        // Categorized Skills
        item {
            Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)) {
                SectionHeader(
                    tag = "Core Competencies",
                    title = "Professional Skills",
                    subtitle = "Specialized capabilities organized by core domain (self-assessed proficiencies)."
                )

                Spacer(modifier = Modifier.height(14.dp))

                val categories = listOf("Digital Marketing", "Lead Generation", "SEO", "Advertising & Analytics")
                categories.forEach { category ->
                    val categorySkills = skills.filter { it.category.equals(category, ignoreCase = true) }
                    if (categorySkills.isNotEmpty()) {
                        Text(
                            text = category,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = CyanAccent,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                        Spacer(modifier = Modifier.height(6.dp))

                        GlassCard {
                            categorySkills.forEachIndexed { index, skill ->
                                Row(
                                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = skill.name,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = PureWhite
                                    )
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(6.dp))
                                            .background(NavySurfaceElevated)
                                            .border(1.dp, NavyBorder, RoundedCornerShape(6.dp))
                                            .padding(horizontal = 8.dp, vertical = 3.dp)
                                    ) {
                                        Text(
                                            text = skill.proficiencyLabel,
                                            style = MaterialTheme.typography.labelSmall,
                                            color = CyanAccent,
                                            fontSize = 11.sp
                                        )
                                    }
                                }
                                if (index < categorySkills.size - 1) {
                                    HorizontalDivider(
                                        modifier = Modifier.padding(vertical = 6.dp),
                                        color = NavyBorder.copy(alpha = 0.5f)
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            }
        }

        // Professional Journey (Experience vs Education)
        item {
            Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)) {
                SectionHeader(
                    tag = "Timeline",
                    title = "Professional Journey"
                )

                Spacer(modifier = Modifier.height(12.dp))

                TabRow(
                    selectedTabIndex = selectedJourneyTab,
                    containerColor = NavySurface,
                    contentColor = PureWhite,
                    indicator = { tabPositions ->
                        TabRowDefaults.SecondaryIndicator(
                            modifier = Modifier.tabIndicatorOffset(tabPositions[selectedJourneyTab]),
                            color = CyanAccent
                        )
                    },
                    modifier = Modifier.clip(RoundedCornerShape(10.dp))
                ) {
                    Tab(
                        selected = selectedJourneyTab == 0,
                        onClick = { selectedJourneyTab = 0 },
                        text = { Text("Experience", fontWeight = FontWeight.Bold) }
                    )
                    Tab(
                        selected = selectedJourneyTab == 1,
                        onClick = { selectedJourneyTab = 1 },
                        text = { Text("Certifications", fontWeight = FontWeight.Bold) }
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                val filteredExperiences = experiences.filter {
                    if (selectedJourneyTab == 0) !it.isEducationOrCert else it.isEducationOrCert
                }

                if (filteredExperiences.isEmpty()) {
                    GlassCard {
                        Text(
                            text = "No records added yet. Manage this section from the Admin Panel.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextSecondary
                        )
                    }
                } else {
                    filteredExperiences.forEach { exp ->
                        GlassCard {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = exp.position,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = PureWhite
                                )
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(ElectricBlueContainer)
                                        .padding(horizontal = 8.dp, vertical = 3.dp)
                                ) {
                                    Text(
                                        text = exp.period,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = CyanAccent
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = exp.organization,
                                style = MaterialTheme.typography.bodySmall,
                                color = CyanAccent,
                                fontWeight = FontWeight.Medium
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = exp.description,
                                style = MaterialTheme.typography.bodyMedium,
                                color = TextSecondary,
                                lineHeight = 20.sp
                            )

                            if (exp.keyContributions.isNotEmpty()) {
                                Spacer(modifier = Modifier.height(8.dp))
                                exp.keyContributions.forEach { item ->
                                    Row(
                                        modifier = Modifier.padding(vertical = 2.dp),
                                        verticalAlignment = Alignment.Top
                                    ) {
                                        Text("• ", color = CyanAccent, fontWeight = FontWeight.Bold)
                                        Text(text = item, style = MaterialTheme.typography.bodySmall, color = TextPrimary)
                                    }
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
            }
        }

        // Contact Button
        item {
            Box(modifier = Modifier.padding(20.dp)) {
                Button(
                    onClick = onContactClick,
                    colors = ButtonDefaults.buttonColors(containerColor = ElectricBlue, contentColor = PureWhite),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth().height(48.dp)
                ) {
                    Text("Get in Touch with Me", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun ApproachBadge(label: String, icon: androidx.compose.ui.graphics.vector.ImageVector, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(NavySurfaceElevated)
            .padding(vertical = 8.dp, horizontal = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(imageVector = icon, contentDescription = null, tint = CyanAccent, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = label, style = MaterialTheme.typography.labelSmall, color = PureWhite, fontSize = 10.sp, textAlign = androidx.compose.ui.text.style.TextAlign.Center)
        }
    }
}
