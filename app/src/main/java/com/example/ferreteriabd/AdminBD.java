package com.example.ferreteriabd;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;
public class AdminBD extends SQLiteOpenHelper {//creamos bases de datos
    public AdminBD(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase BaseDatos) {//creacion de tablas y sus campos
        BaseDatos.execSQL("create table cliente(cedula int primary key, nombre text, direccion text,telefono int)");
        BaseDatos.execSQL("create table pedido(codigo int primary key,  descripcion text, fecha text,cantidad int, cliente_id int, FOREIGN KEY (cliente_id) REFERENCES cliente(cedula))");
        BaseDatos.execSQL("create table producto(codigo int primary key, descripcion text, valor text)");
        BaseDatos.execSQL("create table factura(numero int primary key, fecha text, total text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase BaseDatos, int i, int i1) {
    }
}