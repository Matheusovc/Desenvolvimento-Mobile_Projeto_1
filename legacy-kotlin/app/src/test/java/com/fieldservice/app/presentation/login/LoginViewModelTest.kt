package com.fieldservice.app.presentation.login

import com.fieldservice.app.data.repository.MockAuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class LoginViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `login with valid mock credentials succeeds`() = runTest {
        val viewModel = LoginViewModel(MockAuthRepository())
        viewModel.onEmailChange("tecnico@fieldservice.com")
        viewModel.onPasswordChange("123456")

        viewModel.login()
        testDispatcher.scheduler.advanceUntilIdle()

        assertTrue(viewModel.uiState.value.isLoginSuccessful)
        assertNull(viewModel.uiState.value.errorMessage)
    }

    @Test
    fun `login with invalid credentials shows error and does not navigate`() = runTest {
        val viewModel = LoginViewModel(MockAuthRepository())
        viewModel.onEmailChange("errado@fieldservice.com")
        viewModel.onPasswordChange("senha-errada")

        viewModel.login()
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(false, viewModel.uiState.value.isLoginSuccessful)
        assertNotNull(viewModel.uiState.value.errorMessage)
    }

    @Test
    fun `login with blank fields does not call repository`() = runTest {
        val viewModel = LoginViewModel(MockAuthRepository())

        viewModel.login()

        assertEquals("Informe e-mail e senha", viewModel.uiState.value.errorMessage)
    }
}
