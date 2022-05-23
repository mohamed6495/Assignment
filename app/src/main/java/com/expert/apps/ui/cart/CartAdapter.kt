package com.expert.apps.ui.cart

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.expert.apps.R
import com.expert.apps.databinding.ItemCartBinding
import com.expert.apps.databinding.ItemProductBinding
import com.expert.apps.ui.products.model.Product
import kotlinx.android.synthetic.main.item_cart.view.*
import kotlinx.android.synthetic.main.item_cart.view.img_product
import kotlinx.android.synthetic.main.item_cart.view.q_counter
import kotlinx.android.synthetic.main.item_cart.view.txt_name
import kotlinx.android.synthetic.main.item_cart.view.txt_price
import kotlinx.android.synthetic.main.item_product.view.*
import javax.inject.Inject
import javax.inject.Singleton


/**
 * This is the adapter used for the search result recycler view it takes :
 * @property searchItemActionsListener SearchItemActionsListener which is used to control the actions
 * performed on the item inside the adapter
 *
 */
@Singleton
class CartAdapter @Inject constructor() :
    RecyclerView.Adapter<CartAdapter.DefaultDataViewHolder>() {

    inner class DefaultDataViewHolder(private val itemBinding: ItemCartBinding) :
        RecyclerView.ViewHolder(itemBinding.root)

    private val differCallback = object : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DefaultDataViewHolder {
        val itemBinding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return DefaultDataViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((Product) -> Unit)? = null
    private var onItemRemoveListener: ((Product) -> Unit)? = null

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: DefaultDataViewHolder, position: Int) {
        val data = differ.currentList[position]
        holder.itemView.apply {

            Glide.with(this).load(data.thumbnail).placeholder(R.drawable.ic_cart_1).dontAnimate().into(img_product)
            q_counter.number = data.quantity.toString()
            txt_name.text = data.title
            txt_des.text = data.description
            txt_price.text = data.price.toString() +" $"

            add_to_cart.setOnClickListener {
                data.quantity = q_counter.number.toInt()
                onItemClickListener?.let { it(data) }
            }
            line_remove.setOnClickListener {
                onItemRemoveListener?.let { it(data) }
            }
        }
    }

    fun setOnItemClickListener(listener: (Product) -> Unit) {
        onItemClickListener = listener
    }


    fun setOnItemRemoveListener(listener: (Product) -> Unit) {
        onItemRemoveListener = listener
    }

}

