package com.tzuhsien.noodoeassignment.parkinginfo

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
class ParkingInfoViewModelTest {

    private lateinit var viewModel: ParkingInfoViewModel

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val fakeRepository = FakeRepository()

    @Before
    fun setUp() {
        viewModel = ParkingInfoViewModel(fakeRepository)
    }

    @Test
    fun getParkingInfoData_NoNetworkConnection_returnsError() {
        fakeRepository.setShouldReturnNetworkError(true)
        viewModel.getParkingInfoData()

        assertThat(viewModel.error.getOrAwaitValue()).isEqualTo("Error")
    }
}