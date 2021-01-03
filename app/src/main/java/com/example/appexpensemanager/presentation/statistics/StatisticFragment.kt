package com.example.appexpensemanager.presentation.statistics

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.OnLifecycleEvent
import com.example.appexpensemanager.R
import com.example.appexpensemanager.data.models.TotalAmountModel
import com.example.appexpensemanager.di.ContextModule
import com.example.appexpensemanager.di.DaggerAppComponent
import com.example.appexpensemanager.utils.ColorsHelper
import com.example.appexpensemanager.utils.ext.injectViewModel
import com.example.appexpensemanager.utils.factories.ViewModelProviderFactory
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import kotlinx.android.synthetic.main.statistic_fragment.*

class StatisticFragment : Fragment(R.layout.statistic_fragment) {

    private lateinit var viewModelProviderFactory: ViewModelProviderFactory
    private lateinit var statisticViewModel: StatisticViewModel

    private val totalAmountModel = TotalAmountModel()
    private lateinit var expenseList: MutableList<PieEntry>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().title = "Статистика"
        initViewModel()
        expenseList = mutableListOf()
        subscribeToObservers()
        setupPieChart()
    }

    private fun initViewModel() {
        val appComponent =
            DaggerAppComponent.builder().contextModule(ContextModule(requireContext())).build()
        viewModelProviderFactory = appComponent.getViewModelFactory()
        statisticViewModel = injectViewModel(viewModelProviderFactory)
    }

    private fun setupPieChart() {
        pie_chart.apply {
            setUsePercentValues(true)
            setDrawEntryLabels(false)
            setTouchEnabled(false)
            animateY(1000)
            setEntryLabelColor(resources.getColor(R.color.entryLabelColor))
            setHoleColor(resources.getColor(R.color.backgroundHoleColor))
            setCenterTextColor(resources.getColor(R.color.entryLabelColor))
            description.text = ""
            centerText = "Статистика"
            holeRadius = 60f
            transparentCircleRadius = 31f
        }
    }

    private fun subscribeToObservers() {
        statisticViewModel.totalAmountClothes.observe(viewLifecycleOwner, Observer {
            it?.let {
                totalAmountModel.totalAmountClothes = it
            }
        })

        statisticViewModel.totalAmountOther.observe(viewLifecycleOwner, Observer {
            it?.let {
                totalAmountModel.totalAmountOther = it
            }
        })

        statisticViewModel.totalAmountRestaurants.observe(viewLifecycleOwner, Observer {
            it?.let {
                totalAmountModel.totalAmountRestaurants = it
            }
        })

        statisticViewModel.totalAmountSupermarkets.observe(viewLifecycleOwner, Observer {
            it?.let {
                totalAmountModel.totalAmountSupermarkets = it
            }
        })

        statisticViewModel.totalAmountTravel.observe(viewLifecycleOwner, Observer {
            it?.let {
                totalAmountModel.totalAmountTravel = it
            }
        })

        statisticViewModel.totalAmountMovies.observe(viewLifecycleOwner, Observer {
            it?.let {
                totalAmountModel.totalAmountMovies = it
            }
        })

        statisticViewModel.totalAmountHealth.observe(viewLifecycleOwner, Observer {
            it?.let {
                totalAmountModel.totalAmountHealth = it
            }
        })

        statisticViewModel.savedTransactions.observe(viewLifecycleOwner, Observer {
                if (totalAmountModel.totalAmountClothes != 0) {
                    val clothesTransaction =
                        PieEntry(
                            totalAmountModel.totalAmountClothes.toFloat(),
                            resources.getString(R.string.clothes)
                        )
                    expenseList.add(clothesTransaction)
                }

                if (totalAmountModel.totalAmountRestaurants != 0) {
                    val restaurantsTransaction =
                        PieEntry(
                            totalAmountModel.totalAmountRestaurants.toFloat(),
                            resources.getString(R.string.restaurant)
                        )
                    expenseList.add(restaurantsTransaction)
                }

                if (totalAmountModel.totalAmountOther != 0) {
                    val otherTransaction =
                        PieEntry(
                            totalAmountModel.totalAmountOther.toFloat(),
                            resources.getString(R.string.other)
                        )
                    expenseList.add(otherTransaction)
                }

                if (totalAmountModel.totalAmountSupermarkets != 0) {
                    val supermarketsTransaction =
                        PieEntry(
                            totalAmountModel.totalAmountSupermarkets.toFloat(),
                            resources.getString(R.string.supermarkets)
                        )
                    expenseList.add(supermarketsTransaction)
                }

                if (totalAmountModel.totalAmountTravel != 0) {
                    val travelTransaction =
                        PieEntry(
                            totalAmountModel.totalAmountTravel.toFloat(),
                            resources.getString(R.string.travel)
                        )
                    expenseList.add(travelTransaction)
                }

                if (totalAmountModel.totalAmountMovies != 0) {
                    val moviesTransaction =
                        PieEntry(
                            totalAmountModel.totalAmountMovies.toFloat(),
                            resources.getString(R.string.movies)
                        )
                    expenseList.add(moviesTransaction)
                }

                if (totalAmountModel.totalAmountHealth != 0) {
                    val healthTransaction =
                        PieEntry(
                            totalAmountModel.totalAmountHealth.toFloat(),
                            resources.getString(R.string.health)
                        )
                    expenseList.add(healthTransaction)
                }

                if (expenseList.isNotEmpty()) {
                    val pieDataSet = PieDataSet(expenseList, "").apply {
                        setColors(*ColorsHelper().PIE_COLORS2)
                        valueFormatter = PercentFormatter()
                        valueTextColor = resources.getColor(R.color.pieValueTextColor)
                        xValuePosition = PieDataSet.ValuePosition.INSIDE_SLICE
                        valueTextSize = 14f

                        val l: Legend = pie_chart.legend
                        l.position = Legend.LegendPosition.LEFT_OF_CHART
                        l.orientation = Legend.LegendOrientation.VERTICAL
                        l.textColor = resources.getColor(R.color.legendTextColor)
                    }
                    pie_chart.data = PieData(pieDataSet)
                    pie_chart.invalidate()
                } else pie_chart.visibility = View.GONE
        })
    }
}
