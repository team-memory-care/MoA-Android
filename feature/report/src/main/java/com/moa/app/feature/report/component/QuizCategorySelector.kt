package com.moa.app.feature.report.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moa.app.designsystem.component.core.chip.MaBasicChip
import com.moa.app.designsystem.theme.MoaTheme
import com.moa.app.domain.quiz.model.QuizCategory
import com.moa.app.feature.report.extension.scoreTitle
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Composable
fun QuizCategorySelector(
    selectedCategory: QuizCategory,
    onCategorySelected: (QuizCategory) -> Unit,
    modifier: Modifier = Modifier,
    categories: ImmutableList<QuizCategory> = QuizCategory.entries.toImmutableList(),
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(
            items = categories,
            key = { it.name },
        ) { category ->
            val isSelected = selectedCategory == category
            MaBasicChip(
                onClick = { onCategorySelected(category) },
                selected = isSelected,
            ) {
                Text(
                    text = category.scoreTitle,
                    style = if (isSelected) MoaTheme.typography.body2Medium else MoaTheme.typography.body2Regular,
                    modifier = Modifier.padding(vertical = 4.dp, horizontal = 10.dp),
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    QuizCategorySelector(
        selectedCategory = QuizCategory.ALL,
        onCategorySelected = {},
    )
}
