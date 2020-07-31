package com.example.cradspedidos;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class IngresarProductos extends AppCompatActivity {

    EditText edtCodigo, edtNombre, edtMarca, edtPeso, edtPrecio;
    Button btnAgregar, btnBuscar, btnEditar,btnEliminar;
    RequestQueue requestQueue;
    LinearLayout layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crud_productos);
        layout = (LinearLayout) findViewById(R.id.crud_productos);


        edtCodigo     = (EditText)findViewById(R.id.etxtCodigo);
        edtNombre     = (EditText)findViewById(R.id.etxtNombre);
        edtMarca      = (EditText)findViewById(R.id.etxtMarca);
        edtPeso       = (EditText)findViewById(R.id.etxtPeso);
        edtPrecio     = (EditText)findViewById(R.id.etxtPrecio);

        btnAgregar    = (Button)findViewById(R.id.btnAgregar);
        btnBuscar     = (Button)findViewById(R.id.btnBuscar);
        btnEditar   = (Button)findViewById(R.id.btnEditar);
        btnEliminar   = (Button)findViewById(R.id.btnEliminar);



        btnBuscar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {

                if(edtCodigo.getText().toString().isEmpty())
                {
                    //Toast.makeText(Productos.this,"Ingrese el Código del Producto",Toast.LENGTH_SHORT).show();

                    Snackbar sb = Snackbar.make(layout,"INGRESE EL CODIGO DEL PRODUCTO",Snackbar.LENGTH_SHORT);
                    sb.show();


                }

                else {

                    String  c = edtCodigo.getText().toString();

                    //Toast.makeText(getBaseContext(),c,Toast.LENGTH_LONG).show();

                    buscarProducto("http://192.168.1.7:8080/apiRestFul/buscarProductoPorId.php?codigo=" +c);

                    //Toast.makeText(Productos.this,"Acceso a URL",Toast.LENGTH_SHORT).show();
                    //buscarProducto("http://192.168.1.9:8080/api_rest/buscarProductoPorId.php?codigo=1");

                }

            }
        });


        btnAgregar.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v)
            {

                if(edtCodigo.getText().toString().isEmpty()   ||
                        edtNombre.getText().toString().isEmpty() ||
                        edtMarca.getText().toString().isEmpty()  ||
                        edtPeso.getText().toString().isEmpty()   ||
                        edtPrecio.getText().toString().isEmpty()

                )
                {
                    //Toast.makeText(Productos.this,"Por favor ingresa los campos en blanco",Toast.LENGTH_SHORT).show();

                    Snackbar sb = Snackbar.make(layout,"POR FAVOR INGRESA LOS CAMPOS EN BLANCO",Snackbar.LENGTH_SHORT);
                    sb.show();

                }
                else {

                    insertar("http://192.168.1.7:8080/apiRestFul/insertar_producto.php");
                }


            }
        });

        btnEditar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {

                if(edtCodigo.getText().toString().isEmpty()) {

                    Snackbar sb = Snackbar.make(layout,"INGRESE EL CODIGO DEL PRODUCTO",Snackbar.LENGTH_SHORT);
                    sb.show();
                }
                else
                {
                    actualizarDatos("http://192.168.1.7:8080/apiRestFul/editar_producto.php");
                }


            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {

                if(edtCodigo.getText().toString().isEmpty()) {

                    Snackbar sb = Snackbar.make(layout,"INGRESE EL CODIGO DEL PRODUCTO",Snackbar.LENGTH_SHORT);
                    sb.show();
                }
                else
                {
                    dialogoEliminarProductos();

                    //eliminarDatos("http://192.168.1.9:8080/apiRestFul/eliminar_producto.php");
                }
            }
        });




    }

