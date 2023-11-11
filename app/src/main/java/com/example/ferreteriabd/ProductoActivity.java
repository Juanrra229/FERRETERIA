package com.example.ferreteriabd;

import androidx.appcompat.app.AppCompatActivity;


import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ProductoActivity extends AppCompatActivity {

    private EditText etcp,etdp,etv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto);

        etcp=findViewById(R.id.etcodigo);
        etdp=findViewById(R.id.etdescripcion1);
        etv=findViewById(R.id.etvalor);

    }
    public void menu(View view){
        Intent Menu = new Intent(this,MainActivity.class);
        startActivity(Menu);
    }

    public void registrar (View view){

        AdminBD admin=new AdminBD(this,"BaseDatos",null,2);

        SQLiteDatabase BaseDatos = admin.getWritableDatabase();

        String codigo =etcp.getText().toString();
        String descripcion =etdp.getText().toString();
        String valor =etv.getText().toString();

        if (!codigo.isEmpty() && !descripcion.isEmpty() && !valor.isEmpty()){

            ContentValues registro = new ContentValues();

            registro.put("codigo",codigo);
            registro.put("descripcion",descripcion);
            registro.put("valor",valor);

            BaseDatos.insert("producto",null,registro);

            /*
                LISTANDO TODOS LOS REGISTROS
             */
            String tableName = "producto";
            Cursor cursor = BaseDatos.rawQuery("SELECT * FROM producto", null);

            if (cursor.moveToFirst()) {
                do {
                    int codigoDB = cursor.getInt(0);
                    String descripcionDB = cursor.getString(1);
                    String valorDB = cursor.getString(2);
                    System.out.println("************** LISTANDO PRODUCTOS ***************\n");
                    System.out.println("codigo: " + codigoDB + " - descripcion: " + descripcionDB + " - valor: " + valor + "\n");
                } while (cursor.moveToNext());
            }

            cursor.close();

            BaseDatos.close();

            etcp.setText("");
            etdp.setText("");
            etv.setText("");


            Toast.makeText(this,"Registro Ingresado y Almacenado Correctamente",Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(this,"Ingrese Correctamente todos los datos",Toast.LENGTH_LONG).show();
        }
    }

    public void consult (View view){

        AdminBD admin=new AdminBD(ProductoActivity.this,"BaseDatos",null,2);
        SQLiteDatabase BaseDatos=admin.getWritableDatabase();

        String codigo=etcp.getText().toString();

        if(!codigo.isEmpty()){

            try (Cursor filla = BaseDatos.rawQuery("select descripcion,valor from producto where codigo=" + codigo, null)) {

                if (filla.moveToFirst()) {
                    etdp.setText(filla.getString(0));
                    etv.setText(filla.getString(1));
                    BaseDatos.close();
                } else {
                    Toast.makeText(this, "No se encontro el PRODUCTO", Toast.LENGTH_LONG).show();
                }
            }
        }else{
            Toast.makeText(this,"NO HAY PRODUCTO",Toast.LENGTH_LONG).show();
        }
    }

    public void actualizar (View view){
        AdminBD admin=new AdminBD(this,"BaseDatos",null,2);
        SQLiteDatabase BaseDatos = admin.getWritableDatabase();

        String codigo =etcp.getText().toString();
        String descripcion =etdp.getText().toString();
        String valor =etv.getText().toString();

        if (!codigo.isEmpty() && !descripcion.isEmpty() && !valor.isEmpty()){

            ContentValues registro = new ContentValues();

            registro.put("codigo",codigo);
            registro.put("descripcion",descripcion);
            registro.put("valor",valor);


            int fila=BaseDatos.update("producto",registro,"codigo="+codigo,null);

            if (fila>0){
                    Toast.makeText(this,"EL REGISTRO DEL PRODUCTO FUE EXITOSO",Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(this,"EL REGISTRO DEL PRODUCTO NO FUE EXITOSO",Toast.LENGTH_LONG).show();
            }

        }else{
            Toast.makeText(this,"EL REGISTRO DEL PRODUCTO NO FUE ENCONTRADO",Toast.LENGTH_LONG).show();
        }
        etcp.setText("");
        etdp.setText("");
        etv.setText("");
    }


    public void eliminar (View view){

        AdminBD admin=new AdminBD(this,"BaseDatos",null,2);
        SQLiteDatabase BaseDatos=admin.getWritableDatabase();
        String codigo=etcp.getText().toString();

        if(!codigo.isEmpty()){

            int fila=BaseDatos.delete("producto","codigo="+codigo,null);


            if (fila>0){
                Toast.makeText(this,"EL PRODUCTO FUE ELIMINADO",Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(this,"EL PRODUCTO NO FUE ELIMINADO",Toast.LENGTH_LONG).show();
            }

        }
        etcp.setText("");
        etdp.setText("");
        etv.setText("");
    }
}