package com.moa.app.feature.guardian.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moa.app.designsystem.R
import com.moa.app.designsystem.theme.MoaTheme
import com.moa.app.domain.auth.model.Gender
import com.moa.app.domain.user.model.SeniorProfile
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun SeniorProfileGrid(
    profiles: ImmutableList<SeniorProfile>,
    deletable: Boolean,
    onProfileClick: (SeniorProfile) -> Unit,
    onDeleteClick: (Long) -> Unit,
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val cardColors = SeniorCardColors

    val items = remember(profiles, cardColors) {
        val list = profiles.take(4).mapIndexed { index, profile ->
            SeniorItem.Profile(
                data = profile,
                backgroundColor = cardColors[index % cardColors.size],
            )
        }.toMutableList<SeniorItem>()

        if (list.size < 4) { list.add(SeniorItem.AddButton) }
        list
    }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        items.chunked(2).forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                rowItems.forEach { item ->
                    Box(modifier = Modifier.weight(1f)) {
                        when (item) {
                            is SeniorItem.Profile -> {
                                SeniorProfileCard(
                                    name = item.data.name,
                                    gender = item.data.gender,
                                    backgroundColor = item.backgroundColor,
                                    isDeletable = deletable,
                                    onClick = { onProfileClick(item.data) },
                                    onDeleteClick = { onDeleteClick(item.data.id) },
                                )
                            }

                            is SeniorItem.AddButton -> {
                                AddProfileButton(onClick = onAddClick)
                            }
                        }
                    }
                }
                if (rowItems.size == 1) Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
private fun SeniorProfileCard(
    modifier: Modifier = Modifier,
    name: String,
    gender: Gender,
    backgroundColor: Color,
    isDeletable: Boolean,
    onClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    Box(modifier = modifier) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(24.dp))
                .background(backgroundColor)
                .fillMaxWidth()
                .clickable(
                    onClick = onClick,
                    indication = ripple(),
                    interactionSource = remember { MutableInteractionSource() }
                )
                .padding(top = 14.dp, start = 16.dp, bottom = 18.dp),
            verticalArrangement = Arrangement.spacedBy(52.dp),
        ) {
            ProfileIcon(gender)
            Text(
                text = name,
                color = MoaTheme.colors.white,
                style = MoaTheme.typography.title1Bold,
            )
        }

        if (isDeletable) {
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_delete_circle),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = 8.dp, y = (-8).dp)
                    .clickable(
                        onClick = onDeleteClick,
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ),
            )
        }
    }
}

@Composable
private fun ProfileIcon(
    gender: Gender,
    modifier: Modifier = Modifier,
) {
    val iconRes = if (gender == Gender.MALE) R.drawable.img_male else R.drawable.img_female

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .background(MoaTheme.colors.white)
            .padding(8.dp),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            imageVector = ImageVector.vectorResource(iconRes),
            contentDescription = null,
        )
    }
}

@Composable
private fun AddProfileButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
            .background(MoaTheme.colors.coolGray97)
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 54.dp, horizontal = 38.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_add_plus),
            contentDescription = null,
            colorFilter = ColorFilter.tint(MoaTheme.colors.coolGray50),
        )
        Text(
            text = "추가",
            color = MoaTheme.colors.coolGray50,
            style = MoaTheme.typography.title1Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.defaultMinSize(minWidth = 76.dp)
        )
    }

}

sealed interface SeniorItem {
    data class Profile(val data: SeniorProfile, val backgroundColor: Color) : SeniorItem
    object AddButton : SeniorItem
}

private val SeniorCardColors
    @Composable get() = listOf(
        MoaTheme.colors.orange500,
        MoaTheme.colors.lightBlue500,
        MoaTheme.colors.red400,
        MoaTheme.colors.purple500,
    )

@Preview(showBackground = true)
@Composable
private fun PreviewSeniorProfileGrid() {
    Column {
        SeniorProfileGrid(
            profiles = persistentListOf(
                SeniorProfile(
                    id = 1,
                    name = "어쩌구",
                    birthDate = "",
                    gender = Gender.MALE,
                    phoneNumber = "",
                ),
                SeniorProfile(
                    id = 2,
                    name = "얼쩌구",
                    birthDate = "",
                    gender = Gender.FEMALE,
                    phoneNumber = "",
                ),
                SeniorProfile(
                    id = 3,
                    name = "저쩌구",
                    birthDate = "",
                    gender = Gender.MALE,
                    phoneNumber = "",
                )
            ),
            deletable = true,
            onProfileClick = {},
            onDeleteClick = {},
            onAddClick = {},
        )
    }
}

@Preview
@Composable
private fun PreviewSeniorProfileCard() {
    SeniorProfileCard(
        name = "김철수",
        gender = Gender.MALE,
        backgroundColor = MoaTheme.colors.orange500,
        isDeletable = true,
        onClick = {},
        onDeleteClick = {},
    )
}
