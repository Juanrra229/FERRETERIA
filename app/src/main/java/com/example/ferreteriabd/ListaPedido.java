package com.example.ferreteriabd;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ferreteriabd.entities.Cliente;

import java.util.ArrayList;
import java.util.List;

public class ListaPedido extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_pedidos);

        printTable();
    }

    private void printTable() {
        AdminBD admin = new AdminBD(ListaPedido.this, "BaseDatos", null, 2);
        SQLiteDatabase BaseDatos = admin.getWritableDatabase();

        Cursor cursor = BaseDatos.rawQuery("SELECT * FROM pedido", null);

        TableLayout tablaPedidos = findViewById(R.id.tablaPedidos);

        if (cursor.moveToFirst()) {
            do {
                int codigoDB = cursor.getInt(0);
                String descripcionDB = cursor.getString(1);
                String fechaDB = cursor.getString(2);
                int cantidadDB = cursor.getInt(3);
                int cedulaCliente = cursor.getInt(4);

                TableRow fila = new TableRow(this);
                TableRow.LayoutParams params = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f);
                params.setMargins(2, 2, 2, 2);
                // Crea celdas para cada columna
                TextView columnaCodigo = new TextView(this);
                columnaCodigo.setText(String.valueOf(codigoDB));
                columnaCodigo.setLayoutParams(params);
                fila.addView(columnaCodigo);

                TextView columnaDescripcion = new TextView(this);
                columnaDescripcion.setText(descripcionDB);
                columnaDescripcion.setLayoutParams(params);
                fila.addView(columnaDescripcion);

                TextView columnaFecha = new TextView(this);
                columnaFecha.setText(fechaDB);
                columnaFecha.setLayoutParams(params);
                fila.addView(columnaFecha);

                TextView columnaCantidad = new TextView(this);
                columnaCantidad.setText(String.valueOf(cantidadDB));
                columnaCantidad.setLayoutParams(params);
                fila.addView(columnaCantidad);

                List<Cliente> clientes = getClientes();
                TextView columnaCedulaCliente = new TextView(this);
                String nombreCliente = "";

                for(Cliente c : clientes){
                    if (c.getCedula() == cedulaCliente){
                        nombreCliente = c.getNombre();
                    }
                }

                columnaCedulaCliente.setText(nombreCliente);
                columnaCedulaCliente.setLayoutParams(params);
                fila.addView(columnaCedulaCliente);

                // Agrega la fila a la tabla
                tablaPedidos.addView(fila);

            } while (cursor.moveToNext());
        }

        cursor.close();
    }

    private List<Cliente> getClientes() {
        AdminBD admin=new AdminBD(this,"BaseDatos",null,2);
        SQLiteDatabase baseDatos = admin.getWritableDatabase();

        Cursor cursor = baseDatos.rawQuery("SELECT * FROM cliente", null);
        List<Cliente> listaClientes = new ArrayList<>();
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
        return listaClientes;
    }
}
