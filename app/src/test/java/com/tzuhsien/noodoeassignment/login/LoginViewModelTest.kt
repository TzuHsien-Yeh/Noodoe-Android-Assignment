package com.tzuhsien.noodoeassignment.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.tzuhsien.noodoeassignment.MainCoroutineRule
import com.tzuhsien.noodoeassignment.data.source.FakeRepository
import com.tzuhsien.noodoeassignment.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class LoginViewModelTest {

    private lateinit var viewModel: LoginViewModel

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val fakeRepository = FakeRepository()

    @Before
    fun setUp() {
        viewModel = LoginViewModel(fakeRepository)
    }

    @Test
    fun checkToLogIn_invalidUserName_showErrorMsg(){
        viewModel.userName.value = ""
        viewModel.password.value = "password"
        viewModel.checkToLogIn()

        assertThat(viewModel.error.getOrAwaitValue()).isEqualTo(InputError.INVALID_USER.msg)
    }

    @Test
    fun checkToLogIn_invalidPassword_showErrorMsg(){
        viewModel.userName.value = "user"
        viewModel.password.value = null
        viewModel.checkToLogIn()

        assertThat(viewModel.error.getOrAwaitValue()).isEqualTo(InputError.INVALID_PASSWORD.msg)
    }

    @Test
    fun checkToLogIn_noInput_showErrorMsg(){
        viewModel.userName.value = null
        viewModel.password.value = null
        viewModel.checkToLogIn()

        assertThat(viewModel.error.getOrAwaitValue()).isEqualTo(InputError.NO_INPUT.msg)
    }

    @Test
    fun logIn_NoNetworkConnection_returnsError() {
        fakeRepository.setShouldReturnNetworkError(true)
        viewModel.userName.value = "user"
        viewModel.password.value = "password"
        viewModel.logIn()

        assertThat(viewModel.navigateToParkingInfo.getOrAwaitValue()).isEqualTo(false)
        assertThat(viewModel.error.getOrAwaitValue()).isEqualTo("Error")
    }

    @Test
    fun logIn_NoNetworkConnection_navigateToParkingInfoTrue() {
        viewModel.userName.value = "user"
        viewModel.password.value = "password"
        viewModel.logIn()

        assertThat(viewModel.navigateToParkingInfo.getOrAwaitValue()).isEqualTo(true)
        assertThat(viewModel.error.getOrAwaitValue()).isEqualTo(null)
    }
}