//METODO DE BUSQUEDA POR CODIGO

    private void buscarProducto(String URL)
    {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>()
        {

            @Override
            public void onResponse(JSONArray response){
                JSONObject jsonObject = null;
                for(int i = 0; i<response.length(); i++)
                {
                    try{
                        jsonObject = response.getJSONObject(i);
                        edtNombre.setText(jsonObject.getString("nombre"));
                        edtMarca.setText(jsonObject.getString("marca"));
                        edtPeso.setText(jsonObject.getString("peso"));
                        edtPrecio.setText(jsonObject.getString("precio"));

                        Snackbar sb = Snackbar.make(layout,"DATOS CARGADOS CORRECTAMENTE",Snackbar.LENGTH_SHORT);
                        sb.show();



                    }catch(JSONException e){

                        //Toast.makeText(getApplicationContext(), e.getMessage(),Toast.LENGTH_SHORT).show();
                        //dialogoErrorServidor();


                    }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {

                Snackbar sb = Snackbar.make(layout,"PRODUCTO NO ENCONTRADO",Snackbar.LENGTH_SHORT);
                sb.show();

                limpiarFormulario();

                //Toast.makeText(getApplicationContext(), "Error de Conexion",Toast.LENGTH_SHORT).show();
                //dialogoErrorServidor();


            }

        });
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);


    }

//METODO DE INSERCION

    public void insertar(String URL)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,URL,new Response.Listener<String>() {
            @Override
            public void onResponse (String response)
            {
                limpiarFormulario();
                Toast.makeText(getApplicationContext(), "Datos Ingresados Correctamente",Toast.LENGTH_SHORT).show();
                //Intent i = new Intent(getApplicationContext(), ListaProductos.class);
                //startActivity(i);



            }

        },new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error){

                //dialogoErrorServidor();
                //Toast.makeText(getApplicationContext(), error.toString(),Toast.LENGTH_SHORT).show();

            }

        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map <String,String> parametros = new HashMap<String, String>();
                parametros.put("codigo",edtCodigo.getText().toString());
                parametros.put("nombre",edtNombre.getText().toString());
                parametros.put("marca",edtMarca.getText().toString());
                parametros.put("peso",edtPeso.getText().toString());
                parametros.put("precio", edtPrecio.getText().toString());
                return parametros;

            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

//METODO DE ACTUALIZACION

    private void actualizarDatos(String URL)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,URL,new Response.Listener<String>() {
            @Override
            public void onResponse (String response)
            {
                limpiarFormulario();
                Toast.makeText(getApplicationContext(), "Datos Actualizados Correctamente",Toast.LENGTH_SHORT).show();
                //Intent i = new Intent(getApplicationContext(), ListaProductos.class);
                //startActivity(i);
            }

        },new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error){

                //dialogoErrorServidor();
                //Toast.makeText(getApplicationContext(), error.toString(),Toast.LENGTH_SHORT).show();

            }

        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError{
                Map <String,String> parametros = new HashMap<String, String>();
                parametros.put("codigo",edtCodigo.getText().toString());
                parametros.put("nombre",edtNombre.getText().toString());
                parametros.put("marca",edtMarca.getText().toString());
                parametros.put("peso",edtPeso.getText().toString());
                parametros.put("precio", edtPrecio.getText().toString());
                return parametros;

            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

//METODO DE ELIMINACION

    private void eliminarDatos(String URL)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,URL,new Response.Listener<String>() {
            @Override
            public void onResponse (String response)
            {
                Toast.makeText(getApplicationContext(), "Datos Eliminados Correctamente",Toast.LENGTH_SHORT).show();
                limpiarFormulario();
                //Intent i = new Intent(getApplicationContext(), ListaProductos.class);
                //startActivity(i);
            }

        },new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error){

                //dialogoErrorServidor();
                //Toast.makeText(getApplicationContext(), error.toString(),Toast.LENGTH_SHORT).show();

            }

        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError{
                Map <String,String> parametros = new HashMap<String, String>();
                parametros.put("codigo",edtCodigo.getText().toString());


                return parametros;

            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    public void limpiarFormulario()
    {

        edtCodigo.setText("");
        edtNombre.setText("");
        edtMarca.setText("");
        edtPeso.setText("");
        edtPrecio.setText("");

    }

    public void dialogoEliminarProductos()
    {
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
        dialogo1.setTitle("¿Está seguro de eliminar el producto?");
        dialogo1.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {

                eliminarDatos("http://192.168.1.7:8080/apiRestFul/eliminar_producto.php");
            }
        });

        dialogo1.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        dialogo1.show();
    }

}
