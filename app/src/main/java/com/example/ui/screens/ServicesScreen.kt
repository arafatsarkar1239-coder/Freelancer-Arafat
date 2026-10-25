package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ServiceItem
import com.example.ui.components.GlassCard
import com.example.ui.components.SectionHeader
import com.example.ui.theme.*

@Composable
fun ServicesScreen(
    services: List<ServiceItem>,
    onDiscussService: (String) -> Unit
) {
    var selectedCategory by remember { mutableStateOf("All") }

    val categories = listOf("All", "Lead Generation", "SEO", "Digital Marketing", "Paid Advertising", "Social Media Marketing", "Website Solutions")

    val filteredServices = services.filter { service ->
        service.isActive && (selectedCategory == "All" || service.category.equals(selectedCategory, ignoreCase = true))
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("services_screen_content"),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 20.dp)
    ) {
        item {
            SectionHeader(
                tag = "Solutions & Services",
                title = "What I Offer",
                subtitle = "Comprehensive digital growth and lead acquisition systems customized for your industry."
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Filter pills
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(categories) { cat ->
                    val isSelected = selectedCategory == cat
                    FilterChip(
                        selected = isSelected,
                        onClick = { selectedCategory = cat },
                        label = {
                            Text(
                                text = cat,
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = ElectricBlueContainer,
                            selectedLabelColor = CyanAccent,
                            containerColor = NavySurface,
                            labelColor = TextSecondary
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            enabled = true,
                            selected = isSelected,
                            borderColor = if (isSelected) CyanAccent else NavyBorder
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        items(filteredServices) { service ->
            ServiceCardDetailed(
                service = service,
                onDiscuss = { onDiscussService(service.title) }
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        if (filteredServices.isEmpty()) {
            item {
                GlassCard {
                    Text(
                        text = "No services found in this category. You can activate or add services in the Admin Panel.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondary
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Composable
fun ServiceCardDetailed(
    service: ServiceItem,
    onDiscuss: () -> Unit
) {
    GlassCard {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(ElectricBlueContainer)
                    .border(1.dp, ElectricBlue.copy(alpha = 0.5f), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = when (service.iconKey) {
                        "lead_gen" -> Icons.Default.FilterList
                        "seo" -> Icons.Default.Search
                        "digital_marketing" -> Icons.Default.Lightbulb
                        "meta_ads" -> Icons.Default.Campaign
                        "google_ads" -> Icons.Default.AdsClick
                        "social_media" -> Icons.Default.Share
                        else -> Icons.Default.Web
                    },
                    contentDescription = null,
                    tint = CyanAccent,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = service.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = PureWhite
                )
                Text(
                    text = service.category,
                    style = MaterialTheme.typography.labelSmall,
                    color = CyanAccent
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = service.description,
            style = MaterialTheme.typography.bodyMedium,
            color = TextSecondary,
            lineHeight = 22.sp
        )

        if (service.deliverables.isNotEmpty()) {
            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Key Deliverables:",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = PureWhite
            )

            Spacer(modifier = Modifier.height(6.dp))

            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                service.deliverables.forEach { item ->
                    Row(verticalAlignment = Alignment.Top) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = CyanAccent,
                            modifier = Modifier
                                .size(14.dp)
                                .padding(top = 3.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = item,
                            style = MaterialTheme.typography.bodySmall,
                            color = TextPrimary,
                            lineHeight = 18.sp
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onDiscuss,
            colors = ButtonDefaults.buttonColors(
                containerColor = ElectricBlue,
                contentColor = PureWhite
            ),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp)
                .testTag("service_discuss_${service.id}")
        ) {
            Text("Discuss Your Project", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.width(6.dp))
            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(16.dp))
        }
    }
}
