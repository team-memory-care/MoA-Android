package com.moa.app.designsystem.foundation

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.sp
import com.moa.app.designsystem.R

@Immutable
data class MoaTypography(
    val display1Bold: TextStyle,
    val display1Medium: TextStyle,
    val display1Regular: TextStyle,
    val display2Bold: TextStyle,
    val display2Medium: TextStyle,
    val display2Regular: TextStyle,
    val headLine1Bold: TextStyle,
    val headLine1Medium: TextStyle,
    val headLine1Regular: TextStyle,
    val headLine2Bold: TextStyle,
    val headLine2Medium: TextStyle,
    val headLine2Regular: TextStyle,
    val title1Bold: TextStyle,
    val title1Semibold: TextStyle,
    val title1Medium: TextStyle,
    val title1Regular: TextStyle,
    val title2Bold: TextStyle,
    val title2Semibold: TextStyle,
    val title2Medium: TextStyle,
    val title2Regular: TextStyle,
    val body1Bold: TextStyle,
    val body1Semibold: TextStyle,
    val body1Medium: TextStyle,
    val body1Regular: TextStyle,
    val body2Bold: TextStyle,
    val body2Semibold: TextStyle,
    val body2Medium: TextStyle,
    val body2Regular: TextStyle,
    val caption1Semibold: TextStyle,
    val caption1Medium: TextStyle,
    val caption1Regular: TextStyle,
    val caption2Semibold: TextStyle,
    val caption2Medium: TextStyle,
    val caption2Regular: TextStyle,
)

internal val PretendardFont = FontFamily(
    Font(R.font.pretendard_bold, weight = FontWeight.Bold),
    Font(R.font.pretendard_semibold, weight = FontWeight.SemiBold),
    Font(R.font.pretendard_medium, weight = FontWeight.Medium),
    Font(R.font.pretendard_regular, weight = FontWeight.Normal)
)

internal val PretendardLineHeightStyle = LineHeightStyle(
    alignment = LineHeightStyle.Alignment.Center,
    trim = LineHeightStyle.Trim.None
)

internal val display1 = TextStyle(
    fontFamily = PretendardFont,
    fontSize = 32.sp,
    lineHeight = 46.sp,
    letterSpacing = (-0.5).sp,
    lineHeightStyle = PretendardLineHeightStyle,
)

internal val display2 = TextStyle(
    fontFamily = PretendardFont,
    fontSize = 28.sp,
    lineHeight = 36.sp,
    letterSpacing = (-0.5).sp,
    lineHeightStyle = PretendardLineHeightStyle,
)

internal val headLine1 = TextStyle(
    fontFamily = PretendardFont,
    fontSize = 26.sp,
    lineHeight = 34.sp,
    letterSpacing = (-0.5).sp,
    lineHeightStyle = PretendardLineHeightStyle,
)

internal val headLine2 = TextStyle(
    fontFamily = PretendardFont,
    fontSize = 24.sp,
    lineHeight = 32.sp,
    letterSpacing = (-0.5).sp,
    lineHeightStyle = PretendardLineHeightStyle,
)

internal val title1 = TextStyle(
    fontFamily = PretendardFont,
    fontSize = 22.sp,
    lineHeight = 30.sp,
    letterSpacing = (-0.5).sp,
    lineHeightStyle = PretendardLineHeightStyle,
)

internal val title2 = TextStyle(
    fontFamily = PretendardFont,
    fontSize = 20.sp,
    lineHeight = 28.sp,
    letterSpacing = 0.sp,
    lineHeightStyle = PretendardLineHeightStyle,
)

internal val body1 = TextStyle(
    fontFamily = PretendardFont,
    fontSize = 18.sp,
    lineHeight = 24.sp,
    letterSpacing = 0.sp,
    lineHeightStyle = PretendardLineHeightStyle,
)

internal val body2 = TextStyle(
    fontFamily = PretendardFont,
    fontSize = 16.sp,
    lineHeight = 22.sp,
    letterSpacing = 0.sp,
    lineHeightStyle = PretendardLineHeightStyle,
)

internal val caption1 = TextStyle(
    fontFamily = PretendardFont,
    fontSize = 14.sp,
    lineHeight = 20.sp,
    letterSpacing = 0.sp,
    lineHeightStyle = PretendardLineHeightStyle,
)

internal val caption2 = TextStyle(
    fontFamily = PretendardFont,
    fontSize = 12.sp,
    lineHeight = 18.sp,
    letterSpacing = 0.sp,
    lineHeightStyle = PretendardLineHeightStyle,
)

internal val defaultMoaTypography = MoaTypography(
    display1Bold = display1.copy(fontWeight = FontWeight.Bold),
    display1Medium = display1.copy(fontWeight = FontWeight.Medium),
    display1Regular = display1.copy(fontWeight = FontWeight.Normal),
    display2Bold = display2.copy(fontWeight = FontWeight.Bold),
    display2Medium = display2.copy(fontWeight = FontWeight.Medium),
    display2Regular = display2.copy(fontWeight = FontWeight.Normal),
    headLine1Bold = headLine1.copy(fontWeight = FontWeight.Bold),
    headLine1Medium = headLine1.copy(fontWeight = FontWeight.Medium),
    headLine1Regular = headLine1.copy(fontWeight = FontWeight.Normal),
    headLine2Bold = headLine2.copy(fontWeight = FontWeight.Bold),
    headLine2Medium = headLine2.copy(fontWeight = FontWeight.Medium),
    headLine2Regular = headLine2.copy(fontWeight = FontWeight.Normal),
    title1Bold = title1.copy(fontWeight = FontWeight.Bold),
    title1Semibold = title1.copy(fontWeight = FontWeight.SemiBold),
    title1Medium = title1.copy(fontWeight = FontWeight.Medium),
    title1Regular = title1.copy(fontWeight = FontWeight.Normal),
    title2Bold = title2.copy(fontWeight = FontWeight.Bold),
    title2Semibold = title2.copy(fontWeight = FontWeight.SemiBold),
    title2Medium = title2.copy(fontWeight = FontWeight.Medium),
    title2Regular = title2.copy(fontWeight = FontWeight.Normal),
    body1Bold = body1.copy(fontWeight = FontWeight.Bold),
    body1Semibold = body1.copy(fontWeight = FontWeight.SemiBold),
    body1Medium = body1.copy(fontWeight = FontWeight.Medium),
    body1Regular = body1.copy(fontWeight = FontWeight.Normal),
    body2Bold = body2.copy(fontWeight = FontWeight.Bold),
    body2Semibold = body2.copy(fontWeight = FontWeight.SemiBold),
    body2Medium = body2.copy(fontWeight = FontWeight.Medium),
    body2Regular = body2.copy(fontWeight = FontWeight.Normal),
    caption1Semibold = caption1.copy(fontWeight = FontWeight.SemiBold),
    caption1Medium = caption1.copy(fontWeight = FontWeight.Medium),
    caption1Regular = caption1.copy(fontWeight = FontWeight.Normal),
    caption2Semibold = caption2.copy(fontWeight = FontWeight.SemiBold),
    caption2Medium = caption2.copy(fontWeight = FontWeight.Medium),
    caption2Regular = caption2.copy(fontWeight = FontWeight.Normal),
)

internal val LocalMoaTypography = staticCompositionLocalOf { defaultMoaTypography }
