package com.example.cradspedidos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdaptadorProductos  extends RecyclerView.Adapter<AdaptadorProductos.CamposViewHolder>
{

    private Context mCtx;
    private List<Campos> productList;



    public AdaptadorProductos(Context mCtx, List<Campos> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }


    @Override
    public CamposViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.lista, null);
        return new CamposViewHolder(view);
        //return null;
    }

    @Override
    public void onBindViewHolder( CamposViewHolder holder, int position) {

        Campos product = productList.get(position);

        holder.txtCodigo.setText(String.valueOf("Código:"+" "+ product.getCodigo()));
        holder.txtNombre.setText(String.valueOf("Nombre:"+" "+product.getNombre()));
        holder.txtMarca.setText(String.valueOf("Marca:"+" "+product.getMarca()));
        holder.txtPeso.setText(String.valueOf("Peso:"+" "+product.getPeso()));
        holder.txtPrecio.setText(String.valueOf("Precio:"+" "+product.getPrecio()));

        /*String v;
        /*String v;
        v = String.valueOf(product.getCodigo());
        Toast.makeText(mCtx, v, Toast.LENGTH_SHORT).show();*/

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class CamposViewHolder extends RecyclerView.ViewHolder
    {
        //, txtNombre, txtMarca, txtPeso, txtPrecio ;
        TextView txtCodigo, txtNombre, txtMarca, txtPeso, txtPrecio ;;
        public CamposViewHolder(View itemView)
        {

            super(itemView);

            txtCodigo = itemView.findViewById(R.id.txtCodigo);
            txtNombre = itemView.findViewById(R.id.txtNombre);
            txtMarca = itemView.findViewById(R.id.txtMarca);
            txtPeso = itemView.findViewById(R.id.txtPeso);
            txtPrecio = itemView.findViewById(R.id.txtPrecio);

        }
    }
}