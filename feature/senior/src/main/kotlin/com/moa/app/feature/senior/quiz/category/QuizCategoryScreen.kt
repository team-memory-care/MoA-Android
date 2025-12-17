package com.moa.app.feature.senior.quiz.category

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.moa.app.designsystem.component.product.topbar.MaTopAppBar
import com.moa.app.designsystem.theme.MoaTheme
import com.moa.app.domain.quiz.model.QuizCategory
import com.moa.app.feature.senior.quiz.category.model.QuizCategorySideEffect
import com.moa.app.feature.senior.quiz.category.model.backgroundColor
import com.moa.app.feature.senior.quiz.category.model.description
import com.moa.app.feature.senior.quiz.category.model.iconRes
import com.moa.app.feature.senior.quiz.category.model.title
import com.moa.app.feature.senior.quiz.component.QuizCategoryCard
import com.moa.app.feature.senior.quiz.category.model.QuizCategoryUiState

@Composable
fun QuizCategoryScreen(
    viewModel: QuizCategoryViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.sideEffect
            .flowWithLifecycle(lifecycleOwner.lifecycle)
            .collect { sideEffect ->
                when (sideEffect) {
                    is QuizCategorySideEffect.ShowToast -> {
                        Toast.makeText(context, sideEffect.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
    }

    QuizCategoryScreenContent(
        uiState = uiState,
        onQuizCategoryClick = viewModel::onCategoryClicked,
        onBackClick = viewModel::onBackClick
    )
}

@Composable
private fun QuizCategoryScreenContent(
    uiState: QuizCategoryUiState,
    onQuizCategoryClick: (QuizCategory) -> Unit,
    onBackClick: () -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        MaTopAppBar(title = "퀴즈 선택", onBackClick = onBackClick)

        Text(
            text = "표준 인지평가 방식을 참고해 구성되었어요",
            color = MoaTheme.colors.black,
            style = MoaTheme.typography.body1Regular,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 20.dp)
                .padding(top = 16.dp, bottom = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            QuizCategory.entries.forEach { category ->
                val isEnabled = uiState.enabledCategories.contains(category)

                QuizCategoryCard(
                    title = category.title,
                    description = category.description,
                    backgroundImage = category.iconRes,
                    backgroundColor = category.backgroundColor,
                    isEnabled = isEnabled,
                    modifier = Modifier.weight(1f),
                    onClick = { onQuizCategoryClick(category) }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    QuizCategoryScreenContent(
        uiState = QuizCategoryUiState.INIT,
        onQuizCategoryClick = {},
        onBackClick = {}
    )
}
