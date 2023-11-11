package com.example.ferreteriabd;

import static java.util.stream.StreamSupport.stream;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Spinner;

import com.example.ferreteriabd.entities.Cliente;

import java.util.ArrayList;
import java.util.List;

public class PedidoActivity extends AppCompatActivity {

    private EditText etcd,etd,etf,etcn;
    private Spinner spinnerClientes;

    private List<Cliente> listaClientes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido);
        etcd=findViewById(R.id.etCodigo);
        etd=findViewById(R.id.etdescripcion);
        etf=findViewById(R.id.etfecha);
        etcn=findViewById(R.id.etcantidad);
        spinnerClientes = findViewById(R.id.listaClientes);

        getClientes();

        List<String> listaNombresClientes = new ArrayList<>();
        for(Cliente c : listaClientes){
            listaNombresClientes.add(c.getNombre());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaNombresClientes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerClientes.setAdapter(adapter);

    }

    private void getClientes() {
        AdminBD admin=new AdminBD(this,"BaseDatos",null,2);
        SQLiteDatabase baseDatos = admin.getWritableDatabase();

        Cursor cursor = baseDatos.rawQuery("SELECT * FROM cliente", null);

        if (cursor.moveToFirst()) {
            do {
                int cedula = cursor.getInt(0);
                String nombre = cursor.getString(1);
                String direccion = cursor.getString(2);
                int telefono = cursor.getInt(3);

                Cliente cliente = new Cliente(cedula, nombre, direccion, telefono);
                listaClientes.add(cliente);
            } while (cursor.moveToNext());
        }
        cursor.close();
        baseDatos.close();
    }

    public void menu(View view){
        Intent Menu = new Intent(this,MainActivity.class);
        startActivity(Menu);
    }
    public void registrar (View view){
        AdminBD admin=new AdminBD(this,"BaseDatos",null,2);
        SQLiteDatabase BaseDatos = admin.getWritableDatabase();

        String codigo =etcd.getText().toString();
        String descripcion =etd.getText().toString();
        String fecha =etf.getText().toString();
        String cantidad =etcn.getText().toString();
        String nombreCliente = spinnerClientes.getSelectedItem().toString();

        if (!codigo.isEmpty() && !descripcion.isEmpty() && !fecha.isEmpty() && !cantidad.isEmpty() && !nombreCliente.isEmpty()){

            ContentValues registro = new ContentValues();

            registro.put("codigo",codigo);
            registro.put("descripcion",descripcion);
            registro.put("fecha",fecha);
            registro.put("cantidad",cantidad);

            int cedulaCliente = 0;
            for(Cliente c : listaClientes){
                if(c.getNombre().equals(nombreCliente)){
                    cedulaCliente = c.getCedula();
                }
            }

            registro.put("cliente_id", cedulaCliente);

            BaseDatos.insert("pedido",null,registro);
            BaseDatos.close();

            etcn.setText("");
            etf.setText("");
            etd.setText("");
            etcd.setText("");

            Toast.makeText(this,"Registro Ingresado y Almacenado Correctamente",Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(this,"Ingrese Correctamente todos los datos",Toast.LENGTH_LONG).show();
        }
    }

    public void consultaa (View view){

        AdminBD admin=new AdminBD(PedidoActivity.this,"BaseDatos",null,2);
        SQLiteDatabase BaseDatos = admin.getWritableDatabase();

        Cursor cursor = BaseDatos.rawQuery("SELECT * FROM pedido", null);
        System.out.println("************** LISTANDO PEDIDOS ***************\n");
        if (cursor.moveToFirst()) {
            do {
                int codigoDB = cursor.getInt(0);
                String descripcionDB = cursor.getString(1);
                String fechaDB = cursor.getString(2);
                String cantidadDB = cursor.getString(3);
                int cedulaCliente = cursor.getInt(4);

                System.out.println("codigo: " + codigoDB + " - descripcion: " + descripcionDB + " - fechaDB: " + fechaDB + "cantidadDB: " + cantidadDB +
                        "cedulaCliente: " + cedulaCliente +"\n");
            } while (cursor.moveToNext());
        }

        Intent listarPedidos = new Intent(this,ListaPedido.class);
        startActivity(listarPedidos);

    }
    public void actualizar (View view){
        AdminBD admin=new AdminBD(this,"BaseDatos",null,2);
        SQLiteDatabase BaseDatos = admin.getWritableDatabase();

        String codigo =etcd.getText().toString();
        String descripcion =etd.getText().toString();
        String fecha =etf.getText().toString();
        String cantidad =etcn.getText().toString();

        if (!codigo.isEmpty() && !descripcion.isEmpty() && !fecha.isEmpty() && !cantidad.isEmpty()){

            ContentValues registro = new ContentValues();

            registro.put("codigo",codigo);
            registro.put("descripcion",descripcion);
            registro.put("fecha",fecha);
            registro.put("cantidad",cantidad);

            int fila=BaseDatos.update("pedido",registro,"codigo="+codigo,null);

            if (fila>0){
                Toast.makeText(this,"EL REGISTRO DEL PEDIDO FUE EXITOSO",Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(this,"EL REGISTRO DEL PEDIDO NO FUE EXITOSO",Toast.LENGTH_LONG).show();
            }

        }else{
            Toast.makeText(this,"EL REGISTRO DEL PEDIDO NO FUE ENCONTRADO",Toast.LENGTH_LONG).show();
        }
        etcn.setText("");
        etf.setText("");
        etd.setText("");
        etcd.setText("");
    }


    public void eliminar (View view){

        AdminBD admin=new AdminBD(this,"BaseDatos",null,2);
        SQLiteDatabase BaseDatos=admin.getWritableDatabase();
        String codigo=etcd.getText().toString();

        if(!codigo.isEmpty()){

            int fila=BaseDatos.delete("pedido","codigo="+codigo,null);


            if (fila>0){
                Toast.makeText(this,"EL PEDIDO FUE ELIMINADO",Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(this,"EL PEDIDO NO FUE ELIMINADO",Toast.LENGTH_LONG).show();
            }

        }
        etcn.setText("");
        etf.setText("");
        etd.setText("");
        etcd.setText("");
    }
}