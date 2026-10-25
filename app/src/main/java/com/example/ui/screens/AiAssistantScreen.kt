package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import com.example.AiGenerationState
import com.example.FreelancerViewModel
import com.example.ui.components.GlassCard
import com.example.ui.components.SectionHeader
import com.example.ui.theme.*

@Composable
fun AiAssistantScreen(
    viewModel: FreelancerViewModel,
    aiState: AiGenerationState
) {
    val context = LocalContext.current
    var selectedTask by remember { mutableStateOf("Client Inquiry Response") }
    var inputPrompt by remember { mutableStateOf("") }

    val taskPresets = listOf(
        "Client Inquiry Response",
        "Service Description",
        "SEO Meta & Keywords",
        "LinkedIn Thought Post",
        "Blog Post Outline"
    )

    fun applyPreset(task: String) {
        selectedTask = task
        inputPrompt = when (task) {
            "Client Inquiry Response" -> "A prospect named Alex from a B2B SaaS startup is asking for 500 verified CTO leads in North America with a $2,000 budget. Draft a confident, consultative response explaining our verification process and offering a 15-minute alignment call."
            "Service Description" -> "Draft a high-converting, value-packed service description for 'Targeted B2B Lead Research & Prospect Enrichment' highlighting zero-bounce guarantee and custom ICP mapping."
            "SEO Meta & Keywords" -> "Generate 3 high-CTR SEO title tags, 1 persuasive meta description (under 155 chars), and 6 high-intent keyword ideas for Freelancer Arafat's Lead Generation Services."
            "LinkedIn Thought Post" -> "Write a sharp, engaging LinkedIn post breaking down why 90% of cold outbound emails end up in spam and how multi-factor SMTP verification saves domain reputation."
            "Blog Post Outline" -> "Create an in-depth blog post outline on 'How Small Businesses Can Outrank National Competitors Through Hyper-Local SEO in 2025'."
            else -> ""
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("ai_assistant_screen_content"),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 20.dp)
    ) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        Brush.linearGradient(listOf(ElectricBlueContainer, DeepNavy))
                    )
                    .border(1.dp, CyanAccent.copy(alpha = 0.4f), RoundedCornerShape(16.dp))
                    .padding(18.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Brush.linearGradient(listOf(ElectricBlue, CyanAccent))),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = CharcoalBlack, modifier = Modifier.size(24.dp))
                    }
                    Spacer(modifier = Modifier.width(14.dp))
                    Column {
                        Text("Arafat AI Studio", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = PureWhite)
                        Text("Personal branding & marketing assistant powered by Gemini 3.5 Flash", style = MaterialTheme.typography.bodySmall, color = CyanAccent)
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            SectionHeader(
                tag = "Brand Automation",
                title = "AI Copywriting & Proposals",
                subtitle = "Select a quick branding prompt or enter custom requirements."
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Presets row
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(taskPresets) { preset ->
                    val isSel = selectedTask == preset
                    FilterChip(
                        selected = isSel,
                        onClick = { applyPreset(preset) },
                        label = { Text(preset, fontSize = 12.sp, fontWeight = if (isSel) FontWeight.Bold else FontWeight.Normal) },
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

            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            GlassCard {
                Text(
                    text = "Prompt / Brief ($selectedTask)",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = PureWhite
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = inputPrompt,
                    onValueChange = { inputPrompt = it },
                    placeholder = { Text("Enter project details, client question, or marketing brief...", color = TextMuted) },
                    minLines = 4,
                    maxLines = 8,
                    colors = contactTextFieldColors(),
                    modifier = Modifier.fillMaxWidth().testTag("ai_prompt_input")
                )

                Spacer(modifier = Modifier.height(14.dp))

                Button(
                    onClick = {
                        if (inputPrompt.isNotBlank()) {
                            viewModel.generateAiContent(selectedTask, inputPrompt)
                        } else {
                            Toast.makeText(context, "Please enter a prompt first", Toast.LENGTH_SHORT).show()
                        }
                    },
                    enabled = aiState !is AiGenerationState.Loading,
                    colors = ButtonDefaults.buttonColors(containerColor = ElectricBlue, contentColor = PureWhite),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth().height(48.dp).testTag("ai_generate_button")
                ) {
                    if (aiState is AiGenerationState.Loading) {
                        CircularProgressIndicator(modifier = Modifier.size(20.dp), color = PureWhite, strokeWidth = 2.dp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Drafting Content with Gemini...")
                    } else {
                        Icon(Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(18.dp), tint = CyanAccent)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Generate with Arafat AI", fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        // Output section
        item {
            when (aiState) {
                is AiGenerationState.Idle -> {
                    GlassCard {
                        Text(
                            text = "Choose a preset above and tap 'Generate' to create high-converting marketing copy, client replies, or SEO plans.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextSecondary
                        )
                    }
                }
                is AiGenerationState.Loading -> {
                    GlassCard {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxWidth().padding(20.dp)
                        ) {
                            CircularProgressIndicator(color = CyanAccent)
                            Spacer(modifier = Modifier.height(12.dp))
                            Text("Arafat AI is formulating strategy...", color = PureWhite, fontWeight = FontWeight.SemiBold)
                            Text("Consulting digital marketing guidelines & tone of voice.", color = TextSecondary, fontSize = 12.sp)
                        }
                    }
                }
                is AiGenerationState.Success -> {
                    GlassCard(
                        borderColor = CyanAccent.copy(alpha = 0.5f)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Generated Strategy Output",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = CyanAccent
                            )

                            IconButton(
                                onClick = {
                                    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                    val clip = ClipData.newPlainText("Arafat AI Output", aiState.result)
                                    clipboard.setPrimaryClip(clip)
                                    Toast.makeText(context, "Copied to clipboard!", Toast.LENGTH_SHORT).show()
                                }
                            ) {
                                Icon(Icons.Default.ContentCopy, contentDescription = "Copy", tint = CyanAccent)
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        SelectionContainerText(text = aiState.result)

                        Spacer(modifier = Modifier.height(14.dp))

                        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            OutlinedButton(
                                onClick = { viewModel.clearAiState() },
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = TextSecondary),
                                border = androidx.compose.foundation.BorderStroke(1.dp, NavyBorder),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Clear")
                            }

                            Button(
                                onClick = {
                                    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                    val clip = ClipData.newPlainText("Arafat AI Output", aiState.result)
                                    clipboard.setPrimaryClip(clip)
                                    Toast.makeText(context, "Copied to clipboard! You can paste in Admin or Email.", Toast.LENGTH_LONG).show()
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = CyanAccent, contentColor = CharcoalBlack),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Copy Text", fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
                is AiGenerationState.Error -> {
                    GlassCard(borderColor = ErrorRed.copy(alpha = 0.5f)) {
                        Row(verticalAlignment = Alignment.Top) {
                            Icon(Icons.Default.ErrorOutline, contentDescription = null, tint = ErrorRed)
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text("Generation Notice", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = PureWhite)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(aiState.message, style = MaterialTheme.typography.bodyMedium, color = TextSecondary)
                                if (aiState.isKeyMissing) {
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        "To configure live Gemini AI:\n1. Open the Secrets panel in AI Studio.\n2. Set GEMINI_API_KEY with your Google AI Studio API key.",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = CyanAccent
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Composable
fun SelectionContainerText(text: String) {
    androidx.compose.foundation.text.selection.SelectionContainer {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = PureWhite,
            lineHeight = 22.sp
        )
    }
}
