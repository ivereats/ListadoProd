package dataadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dataclass.Producto
import ni.uca.edu.listadoprod.databinding.ActivityMainBinding
import ni.uca.edu.listadoprod.databinding.ItemlistaBinding

class ProductoAdapter(
    val listProd: List<Producto>,
    private val onClickerList: (Producto) -> Unit,
    private val onClickerElim: (Int) -> Unit,
    private val onClickerUpdate : (Int) -> Unit,
) :
    RecyclerView.Adapter<ProductoAdapter.ProductoHolder>() {
    inner class ProductoHolder(val binding: ItemlistaBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun cargar(
            producto : Producto, onClickerList : (Producto) -> Unit,
            onClickerElim: (Int) -> Unit,
            onClickerUpdate: (Int) -> Unit
        ) {
            with(binding) {
                tvCodProd.text = producto.id.toString()
                tvNombreProd.text = producto.nombre
                tvPrecioProd.text = producto.precio.toString()
                itemView.setOnClickListener{onClickerList(producto)}
                binding.btnElim.setOnClickListener { onClickerElim(adapterPosition) }
                binding.btnEditar.setOnClickListener { onClickerUpdate(adapterPosition) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoHolder {
        val binding = ItemlistaBinding.inflate(
            LayoutInflater.from(parent.context), parent,
            false
        )
        return ProductoHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductoAdapter.ProductoHolder, position: Int) {
        holder.cargar(listProd[position], onClickerList, onClickerElim, onClickerUpdate)
    }

    override fun getItemCount(): Int = listProd.size
}