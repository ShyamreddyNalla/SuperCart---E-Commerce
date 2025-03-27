import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.example.e_commercesupercart.databinding.ProductItemBinding
import com.example.e_commercesupercart.model.roomdb.CartItem

class CartAdapter(
    private var cartItems: List<CartItem>,
    private val onQuantityChange: (CartItem, Int) -> Unit,
    private val onRemoveItem: (CartItem) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(val binding: ProductItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = cartItems[position]

        holder.binding.apply {
            productName.text = item.productName
            productPrice.text = "$${item.productPrice}"
            textQuantity.text = item.quantity.toString()
            Glide.with(imageProduct.context).load(item.productImage).into(imageProduct)

            if (item.quantity == 0) {
                buttonAddToCart.visibility = View.VISIBLE
                quantityLayout.visibility = View.GONE
            } else {
                buttonAddToCart.visibility = View.GONE
                quantityLayout.visibility = View.VISIBLE
            }

            buttonAddToCart.setOnClickListener {
                onQuantityChange(item, 1)
            }

            buttonIncrease.setOnClickListener {
                onQuantityChange(item, item.quantity + 1)
            }

            buttonDecrease.setOnClickListener {
                if (item.quantity > 1) {
                    onQuantityChange(item, item.quantity - 1)
                } else {
                    onRemoveItem(item)
                }
            }
        }
    }

    override fun getItemCount() = cartItems.size

    fun updateCartItems(newItems: List<CartItem>) {
        cartItems = newItems
        notifyDataSetChanged()
    }
}
