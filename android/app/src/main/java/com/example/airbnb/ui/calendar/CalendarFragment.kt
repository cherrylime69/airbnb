package com.example.airbnb.ui.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.airbnb.R
import com.example.airbnb.databinding.FragmentCalendarBinding
import com.example.airbnb.ui.information.InformationViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.joda.time.LocalDate

class CalendarFragment : Fragment() {

    private val viewModel: InformationViewModel by activityViewModels()
    private lateinit var binding: FragmentCalendarBinding
    private lateinit var monthAdapter: MonthAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_calendar, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        monthAdapter = MonthAdapter { selectedDate ->
            selectDate(selectedDate)
        }
        binding.rvCalendar.adapter = monthAdapter
        monthAdapter.submitCalendarMaps(viewModel.calendarDatMap)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch { setCheckOut() }
                launch { setCheckIn() }
            }
        }
    }

    private suspend fun setCheckIn() {
        viewModel.checkInStatedFlow.collect {
            binding.checkIn = it
            monthAdapter.setCheckInAndCheckOut(it, viewModel.checkOutStatedFlow.value)
            monthAdapter.notifyDataSetChanged()
            if (it != null) {
                viewModel.switchSkipFlag()
            }
        }
    }

    private suspend fun setCheckOut() {
        viewModel.checkOutStatedFlow.collect {
            binding.checkOut = it
            monthAdapter.setCheckInAndCheckOut(viewModel.checkInStatedFlow.value, it)
            monthAdapter.notifyDataSetChanged()
            if (it != null) {
                viewModel.switchCheckedFlag()
            }
        }
    }

    private fun selectDate(selectedDate: LocalDate) {
        viewModel.saveDate(selectedDate)
    }
}