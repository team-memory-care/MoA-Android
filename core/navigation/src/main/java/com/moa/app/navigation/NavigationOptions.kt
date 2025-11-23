package com.moa.app.navigation

/**
 * Navigation 동작 시 적용할 옵션들을 정의
 *
 * @property popUpTo 백스택에서 제거할 목적지 (null이면 백스택 유지)
 * @property inclusive popUpTo 목적지도 함께 제거할지 여부
 * @property launchSingleTop 이미 백스택 최상단에 있는 목적지면 재사용
 * @property restoreState 이전에 저장된 상태를 복원할지 여부
 * @property saveState 현재 상태를 저장할지 여부 (popUpTo와 함께 사용)
 * @property clearBackStack 백스택의 모든 항목을 제거하고 새 목적지를 Root로 설정할지 여부.(true인 경우 [popUpTo] 옵션보다 우선하며, 전체 스택을 초기화합니다.)
 */
data class NavigationOptions(
    val popUpTo: AppRoute? = null,
    val inclusive: Boolean = false,
    val launchSingleTop: Boolean = false,
    val restoreState: Boolean = false,
    val saveState: Boolean = false,
    val clearBackStack: Boolean = false,
) {
    companion object {
        val Default = NavigationOptions()
    }
}
