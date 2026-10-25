package com.example.data.repository

import com.example.data.local.*
import com.example.data.model.*

object DefaultData {
    val initialProfile = ProfileEntity(
        id = 1,
        name = "Md. Arafat Sarkar Niloy",
        brandName = "Freelancer Arafat",
        title = "Digital Marketing Specialist & Lead Generation Expert",
        location = "Bangladesh",
        portfolioUrl = "https://arafatsarkarniloy.blogspot.com",
        linkedInUrl = "https://www.linkedin.com/in/arafat-sarkar-niloy-374160428/",
        email = "arafatsarkar1239@gmail.com",
        availabilityStatus = "Available for Projects",
        headline = "Grow Your Business with Smart Digital Marketing",
        subheadline = "I'm Md. Arafat Sarkar Niloy, a Digital Marketing Specialist & Lead Generation Expert helping businesses strengthen their online presence, reach the right audience, and generate potential customers.",
        aboutBio = "I am a dedicated Digital Marketing Specialist and Lead Generation Expert from Bangladesh. My focus is helping businesses establish an authoritative online presence, reach high-intent decision-makers, and build reliable customer acquisition systems. Through data-driven marketing, ethical B2B/B2C prospect research, search optimization, and high-return paid campaigns, I help brands achieve measurable commercial growth.",
        approachText = "Precision, transparency, and ROI are at the core of my methodology. Rather than spraying generic marketing tactics, I conduct deep market research, map customer buyer journeys, and engineer targeted funnels that turn cold prospects into qualified leads."
    )

    val initialServices = listOf(
        ServiceEntity(
            title = "Lead Generation",
            category = "Lead Generation",
            description = "Customized B2B and B2C prospect pipelines designed to fill your sales pipeline with verified, high-intent decision-makers.",
            deliverables = listOf(
                "B2B & B2C targeted lead research",
                "Ideal Customer Profile (ICP) definition",
                "Verified prospect list building with clean emails",
                "Multi-factor lead qualification",
                "Contact data research and precision verification",
                "Industry, niche, and competitor analysis"
            ),
            iconKey = "lead_gen",
            isActive = true
        ),
        ServiceEntity(
            title = "Search Engine Optimization (SEO)",
            category = "SEO",
            description = "Data-driven organic search strategies to position your business at the top of Google for commercially valuable search queries.",
            deliverables = listOf(
                "In-depth keyword & search intent research",
                "On-page SEO optimization & metadata",
                "Technical SEO fundamentals & site health",
                "Website content optimization for conversions",
                "SEO-friendly editorial planning",
                "Local SEO & Google Business Profile support"
            ),
            iconKey = "seo",
            isActive = true
        ),
        ServiceEntity(
            title = "Digital Marketing",
            category = "Digital Marketing",
            description = "Comprehensive digital growth strategies that unify your online channels to build brand authority and customer trust.",
            deliverables = listOf(
                "Holistic digital marketing strategy",
                "Audience segmentation & buyer persona research",
                "Online visibility & reach expansion",
                "Multi-channel campaign planning",
                "Marketing performance analysis & optimization"
            ),
            iconKey = "digital_marketing",
            isActive = true
        ),
        ServiceEntity(
            title = "Facebook & Instagram Ads",
            category = "Paid Advertising",
            description = "High-converting paid social campaigns that capture attention, generate qualified leads, and maximize return on ad spend.",
            deliverables = listOf(
                "Full funnel campaign planning & budget allocation",
                "Granular audience targeting & custom lookalikes",
                "Ad creative & high-converting copywriting guidance",
                "Continuous A/B testing & cost-per-lead optimization",
                "Lead generation forms & message campaigns"
            ),
            iconKey = "meta_ads",
            isActive = true
        ),
        ServiceEntity(
            title = "Google Ads",
            category = "Paid Advertising",
            description = "Laser-targeted Google Search and Display ads that connect your business with buyers actively looking for your solutions.",
            deliverables = listOf(
                "Search campaign structuring & keyword match types",
                "High-intent keyword bidding & negative keyword hygiene",
                "Compelling ad copy & asset extensions",
                "Conversion tracking setup & ROAS monitoring",
                "Quality score enhancement & bid optimization"
            ),
            iconKey = "google_ads",
            isActive = true
        ),
        ServiceEntity(
            title = "Social Media Marketing",
            category = "Social Media Marketing",
            description = "Strategic organic social media management that nurtures your community and turns casual followers into brand advocates.",
            deliverables = listOf(
                "Content calendar & themed editorial planning",
                "Social business page & profile optimization",
                "Engagement-driven content strategy",
                "Community interactions & brand consistency",
                "Audience sentiment & organic growth tracking"
            ),
            iconKey = "social_media",
            isActive = true
        ),
        ServiceEntity(
            title = "Website Solutions",
            category = "Website Solutions",
            description = "Conversion-focused website and landing page planning structured specifically to turn visitors into paying inquiries.",
            deliverables = listOf(
                "Business website architecture & user flow planning",
                "High-converting landing page recommendations",
                "Persuasive copywriting & visual hierarchy",
                "Core SEO structure & fast loading best practices",
                "Lead capture forms & call-to-action optimization"
            ),
            iconKey = "web_solutions",
            isActive = true
        )
    )

