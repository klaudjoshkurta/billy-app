package com.shkurta.billy.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.sp
import com.shkurta.billy.R

val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

val GeistFont = GoogleFont("Geist")
val GeistMonoFont = GoogleFont("Geist Mono")

val GeistFontFamily = FontFamily(
    Font(googleFont = GeistFont, fontProvider = provider, weight = FontWeight.Normal),
    Font(googleFont = GeistFont, fontProvider = provider, weight = FontWeight.Medium),
    Font(googleFont = GeistFont, fontProvider = provider, weight = FontWeight.SemiBold),
    Font(googleFont = GeistFont, fontProvider = provider, weight = FontWeight.Bold)
)

val GeistMonoFontFamily = FontFamily(
    Font(googleFont = GeistMonoFont, fontProvider = provider, weight = FontWeight.Normal),
    Font(googleFont = GeistMonoFont, fontProvider = provider, weight = FontWeight.Medium)
)

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = GeistFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    titleLarge = TextStyle(
        fontFamily = GeistFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = GeistMonoFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = GeistFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 28.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp
    )
)
