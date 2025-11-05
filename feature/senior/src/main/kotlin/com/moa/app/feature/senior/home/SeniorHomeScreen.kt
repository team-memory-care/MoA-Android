package com.moa.app.feature.senior.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter.Companion.tint
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moa.app.designsystem.R
import com.moa.app.designsystem.component.core.button.MaButton
import com.moa.app.designsystem.theme.MoaTheme

@Composable
fun SeniorHomeScreen(
    modifier: Modifier = Modifier,
) {
    SeniorHomeScreenContent()
}

@Composable
private fun SeniorHomeScreenContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MoaTheme.colors.white),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(start = 20.dp, end = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.img_moa_logo),
                contentDescription = null,
                colorFilter = tint(MoaTheme.colors.coolGray60),
                modifier = Modifier.size(60.dp, 24.dp)
            )

            Image(
                imageVector = ImageVector.vectorResource(R.drawable.ic_setting),
                contentDescription = null,
                modifier = Modifier
                    .clickable(
                        onClick = {},
                        role = Role.Button,
                        interactionSource = null,
                        indication = null
                    )
                    .padding(12.dp)
            )
        }

        Column(
            modifier = Modifier.padding(horizontal = 20.dp),
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .background(
                        color = MoaTheme.colors.coolGray99,
                        shape = RoundedCornerShape(24.dp),
                    ),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp, start = 24.dp, end = 26.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp),
                ) {
                    Image(
                        imageVector = ImageVector.vectorResource(R.drawable.img_senior_home_sun),
                        contentDescription = null,
                        modifier = Modifier.size(32.dp)
                    )

                    Text(
                        text = "안녕하세요,\n김순자님\n오늘 하루도 활기차게\n시작해볼까요?",
                        color = MoaTheme.colors.black,
                        style = MoaTheme.typography.display1Bold,
                    )
                }

                Image(
                    imageVector = ImageVector.vectorResource(R.drawable.img_senior_home_character),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .size(170.dp, 140.dp)
                        .offset(y = 32.dp),
                )
            }

            Spacer(Modifier.height(32.dp))


            MaButton(
                onClick = {},
                shape = RoundedCornerShape(24.dp),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 32.dp, horizontal = 24.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = "오늘의 퀴즈 풀러가기",
                        style = MoaTheme.typography.title1Bold,
                    )

                    Image(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_chevron_left),
                        contentDescription = null,
                        colorFilter = tint(MoaTheme.colors.white),
                    )
                }
            }


            Spacer(Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(166.dp)
                    .padding(bottom = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(24.dp))
                        .clickable(
                            onClick = {},
                            role = Role.Button,
                        )
                        .background(MoaTheme.colors.lightBlue500)
                        .padding(start = 16.dp)
                ) {
                    Image(
                        imageVector = ImageVector.vectorResource(R.drawable.img_senior_home_quiz),
                        contentDescription = null,
                        modifier = Modifier.align(Alignment.CenterEnd),
                    )

                    Box(
                        modifier = Modifier
                            .padding(top = 14.dp)
                            .background(
                                color = MoaTheme.colors.white,
                                shape = RoundedCornerShape(14.dp)
                            )
                            .size(40.dp)
                            .align(Alignment.TopStart),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_senior_quiz),
                            contentDescription = null,
                        )
                    }

                    Text(
                        text = "퀴즈",
                        color = MoaTheme.colors.white,
                        style = MoaTheme.typography.title1Bold,
                        modifier = Modifier
                            .padding(bottom = 18.dp)
                            .align(Alignment.BottomStart)
                    )
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(24.dp))
                        .clickable(
                            onClick = {},
                            role = Role.Button,
                        )
                        .background(MoaTheme.colors.orange500)
                        .padding(start = 16.dp)
                ) {
                    Image(
                        imageVector = ImageVector.vectorResource(R.drawable.img_senior_home_report),
                        contentDescription = null,
                        modifier = Modifier.align(Alignment.CenterEnd)
                    )

                    Box(
                        modifier = Modifier
                            .padding(top = 14.dp)
                            .background(
                                color = MoaTheme.colors.white,
                                shape = RoundedCornerShape(14.dp)
                            )
                            .size(40.dp)
                            .align(Alignment.TopStart),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_senior_report),
                            contentDescription = null,
                        )
                    }

                    Text(
                        text = "리포트",
                        color = MoaTheme.colors.white,
                        style = MoaTheme.typography.title1Bold,
                        modifier = Modifier
                            .padding(bottom = 18.dp)
                            .align(Alignment.BottomStart)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    SeniorHomeScreenContent()
}