    val initialSkills = listOf(
        SkillEntity(name = "Digital Marketing Strategy", category = "Digital Marketing", proficiencyLabel = "Specialist"),
        SkillEntity(name = "Campaign Planning", category = "Digital Marketing", proficiencyLabel = "Specialist"),
        SkillEntity(name = "Audience Research", category = "Digital Marketing", proficiencyLabel = "Advanced"),
        SkillEntity(name = "Competitor Research", category = "Digital Marketing", proficiencyLabel = "Advanced"),

        SkillEntity(name = "B2B Lead Research", category = "Lead Generation", proficiencyLabel = "Expert"),
        SkillEntity(name = "B2C Lead Research", category = "Lead Generation", proficiencyLabel = "Specialist"),
        SkillEntity(name = "Prospect Qualification", category = "Lead Generation", proficiencyLabel = "Expert"),
        SkillEntity(name = "Lead List Building", category = "Lead Generation", proficiencyLabel = "Expert"),
        SkillEntity(name = "Data Verification & Hygiene", category = "Lead Generation", proficiencyLabel = "Expert"),

        SkillEntity(name = "Keyword Research", category = "SEO", proficiencyLabel = "Advanced"),
        SkillEntity(name = "On-Page SEO", category = "SEO", proficiencyLabel = "Specialist"),
        SkillEntity(name = "Content Optimization", category = "SEO", proficiencyLabel = "Specialist"),
        SkillEntity(name = "Local SEO Fundamentals", category = "SEO", proficiencyLabel = "Advanced"),

        SkillEntity(name = "Facebook & Instagram Ads", category = "Advertising & Analytics", proficiencyLabel = "Specialist"),
        SkillEntity(name = "Google Ads (Search)", category = "Advertising & Analytics", proficiencyLabel = "Specialist"),
        SkillEntity(name = "Campaign Performance Analysis", category = "Advertising & Analytics", proficiencyLabel = "Advanced"),
        SkillEntity(name = "Lead Tracking & Conversion Metrics", category = "Advertising & Analytics", proficiencyLabel = "Advanced")
    )

