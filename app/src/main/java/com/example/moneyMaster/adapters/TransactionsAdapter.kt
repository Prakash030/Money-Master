package com.example.moneyMaster.adapters

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moneyMaster.R
import com.example.moneyMaster.adapters.TransactionsAdapter.TransactionViewHolder
import com.example.moneyMaster.databinding.RowTransactionBinding
import com.example.moneyMaster.models.Transaction
import com.example.moneyMaster.utils.Constants
import com.example.moneyMaster.utils.Helper
import com.example.moneyMaster.views.activites.MainActivity
import io.realm.RealmResults

class TransactionsAdapter(var context: Context, var transactions: RealmResults<Transaction?>) :
    RecyclerView.Adapter<TransactionViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        return TransactionViewHolder(
            LayoutInflater.from(context).inflate(R.layout.row_transaction, parent, false)
        )
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val transaction = transactions[position]
        holder.binding.transactionAmount.text = transaction!!.amount.toString()
        holder.binding.accountLbl.text = transaction.account
        holder.binding.transactionDate.text = Helper.formatDate(
            transaction.date
        )
        holder.binding.transactionCategory.text = transaction.category
        val transactionCategory = Constants.getCategoryDetails(
            transaction.category
        )
        holder.binding.categoryIcon.setImageResource(transactionCategory.categoryImage)
        holder.binding.categoryIcon.backgroundTintList =
            context.getColorStateList(transactionCategory.categoryColor)
        holder.binding.accountLbl.backgroundTintList = context.getColorStateList(
            Constants.getAccountsColor(
                transaction.account
            )
        )
        if ((transaction.type == Constants.INCOME)) {
            holder.binding.transactionAmount.setTextColor(context.getColor(R.color.greenColor))
        } else if ((transaction.type == Constants.EXPENSE)) {
            holder.binding.transactionAmount.setTextColor(context.getColor(R.color.redColor))
        }
        holder.itemView.setOnLongClickListener(OnLongClickListener {
            val deleteDialog = AlertDialog.Builder(
                context
            ).create()
            deleteDialog.setTitle("Delete Transaction")
            deleteDialog.setMessage("Are you sure to delete this transaction?")
            deleteDialog.setButton(
                DialogInterface.BUTTON_POSITIVE,
                "Yes"
            ) { dialogInterface: DialogInterface?, i: Int ->
                (context as MainActivity).viewModel.deleteTransaction(transaction)
            }
            deleteDialog.setButton(
                DialogInterface.BUTTON_NEGATIVE,
                "No",
                { dialogInterface: DialogInterface?, i: Int -> deleteDialog.dismiss() })
            deleteDialog.show()
            false
        })
    }

    override fun getItemCount(): Int {
        return transactions.size
    }

    inner class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: RowTransactionBinding

        init {
            binding = RowTransactionBinding.bind(itemView)
        }
    }
}