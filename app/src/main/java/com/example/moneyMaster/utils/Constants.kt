package com.example.moneyMaster.utils

import com.example.moneyMaster.R
import com.example.moneyMaster.models.Category

object Constants {
    @JvmField
    var INCOME = "INCOME"
    @JvmField
    var EXPENSE = "EXPENSE"
    @JvmField
    var categories: ArrayList<Category>? = null
    @JvmField
    var DAILY = 0
    @JvmField
    var MONTHLY = 1
    var CALENDAR = 2
    var SUMMARY = 3
    var NOTES = 4
    @JvmField
    var SELECTED_TAB = 0
    @JvmStatic
    fun setCategories() {
        categories = ArrayList()
        categories!!.add(Category("Salary", R.drawable.ic_salary, R.color.category1))
        categories!!.add(Category("Business", R.drawable.ic_business, R.color.category2))
        categories!!.add(Category("Investment", R.drawable.ic_investment, R.color.category3))
        categories!!.add(Category("Loan", R.drawable.ic_loan, R.color.category4))
        categories!!.add(Category("Rent", R.drawable.ic_rent, R.color.category5))
        categories!!.add(Category("Other", R.drawable.ic_other, R.color.category6))
    }

    fun getCategoryDetails(categoryName: String): Category? {
        for (cat in categories!!) {
            if (cat.categoryName == categoryName) {
                return cat
            }
        }
        return null
    }

    fun getAccountsColor(accountName: String?): Int {
        return when (accountName) {
            "Bank" -> R.color.bank_color
            "Cash" -> R.color.cash_color
            "Card" -> R.color.card_color
            else -> R.color.default_color
        }
    }
}