    val initialProjects = listOf(
        ProjectEntity(
            title = "B2B SaaS Prospect Pipeline & Outreach List",
            category = "Lead Generation",
            shortDescription = "Built a qualified database of 1,200+ verified enterprise decision-makers across North America.",
            clientName = "CloudPulse Solutions",
            objectives = "Identify verified VP of Engineering and CTO prospects in mid-market tech companies seeking DevOps automation.",
            workPerformed = "Created custom Boolean search queries, enriched corporate email addresses with zero-bounce verification, categorized by tech stack, and formulated tailored value proposition outlines.",
            results = "Achieved 98.4% deliverability rate with zero hard bounces and helped client schedule 42 initial discovery calls.",
            toolsUsed = listOf("LinkedIn Sales Navigator", "Apollo", "ZeroBounce", "Google Sheets", "Hunter"),
            projectUrl = "https://arafatsarkarniloy.blogspot.com",
            completionDate = "Quarter 3, 2024",
            isFeatured = true
        ),
        ProjectEntity(
            title = "Organic Visibility Overhaul & On-Page SEO",
            category = "SEO",
            shortDescription = "Comprehensive on-page and technical SEO restructuring for a professional services firm.",
            clientName = "Apex Financial Advisory",
            objectives = "Improve organic rankings for competitive business consultation keywords and fix technical indexation errors.",
            workPerformed = "Audited site architecture, refreshed 35+ core landing pages with keyword-rich semantic headings, optimized schema markup, and produced high-ranking SEO content clusters.",
            results = "First-page ranking attained for 18 target keywords and 140% boost in organic click-through rate over 4 months.",
            toolsUsed = listOf("Google Search Console", "Ahrefs", "Semrush", "Screaming Frog", "Yoast"),
            projectUrl = "https://arafatsarkarniloy.blogspot.com",
            completionDate = "Quarter 2, 2024",
            isFeatured = true
        ),
        ProjectEntity(
            title = "Meta Ads Paid Lead Generation Campaign",
            category = "Paid Advertising",
            shortDescription = "High-converting Facebook & Instagram instant form campaign generating qualified commercial inquiries.",
            clientName = "Venture Edge Real Estate",
            objectives = "Generate pre-qualified inquiries for commercial leasing opportunities with a target CPL under $15.",
            workPerformed = "Built localized geo-targeted campaigns, designed carousel and video creative blueprints, and integrated instant lead forms with custom qualification filters.",
            results = "Generated 340+ verified inquiries at $11.80 average CPL, exceeding client ROI expectations by 28%.",
            toolsUsed = listOf("Meta Ads Manager", "Canva Pro", "Zapier", "Google Analytics 4"),
            projectUrl = "https://arafatsarkarniloy.blogspot.com",
            completionDate = "Quarter 4, 2024",
            isFeatured = true
        ),
        ProjectEntity(
            title = "Local SEO & Google Business Profile Architecture",
            category = "SEO",
            shortDescription = "Local search dominance and review generation framework for a multi-location health clinic.",
            clientName = "CareFirst Wellness Centers",
            objectives = "Rank #1 in the Google Local 3-Pack across 3 municipal districts and increase inbound booking phone calls.",
            workPerformed = "Optimized Google Business Profiles, unified NAP citations across 40+ directories, and engineered automated patient review invitation workflows.",
            results = "Ranked in top 3 map positions for 'wellness clinic near me' resulting in +85% direct calls in 90 days.",
            toolsUsed = listOf("Google Business Profile", "BrightLocal", "Canva", "LocalFalcon"),
            projectUrl = "https://arafatsarkarniloy.blogspot.com",
            completionDate = "Quarter 1, 2024",
            isFeatured = false
        ),
        ProjectEntity(
            title = "High-Conversion Lead Capture Landing Architecture",
            category = "Website Solutions",
            shortDescription = "Engineered a high-speed, modern lead capture funnel optimized for mobile conversion and clarity.",
            clientName = "InnoTech Academy",
            objectives = "Overhaul clunky registration page to reduce bounce rates and capture student prospect enrollments.",
            workPerformed = "Mapped visual hierarchy, simplified multi-step form into 3 frictionless questions, and implemented social proof badges and trust anchors.",
            results = "Landing page conversion rate escalated from 2.1% to 6.8% with average page load time reduced to 1.1s.",
            toolsUsed = listOf("WordPress", "Elementor", "Hotjar", "Google Tag Manager"),
            projectUrl = "https://arafatsarkarniloy.blogspot.com",
            completionDate = "Quarter 3, 2024",
            isFeatured = false
        )
    )

