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

public class ClienteActivity extends AppCompatActivity {

    private EditText etc,etn,etd,ett;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);

        etc=findViewById(R.id.etcedula);
        etn=findViewById(R.id.etnombre);
        etd=findViewById(R.id.etdireccion);
        ett=findViewById(R.id.ettelefono);

    }
    public void menu(View view){
        Intent Menu = new Intent(this,MainActivity.class);
        startActivity(Menu);

    }
    public void registrar (View view){//metodo para realizar registro en la BD
        AdminBD admin=new AdminBD(this,"BaseDatos",null,2);
        SQLiteDatabase BaseDatos = admin.getWritableDatabase();

        String cedula =etc.getText().toString();
        String nombre =etn.getText().toString();
        String direccion =etd.getText().toString();
        String telefono =ett.getText().toString();

        if (!cedula.isEmpty() && !nombre.isEmpty() && !direccion.isEmpty() && !telefono.isEmpty()){

            ContentValues registro = new ContentValues();

            //almacene los datos digitados
            registro.put("cedula",cedula);
            registro.put("nombre",nombre);
            registro.put("direccion",direccion);
            registro.put("telefono",telefono);

            BaseDatos.insert("cliente",null,registro);
            BaseDatos.close();

            etd.setText("");
            ett.setText("");
            etn.setText("");
            etc.setText("");


            Toast.makeText(this,"Registro Ingresado y Almacenado Correctamente",Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(this,"Ingrese Correctamente todos los datos",Toast.LENGTH_LONG).show();
        }


    }

    public void consultar (View view){

        AdminBD admin=new AdminBD(ClienteActivity.this,"BaseDatos",null,2);
        SQLiteDatabase BaseDatos=admin.getWritableDatabase();
        String cedula=etc.getText().toString();

        if(!cedula.isEmpty()){

            try (Cursor fila = BaseDatos.rawQuery("select nomdirecciobre,n,telefono from cliente where cedula=" + cedula, null)) {

                if (fila.moveToFirst()) {
                    etn.setText(fila.getString(0));
                    etd.setText(fila.getString(1));
                    ett.setText(fila.getString(2));
                    BaseDatos.close();
                } else {
                    Toast.makeText(this, "No se encontro el CLIENTE", Toast.LENGTH_LONG).show();
                }
            }
        }else{
            Toast.makeText(this,"NO HAY CLIENTE",Toast.LENGTH_LONG).show();
        }


    }

    public void actualizar (View view){
        AdminBD admin=new AdminBD(this,"BaseDatos",null,2);
        SQLiteDatabase BaseDatos = admin.getWritableDatabase();

        String cedula =etc.getText().toString();
        String nombre =etn.getText().toString();
        String direccion =etd.getText().toString();
        String telefono =ett.getText().toString();

        if (!cedula.isEmpty() && !nombre.isEmpty() && !direccion.isEmpty() && !telefono.isEmpty()){

            ContentValues registro = new ContentValues();

            registro.put("cedula",cedula);
            registro.put("nombre",nombre);
            registro.put("direccion",direccion);
            registro.put("telefono",telefono);

                int fila=BaseDatos.update("cliente",registro,"cedula="+cedula,null);

                if (fila>0){
                    Toast.makeText(this,"EL REGISTRO DEL CLIENTE FUE EXITOSO",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(this,"EL REGISTRO DEL CLIENTE NO FUE EXITOSO",Toast.LENGTH_LONG).show();
                }

        }else{
            Toast.makeText(this,"EL REGISTRO DEL CLIENTE NO FUE ENCONTRADO",Toast.LENGTH_LONG).show();
        }
        etd.setText("");
        ett.setText("");
        etn.setText("");
        etc.setText("");
    }


    public void eliminar (View view){

        AdminBD admin=new AdminBD(this,"BaseDatos",null,2);
        SQLiteDatabase BaseDatos=admin.getWritableDatabase();
        String cedula=etc.getText().toString();

        if(!cedula.isEmpty()){

            int fila=BaseDatos.delete("cliente","cedula="+cedula,null);


            if (fila>0){
                Toast.makeText(this,"EL CLIENTE FUE ELIMINADO",Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(this,"EL CLIENTE NO FUE ELIMINADO",Toast.LENGTH_LONG).show();
            }

        }
        etd.setText("");
        ett.setText("");
        etn.setText("");
        etc.setText("");
    }
}