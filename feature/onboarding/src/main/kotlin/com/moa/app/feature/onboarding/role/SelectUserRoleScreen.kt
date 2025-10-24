package com.moa.app.feature.onboarding.role

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moa.app.designsystem.R
import com.moa.app.designsystem.component.core.button.MaButton
import com.moa.app.designsystem.component.core.button.MaSelectButton
import com.moa.app.designsystem.theme.MoaTheme

@Composable
fun SelectUserRoleScreen() {
    SelectUserRoleScreenContent()
}

@Composable
private fun SelectUserRoleScreenContent() {
    var selectedUserRole by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MoaTheme.colors.white)
            .padding(top = 24.dp, start = 20.dp, end = 20.dp, bottom = 12.dp)
    ) {
        Text(
            text = "모아 사용 목적을\n선택해주세요",
            color = MoaTheme.colors.black,
            style = MoaTheme.typography.headLine2Bold,
        )

        Spacer(Modifier.height(28.dp))

        MaSelectButton(
            onClick = { selectedUserRole = "senior" },
            selected = selectedUserRole == "senior",
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(vertical = 16.dp, horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.img_role_senior),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(126.dp, 112.dp)
                )

                Spacer(Modifier.height(18.dp))

                Text(
                    text = "저는 본인이에요",
                    style = MoaTheme.typography.title2Semibold,
                    modifier = Modifier.padding(bottom = 2.dp)
                )

                Text(
                    text = "인지 능력을 키우고 싶어요",
                    style = MoaTheme.typography.body1Medium,
                )
            }
        }

        Spacer(Modifier.height(10.dp))

        MaSelectButton(
            onClick = { selectedUserRole = "guradian" },
            selected = selectedUserRole == "guradian",
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(vertical = 16.dp, horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.img_role_guradian),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(126.dp, 112.dp)
                )

                Spacer(Modifier.height(18.dp))

                Text(
                    text = "저는 가족이나 보호자에요",
                    style = MoaTheme.typography.title2Semibold,
                    modifier = Modifier.padding(bottom = 2.dp)
                )

                Text(
                    text = "상대방의 리포트를 확인하고 싶어요",
                    style = MoaTheme.typography.body1Medium,
                )
            }
        }

        Spacer(Modifier.weight(1f))

        MaButton(
            onClick = { },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "다음",
                style = MoaTheme.typography.body1Bold,
                modifier = Modifier.padding(vertical = 16.dp, horizontal = 20.dp)
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    SelectUserRoleScreenContent()
}