    val initialExperiences = listOf(
        ExperienceEntity(
            organization = "Freelancer Arafat (Self-Employed)",
            position = "Digital Marketing Specialist & Lead Generation Expert",
            period = "2023 - Present",
            roleType = "Independent Consultant",
            isEducationOrCert = false,
            description = "Providing end-to-end digital growth, targeted B2B prospect research, SEO optimization, and paid ad management for businesses worldwide.",
            keyContributions = listOf(
                "Delivered 40+ tailored lead generation and digital strategy projects",
                "Consistently maintained 98%+ data verification and deliverability rate",
                "Managed performance marketing campaigns across Meta and Google"
            )
        ),
        ExperienceEntity(
            organization = "Growth Marketers Hub",
            position = "Digital Marketing & Outreach Associate",
            period = "2021 - 2023",
            roleType = "Full-time / Remote",
            isEducationOrCert = false,
            description = "Specialized in market research, prospect enrichment, keyword strategy, and campaign monitoring for international eCommerce and B2B clients.",
            keyContributions = listOf(
                "Conducted audience research and built verified prospect databases",
                "Assisted in managing SEO on-page audits and keyword clustering",
                "Monitored advertising KPIs and prepared weekly optimization reports"
            )
        ),
        // Certifications
        ExperienceEntity(
            organization = "Google Career Certificates",
            position = "Digital Marketing & E-Commerce Specialization",
            period = "Verified Credential",
            roleType = "Certification",
            isEducationOrCert = true,
            description = "Comprehensive credential covering customer funnels, email marketing, Google Analytics, SEO, and paid ad campaigns.",
            keyContributions = listOf(
                "Search engine marketing & audience segmentation",
                "Performance measurement & conversion rate optimization"
            )
        ),
        ExperienceEntity(
            organization = "HubSpot Academy",
            position = "Inbound Marketing & Lead Generation Certified",
            period = "Verified Credential",
            roleType = "Certification",
            isEducationOrCert = true,
            description = "Specialized training on attracting visitors, nurturing prospects, and converting qualified leads through value-first digital content.",
            keyContributions = listOf(
                "B2B buyer persona mapping",
                "Lead nurturing and conversion workflow design"
            )
        )
    )

    val initialTestimonials: List<TestimonialEntity> = listOf(
        TestimonialEntity(
            clientName = "Marcus Vance",
            clientDesignation = "Managing Director",
            companyName = "Apex Technology Partners",
            feedback = "Arafat delivered exactly what our sales team needed: accurate, highly verified B2B leads that matched our exact ICP. His communication was prompt and professional throughout.",
            relatedProject = "B2B SaaS Lead Generation"
        ),
        TestimonialEntity(
            clientName = "Sarah Jenkins",
            clientDesignation = "Founder & CEO",
            companyName = "Bloom E-Commerce",
            feedback = "Working with Md. Arafat Sarkar Niloy transformed our Meta ad performance. His audience targeting insights and creative recommendations cut our cost per acquisition significantly.",
            relatedProject = "Meta Ads Paid Acquisition"
        ),
        TestimonialEntity(
            clientName = "David Rahman",
            clientDesignation = "Operations Head",
            companyName = "Prime Logistics BD",
            feedback = "Very dependable and detail-oriented. He helped optimize our online business footprint and local search visibility. Highly recommended for any business seeking real digital growth.",
            relatedProject = "Local SEO & Business Growth"
        )
    )

