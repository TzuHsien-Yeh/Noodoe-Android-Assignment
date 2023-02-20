package com.tzuhsien.noodoeassignment.timezone

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.tzuhsien.noodoeassignment.MainCoroutineRule
import com.tzuhsien.noodoeassignment.data.UserInfo
import com.tzuhsien.noodoeassignment.data.source.FakeRepository
import com.tzuhsien.noodoeassignment.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class TimeZoneViewModelTest {

    private lateinit var viewModel: TimeZoneViewModel

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val fakeRepository = FakeRepository()

    @Before
    fun setUp() {
        viewModel = TimeZoneViewModel(fakeRepository)
    }

    @Test
    fun checkUserLoggedIn_userNotNull_returnsTrue() {
        assertThat(viewModel.isLoggedIn.getOrAwaitValue()).isEqualTo(true)
    }

    @Test
    fun checkUserLoggedIn_userNull_returnsFalse() {
        // Create a new instance of repository that pass null user to a uninitialized viewModel
        val fakeRepository2 = FakeRepository()
        fakeRepository2.setUserInfo(null)
        val viewModel2 = TimeZoneViewModel(fakeRepository2)

        viewModel2.checkUserLoggedIn()
        assertThat(viewModel2.isLoggedIn.getOrAwaitValue()).isEqualTo(false)
    }

    @Test
    fun updateUserTimeZone_updateSuccess_invokeDoneUpdateLiveData() {
        viewModel.updateUserTimeZone("newTimeZone")

        assertThat(viewModel.error.getOrAwaitValue()).isEqualTo(null)
        assertThat(viewModel.doneTimeZoneUpdate.getOrAwaitValue()).isEqualTo(true)
    }

    @Test
    fun updateUserTimeZone_NoNetworkConnection_returnsError() {
        fakeRepository.setShouldReturnNetworkError(true)
        viewModel.updateUserTimeZone("newTimeZone")

        assertThat(viewModel.doneTimeZoneUpdate.getOrAwaitValue()).isEqualTo(false)
        assertThat(viewModel.error.getOrAwaitValue()).isEqualTo("Error")
    }

}