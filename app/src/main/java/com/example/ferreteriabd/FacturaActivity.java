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

public class FacturaActivity extends AppCompatActivity {

    private EditText etn,etf,ett;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factura);

        etn=findViewById(R.id.etfactura);
        etf=findViewById(R.id.etfecha1);
        ett=findViewById(R.id.ettotal);
    }
    public void menu(View view){
        Intent Menu = new Intent(this,MainActivity.class);
        startActivity(Menu);
    }

    public void registrar (View view){

        AdminBD admin=new AdminBD(this,"BaseDatos",null,2);
        SQLiteDatabase BaseDatos = admin.getWritableDatabase();

        String numero =etn.getText().toString();
        String fecha =etf.getText().toString();
        String total =ett.getText().toString();

        if (!numero.isEmpty() && !fecha.isEmpty() && !total.isEmpty()){
            ContentValues registro = new ContentValues();
            registro.put("numero",numero);
            registro.put("fecha",fecha);
            registro.put("total",total);

            BaseDatos.insert("factura",null,registro);
            BaseDatos.close();

            etn.setText("");
            etf.setText("");
            ett.setText("");


            Toast.makeText(this,"Registro Ingresado y Almacenado Correctamente",Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(this,"Ingrese Correctamente todos los datos",Toast.LENGTH_LONG).show();
        }
    }

    public void cosultar (View view){

        AdminBD admin=new AdminBD(FacturaActivity.this,"BaseDatos",null,2);
        SQLiteDatabase BaseDatos=admin.getWritableDatabase();

        String numero=etn.getText().toString();

        if(!numero.isEmpty()){

            try (Cursor filaa = BaseDatos.rawQuery("select fecha,total from factura where numero=" + numero, null)) {

                if (filaa.moveToFirst()) {
                    etf.setText(filaa.getString(0));
                    ett.setText(filaa.getString(1));
                    BaseDatos.close();
                } else {
                    Toast.makeText(this, "No se encontro el FACTURA", Toast.LENGTH_LONG).show();
                }
            }
        }else{
            Toast.makeText(this,"NO HAY FACTURA",Toast.LENGTH_LONG).show();
        }
    }

    public void actualizar (View view){
        AdminBD admin=new AdminBD(this,"BaseDatos",null,2);
        SQLiteDatabase BaseDatos = admin.getWritableDatabase();

        String numero =etn.getText().toString();
        String fecha =etf.getText().toString();
        String total =ett.getText().toString();

        if (!numero.isEmpty() && !fecha.isEmpty() && !total.isEmpty()){

            ContentValues registro = new ContentValues();

            registro.put("numero",numero);
            registro.put("fecha",fecha);
            registro.put("total",total);

            int fila=BaseDatos.update("factura",registro,"numero="+numero,null);

            if (fila>0){
                Toast.makeText(this,"EL REGISTRO DEL FACTURA FUE EXITOSO",Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(this,"EL REGISTRO DEL FACTURA NO FUE EXITOSO",Toast.LENGTH_LONG).show();
            }

        }else{
            Toast.makeText(this,"EL REGISTRO DEL FACTURA NO FUE ENCONTRADO",Toast.LENGTH_LONG).show();
        }
        etn.setText("");
        etf.setText("");
        ett.setText("");
    }


    public void eliminar (View view){

        AdminBD admin=new AdminBD(this,"BaseDatos",null,2);
        SQLiteDatabase BaseDatos=admin.getWritableDatabase();
        String numero=etn.getText().toString();

        if(!numero.isEmpty()){

            int fila=BaseDatos.delete("factura","numero="+numero,null);


            if (fila>0){
                Toast.makeText(this,"EL FACTURA FUE ELIMINADO",Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(this,"EL FACTURA NO FUE ELIMINADO",Toast.LENGTH_LONG).show();
            }

        }
        etn.setText("");
        etf.setText("");
        ett.setText("");
    }
}