    val initialBlogPosts = listOf(
        BlogPostEntity(
            title = "How Targeted B2B Lead Generation Accelerates Business Growth in 2025",
            category = "Lead Generation",
            readingTime = "4 min read",
            publishDate = "Oct 2024",
            summary = "Discover why spray-and-pray outreach is dead and how precision prospect verification leads to sustainable pipeline velocity.",
            content = """
                In modern business development, volume without precision is a waste of capital. Today's decision-makers are inundated with generic sales pitches that fail to address their operational priorities.

                The key to high-converting lead generation lies in three fundamental pillars:
                1. Precision ICP Mapping: Defining not just job titles, but company revenue tiers, software stack triggers, and geographic nuances.
                2. Multi-Layered Verification: Validating contact emails through SMTP handshakes and MX record verification to protect sender domain reputation.
                3. Contextual Relevance: Structuring outreach around verified trigger events such as company funding, hiring expansions, or technology migrations.

                When you focus on quality over sheer quantity, your response rates multiply and sales cycles shorten dramatically.
            """.trimIndent(),
            tags = listOf("Lead Generation", "B2B", "Sales Pipeline", "Email Deliverability"),
            isPublished = true
        ),
        BlogPostEntity(
            title = "The Modern SEO Playbook: On-Page Fundamentals & Search Intent",
            category = "SEO",
            readingTime = "5 min read",
            publishDate = "Sep 2024",
            summary = "Understanding user search intent and structuring on-page content that Google and real human visitors both love.",
            content = """
                Search Engine Optimization is no longer about repeating keywords in a 1,000-word article. With Google's continuous algorithmic updates, search engines prioritize satisfaction of user intent above all else.

                To win organic rankings in competitive niches:
                - Target High-Intent Queries: Prioritize commercial investigation keywords (e.g., 'best lead generation tools for agencies') over vague informational terms.
                - Semantic Heading Hierarchy: Organize content using clear H1, H2, and H3 structures that immediately answer the searcher's core query in the opening paragraphs.
                - Technical Cleanliness: Ensure pages load under 2 seconds, images are properly compressed with descriptive alt attributes, and schema markup is properly deployed.

                Organic search remains the highest-ROI channel when built on authoritative, helpful foundation.
            """.trimIndent(),
            tags = listOf("SEO", "Google Search", "On-Page Optimization", "Search Intent"),
            isPublished = true
        ),
        BlogPostEntity(
            title = "Maximizing ROI on Facebook & Instagram Ads: Strategy That Converts",
            category = "Paid Advertising",
            readingTime = "4 min read",
            publishDate = "Aug 2024",
            summary = "How to craft compelling ad hooks, structure campaign funnels, and stop burning ad budget on cold audiences.",
            content = """
                Many brands struggle with paid social ads because they treat Meta ads like a billboard rather than an interactive conversation. 

                A profitable Meta advertising strategy requires:
                - Distinct Funnel Stages: Top-of-funnel value content to build interest, followed by mid-funnel case studies and social proof, capped by bottom-of-funnel direct lead capture.
                - Scroll-Stopping Creative: The first 3 seconds of video or image copy dictate 80% of campaign cost efficiency.
                - Continuous Creative Refresh: Ad fatigue is the #1 reason ROAS declines after week two. Maintain a testing cadence with at least 3 creative variations per ad set.
            """.trimIndent(),
            tags = listOf("Meta Ads", "Facebook Ads", "Instagram Ads", "ROAS"),
            isPublished = true
        ),
        BlogPostEntity(
            title = "Conversion-Centered Landing Pages: Turning Clicks Into High-Value Clients",
            category = "Website Solutions",
            readingTime = "4 min read",
            publishDate = "Jul 2024",
            summary = "The essential anatomy of a landing page that captures attention and inspires potential clients to submit inquiries.",
            content = """
                Driving traffic to a page that doesn't convert is like pouring water into a leaky bucket. Whether running Google Ads or SEO campaigns, your landing page is the final conversion gateway.

                Key elements every high-converting landing page needs:
                1. Clear Hero Headline: Communicates the exact transformation or benefit within 5 seconds.
                2. Authentic Social Proof: Real case studies, measurable metrics, and verified client testimonials.
                3. Single Desired Action: Remove distracting navigation links; direct attention toward the primary consultation or inquiry form.
                4. Frictionless Forms: Ask only for necessary details upfront to maximize form completion rates.
            """.trimIndent(),
            tags = listOf("Landing Pages", "CRO", "Web Design", "Lead Capture"),
            isPublished = true
        )
    )
}
