package zekai.myapplication.paypaycurrency.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import zekai.myapplication.paypaycurrency.data.db.Rate
import zekai.myapplication.paypaycurrency.databinding.ItemLayoutBinding

class GridAdapter: RecyclerView.Adapter<GridAdapter.ContentViewHolder>() {

    class ContentViewHolder(
        private val binding:ItemLayoutBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(nameStr: String, rateStr: String) {

            binding.apply {
                name = nameStr
                rate = rateStr
                executePendingBindings()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder {
        return ContentViewHolder(
            ItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ContentViewHolder, position: Int) {
        holder.bind(mRatess[position]?.currencyTo ?: "",
            (mAmount / mCurrentSelectRate * mRatess[position].rate).toString()?: "")
    }

    override fun getItemCount(): Int {
        return mRatess.size
    }

    private var mRatess = mutableListOf<Rate>()

    fun initContents(rates: List<Rate>) {
        mRatess.clear()
        mRatess.addAll(rates)
        notifyDataSetChanged()
    }

    private var mAmount = 1.0
    fun setAmount(amount: Double){
        mAmount = amount
        notifyDataSetChanged()
    }

    private var mCurrentSelectRate = 1.0
    fun setCurrentSelectRate(currentSelectRate: Double){
        mCurrentSelectRate = currentSelectRate
        notifyDataSetChanged()
    }
}