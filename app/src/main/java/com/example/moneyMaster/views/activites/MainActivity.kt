package com.example.moneyMaster.views.activites

import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.emanager.R
import com.example.emanager.databinding.ActivityMainBinding
import com.example.moneyMaster.R
import com.example.moneyMaster.adapters.TransactionsAdapter
import com.example.moneyMaster.databinding.ActivityMainBinding
import com.example.moneyMaster.models.Transaction
import com.example.moneyMaster.utils.Constants
import com.example.moneyMaster.utils.Constants.setCategories
import com.example.moneyMaster.utils.Helper.formatDate
import com.example.moneyMaster.utils.Helper.formatDateByMonth
import com.example.moneyMaster.viewmodels.MainViewModel
import com.example.moneyMaster.views.fragments.AddTransactionFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import io.realm.RealmResults
import java.util.*

class MainActivity : AppCompatActivity() {
    var binding: ActivityMainBinding? = null
    private var calendar: Calendar? = null

    /*
    0 = Daily
    1 = Monthly
    2 = Calendar
    3 = Summary
    4 = Notes
     */
    var viewModel: MainViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        setSupportActionBar(binding!!.toolBar)
        supportActionBar!!.title = "Transactions"
        setCategories()
        calendar = Calendar.getInstance()
        updateDate()
        binding!!.nextDateBtn.setOnClickListener { c: View? ->
            if (Constants.SELECTED_TAB == Constants.DAILY) {
                calendar.add(Calendar.DATE, 1)
            } else if (Constants.SELECTED_TAB == Constants.MONTHLY) {
                calendar.add(Calendar.MONTH, 1)
            }
            updateDate()
        }
        binding!!.previousDateBtn.setOnClickListener { c: View? ->
            if (Constants.SELECTED_TAB == Constants.DAILY) {
                calendar.add(Calendar.DATE, -1)
            } else if (Constants.SELECTED_TAB == Constants.MONTHLY) {
                calendar.add(Calendar.MONTH, -1)
            }
            updateDate()
        }
        binding!!.floatingActionButton.setOnClickListener { c: View? ->
            AddTransactionFragment().show(
                supportFragmentManager, null
            )
        }
        binding!!.tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                if (tab.text == "Monthly") {
                    Constants.SELECTED_TAB = 1
                    updateDate()
                } else if (tab.text == "Daily") {
                    Constants.SELECTED_TAB = 0
                    updateDate()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
        binding!!.transactionsList.layoutManager = LinearLayoutManager(this)
        viewModel!!.transactions.observe(
            this,
            Observer<RealmResults<Transaction?>> { transactions ->
                val transactionsAdapter = TransactionsAdapter(this@MainActivity, transactions)
                binding!!.transactionsList.adapter = transactionsAdapter
                if (transactions.size > 0) binding!!.emptyState.visibility = View.GONE
                else binding!!.emptyState.visibility = View.VISIBLE
            })
        viewModel!!.totalIncome.observe(this) { aDouble ->
            binding!!.incomeLbl.text = aDouble.toString()
        }
        viewModel!!.totalExpense.observe(this) { aDouble ->
            binding!!.expenseLbl.text = aDouble.toString()
        }
        viewModel!!.totalAmount.observe(this) { aDouble ->
            binding!!.totalLbl.text = aDouble.toString()
        }
        viewModel!!.getTransactions(calendar)
    }

    val transactions: Unit
        get() {
            viewModel!!.getTransactions(calendar)
        }

    fun updateDate() {
        if (Constants.SELECTED_TAB == Constants.DAILY) {
            binding!!.currentDate.text = formatDate(calendar!!.time)
        } else if (Constants.SELECTED_TAB == Constants.MONTHLY) {
            binding!!.currentDate.text = formatDateByMonth(
                calendar!!.time
            )
        }
        viewModel!!.getTransactions(calendar)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.top_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
}

private fun <T> MutableLiveData<T>.observe(mainActivity: MainActivity, observer: Observer<RealmResults<Transaction?>>) {

}
