package ni.uca.edu.listadoprod

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import dataadapter.ProductoAdapter
import dataclass.Producto
import ni.uca.edu.listadoprod.databinding.ActivityMainBinding
import ni.uca.edu.listadoprod.databinding.ItemlistaBinding
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var listaProd = ArrayList<Producto>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        iniciar()
    }

    private fun limpiar() {
        with(binding) {
            etID.setText("")
            etNombreProd.setText("")
            etPrecio.setText("")
            etID.requestFocus()
        }
    }

    private fun agregarProd() {
        with(binding) {
            try {
                val id: Int = etID.text.toString().toInt()
                val nombre: String = etNombreProd.text.toString()
                val precio: Double = etPrecio.text.toString().toDouble()
                val prod = Producto(id, nombre, precio)
                listaProd.add(prod)
            } catch (ex: Exception) {
                Toast.makeText(
                    this@MainActivity, "Error: ${ex.toString()}",
                    Toast.LENGTH_LONG
                ).show()
            }
            rcvLista.layoutManager = LinearLayoutManager(this@MainActivity)
            rcvLista.adapter = ProductoAdapter(listaProd,
                { producto -> selecProd(producto) },
                { position -> elimProd(position) },
                { position -> updateProd(position) }
            )
        }
        limpiar()
    }

    fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)

    fun updateProd(position: Int) {
        with(binding) {
            val id: Int = etID.text.toString().toInt()
            val nombre: String = etNombreProd.text.toString()
            val precio: Double = etPrecio.text.toString().toDouble()
            val prod = Producto(id, nombre, precio)
            listaProd.set(position, prod)
            rcvLista.adapter?.notifyItemRemoved(position)
        }
    }

    fun selecProd(producto: Producto) {
        with(binding) {
            etID.text = producto.id.toString().toEditable()
            etNombreProd.text = producto.nombre.toEditable()
            etPrecio.text = producto.precio.toString().toEditable()
        }
    }

    fun elimProd(position: Int) {
        val builder = AlertDialog.Builder(this@MainActivity)
        builder.setMessage("¿Deseas eliminar el producto? No hay forma de deshacer esta acción")
            .setCancelable(false).setPositiveButton("SI") { dialog, id ->
                with(binding) {
                    listaProd.removeAt(position)
                    rcvLista.adapter?.notifyItemRemoved(position)
                    limpiar()
                }
            }.setNegativeButton("NO"){dialog, id->
                dialog.dismiss()
            }
        val alert = builder.create()
        alert.show()
    }

    private fun iniciar() {
        binding.btnAgregar.setOnClickListener {
            agregarProd()
        }
        binding.btnLimpiar.setOnClickListener {
            limpiar()
        }
    }
}