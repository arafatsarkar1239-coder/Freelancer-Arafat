package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.InquirySubmissionState
import com.example.data.model.Profile
import com.example.ui.components.GlassCard
import com.example.ui.components.SectionHeader
import com.example.ui.components.openExternalUrl
import com.example.ui.components.sendEmailIntent
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactScreen(
    profile: Profile,
    prefilledService: String?,
    inquiryState: InquirySubmissionState,
    onSubmitInquiry: (name: String, email: String, company: String, service: String, budget: String, details: String, preferredContact: String) -> Unit,
    onResetInquiryState: () -> Unit
) {
    val context = LocalContext.current

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var company by remember { mutableStateOf("") }
    var selectedService by remember { mutableStateOf(prefilledService ?: "Lead Generation") }
    var selectedBudget by remember { mutableStateOf("$1,000 - $3,000") }
    var details by remember { mutableStateOf("") }
    var preferredContact by remember { mutableStateOf("Email") }
    var serviceDropdownExpanded by remember { mutableStateOf(false) }

    val serviceOptions = listOf(
        "Lead Generation",
        "Search Engine Optimization (SEO)",
        "Digital Marketing",
        "Facebook & Instagram Ads",
        "Google Ads",
        "Social Media Marketing",
        "Website Solutions"
    )

    val budgetOptions = listOf(
        "Under $1,000",
        "$1,000 - $3,000",
        "$3,000 - $5,000",
        "$5,000+",
        "To Be Discussed"
    )

    val contactMethods = listOf("Email", "LinkedIn", "WhatsApp")

    // Update if prefilledService changes
    LaunchedEffect(prefilledService) {
        if (!prefilledService.isNullOrBlank()) {
            selectedService = prefilledService
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("contact_screen_content"),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 20.dp)
    ) {
        item {
            SectionHeader(
                tag = "Client Acquisition & Contact",
                title = "Let's Discuss Your Project",
                subtitle = "Have a project in mind? Share your objectives below and I'll recommend the optimal digital strategy."
            )

            Spacer(modifier = Modifier.height(16.dp))
        }

        // Success State
        if (inquiryState is InquirySubmissionState.Success) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(
                            Brush.linearGradient(listOf(NavySurfaceElevated, DeepNavy))
                        )
                        .border(1.dp, SuccessGreen, RoundedCornerShape(16.dp))
                        .padding(22.dp)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .clip(RoundedCornerShape(28.dp))
                                .background(SuccessGreen.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = SuccessGreen,
                                modifier = Modifier.size(36.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        Text(
                            text = "Inquiry Successfully Saved!",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = PureWhite
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "Reference ID: #LGN-${inquiryState.inquiryId}\nThank you, $name. I have logged your project inquiry and will personally review your requirements within 24 hours.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextSecondary,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                            lineHeight = 22.sp
                        )

                        Spacer(modifier = Modifier.height(18.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Button(
                                onClick = {
                                    sendEmailIntent(
                                        context,
                                        profile.email,
                                        "Project Inquiry [#LGN-${inquiryState.inquiryId}] - $selectedService"
                                    )
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = ElectricBlue),
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(Icons.Default.Email, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Email Directly")
                            }

                            OutlinedButton(
                                onClick = {
                                    onResetInquiryState()
                                    name = ""
                                    email = ""
                                    company = ""
                                    details = ""
                                },
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = CyanAccent),
                                border = androidx.compose.foundation.BorderStroke(1.dp, NavyBorder),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("New Inquiry")
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
        } else {
            // The Form
            item {
                GlassCard {
                    Text(
                        text = "Project Inquiry Form",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = PureWhite
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Client Name
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Your Full Name *") },
                        leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = CyanAccent) },
                        singleLine = true,
                        colors = contactTextFieldColors(),
                        modifier = Modifier.fillMaxWidth().testTag("inquiry_name_input")
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Client Email
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email Address *") },
                        leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = CyanAccent) },
                        singleLine = true,
                        colors = contactTextFieldColors(),
                        modifier = Modifier.fillMaxWidth().testTag("inquiry_email_input")
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Company Name
                    OutlinedTextField(
                        value = company,
                        onValueChange = { company = it },
                        label = { Text("Company or Business Name (Optional)") },
                        leadingIcon = { Icon(Icons.Default.Business, contentDescription = null, tint = CyanAccent) },
                        singleLine = true,
                        colors = contactTextFieldColors(),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Requested Service Selector
                    Text(
                        text = "Requested Service",
                        style = MaterialTheme.typography.labelMedium,
                        color = PureWhite,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    ExposedDropdownMenuBox(
                        expanded = serviceDropdownExpanded,
                        onExpandedChange = { serviceDropdownExpanded = !serviceDropdownExpanded },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            value = selectedService,
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = serviceDropdownExpanded) },
                            colors = contactTextFieldColors(),
                            modifier = Modifier
                                .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                                .fillMaxWidth()
                        )
                        ExposedDropdownMenu(
                            expanded = serviceDropdownExpanded,
                            onDismissRequest = { serviceDropdownExpanded = false },
                            containerColor = DeepNavy
                        ) {
                            serviceOptions.forEach { opt ->
                                DropdownMenuItem(
                                    text = { Text(opt, color = PureWhite) },
                                    onClick = {
                                        selectedService = opt
                                        serviceDropdownExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Estimated Budget Chips
                    Text(
                        text = "Estimated Budget",
                        style = MaterialTheme.typography.labelMedium,
                        color = PureWhite,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        budgetOptions.take(3).forEach { bgt ->
                            val isSel = selectedBudget == bgt
                            FilterChip(
                                selected = isSel,
                                onClick = { selectedBudget = bgt },
                                label = { Text(bgt, fontSize = 11.sp) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = ElectricBlueContainer,
                                    selectedLabelColor = CyanAccent,
                                    containerColor = NavySurface,
                                    labelColor = TextSecondary
                                ),
                                border = FilterChipDefaults.filterChipBorder(
                                    enabled = true,
                                    selected = isSel,
                                    borderColor = if (isSel) CyanAccent else NavyBorder
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Project Details
                    OutlinedTextField(
                        value = details,
                        onValueChange = { details = it },
                        label = { Text("Project Details & Growth Goals *") },
                        placeholder = { Text("Describe your target market, pain points, or timeline...", color = TextMuted) },
                        minLines = 4,
                        maxLines = 6,
                        colors = contactTextFieldColors(),
                        modifier = Modifier.fillMaxWidth().testTag("inquiry_details_input")
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Preferred Contact Method
                    Text(
                        text = "Preferred Follow-up Method",
                        style = MaterialTheme.typography.labelMedium,
                        color = PureWhite,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        contactMethods.forEach { method ->
                            val isMethodSel = preferredContact == method
                            FilterChip(
                                selected = isMethodSel,
                                onClick = { preferredContact = method },
                                label = { Text(method, fontSize = 12.sp) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = ElectricBlueContainer,
                                    selectedLabelColor = CyanAccent,
                                    containerColor = NavySurface,
                                    labelColor = TextSecondary
                                )
                            )
                        }
                    }

                    if (inquiryState is InquirySubmissionState.Error) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = inquiryState.message,
                            color = ErrorRed,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Submit Button
                    Button(
                        onClick = {
                            onSubmitInquiry(
                                name,
                                email,
                                company,
                                selectedService,
                                selectedBudget,
                                details,
                                preferredContact
                            )
                        },
                        enabled = inquiryState !is InquirySubmissionState.Submitting,
                        colors = ButtonDefaults.buttonColors(containerColor = ElectricBlue, contentColor = PureWhite),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .testTag("inquiry_submit_button")
                    ) {
                        if (inquiryState is InquirySubmissionState.Submitting) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                color = PureWhite,
                                strokeWidth = 2.dp
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Saving Inquiry...")
                        } else {
                            Icon(Icons.Default.Send, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Submit Project Inquiry", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        // Direct Channels & Social Proof
        item {
            GlassCard {
                Text(
                    text = "Direct Contact & Profiles",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = PureWhite
                )

                Spacer(modifier = Modifier.height(10.dp))

                ContactInfoRow(
                    icon = Icons.Default.Email,
                    label = "Direct Email",
                    value = profile.email,
                    onClick = { sendEmailIntent(context, profile.email) }
                )

                Spacer(modifier = Modifier.height(8.dp))

                ContactInfoRow(
                    icon = Icons.Default.Work,
                    label = "LinkedIn Profile",
                    value = "linkedin.com/in/arafat-sarkar-niloy-374160428",
                    onClick = { openExternalUrl(context, profile.linkedInUrl) }
                )

                Spacer(modifier = Modifier.height(8.dp))

                ContactInfoRow(
                    icon = Icons.Default.Public,
                    label = "Blogspot Portfolio",
                    value = "arafatsarkarniloy.blogspot.com",
                    onClick = { openExternalUrl(context, profile.portfolioUrl) }
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Composable
fun ContactInfoRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(NavySurface)
            .border(1.dp, NavyBorder, RoundedCornerShape(8.dp))
            .clickable { onClick() }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = CyanAccent, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = label, style = MaterialTheme.typography.labelSmall, color = TextMuted)
            Text(text = value, style = MaterialTheme.typography.bodyMedium, color = PureWhite, fontWeight = FontWeight.Medium)
        }
        Icon(imageVector = Icons.Default.ChevronRight, contentDescription = null, tint = TextMuted, modifier = Modifier.size(18.dp))
    }
}

@Composable
fun contactTextFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor = CyanAccent,
    unfocusedBorderColor = NavyBorder,
    focusedTextColor = PureWhite,
    unfocusedTextColor = PureWhite,
    focusedLabelColor = CyanAccent,
    unfocusedLabelColor = TextSecondary,
    cursorColor = CyanAccent
)
