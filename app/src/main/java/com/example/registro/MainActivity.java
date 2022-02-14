package com.example.registro;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText codigo,nomb,apell,edad,corre,direc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        codigo=(EditText)findViewById(R.id.txtid);
        nomb=(EditText)findViewById(R.id.txtnom);
        apell=(EditText)findViewById(R.id.txtape);
        edad=(EditText)findViewById(R.id.txtedad);
        corre=(EditText)findViewById(R.id.txtcorreo);
        direc=(EditText)findViewById(R.id.txtdirec);
    }

    public void registrar(View view){
        Adminsql admin=new Adminsql(this,"administracion",null,1);
        SQLiteDatabase BaseDeDatos=admin.getWritableDatabase();

        String id=codigo.getText().toString();
        String nombr=nomb.getText().toString();
        String apelli=apell.getText().toString();
        String eda=edad.getText().toString();
        String email=corre.getText().toString();
        String direcc=direc.getText().toString();

        if (!id.isEmpty() && !nombr.isEmpty() && !apelli.isEmpty() && !eda.isEmpty() && !email.isEmpty() && !direcc.isEmpty())
        {
            ContentValues registro=new ContentValues();
            registro.put("ide",id);
            registro.put("nombre",nombr);
            registro.put("apellido",apelli);
            registro.put("edad",eda);
            registro.put("correo",email);
            registro.put("direccion",direcc);

            BaseDeDatos.insert("archivos", null, registro);
            BaseDeDatos.close();
            codigo.setText("");
            nomb.setText("");
            apell.setText("");
            edad.setText("");
            corre.setText("");
            direc.setText("");
            Toast.makeText(this, "Registro con Exito!!!", Toast.LENGTH_SHORT).show();

        }else{
            Toast.makeText(this, "Rellenar Los campos Vacios", Toast.LENGTH_SHORT).show();

        }

    }

    public void buscar(View view)
    {
        Adminsql admin = new Adminsql(this, "administracion", null, 1);
        SQLiteDatabase BaseDeDatabase = admin.getWritableDatabase();

        String ident=codigo.getText().toString();

        if(!ident.isEmpty()){
            Cursor fila = BaseDeDatabase.rawQuery
                    ("select nombre,apellido,edad,correo,direccion from archivos where ide =" + ident, null);

            if(fila.moveToFirst()){
                nomb.setText(fila.getString(0));
                apell.setText(fila.getString(1));
                edad.setText(fila.getString(2));
                corre.setText(fila.getString(3));
                direc.setText(fila.getString(4));
                BaseDeDatabase.close();
            } else {
                Toast.makeText(this,"No existe el nombre", Toast.LENGTH_SHORT).show();
                BaseDeDatabase.close();
            }

        } else {
            Toast.makeText(this, "Debes introducir el nombre del ", Toast.LENGTH_SHORT).show();
        }

    }
    ///////////////////////////////////////


    public void eliminar(View view)
    {

        Adminsql admin=new Adminsql(this,"administracion",null,1);
        SQLiteDatabase BaseDatabase=admin.getWritableDatabase();
        String ident=codigo.getText().toString();

        if(!ident.isEmpty())
        {
            int cantidad=BaseDatabase.delete("archivos","ide="+ident,null);
            BaseDatabase.close();
            codigo.setText("");
            nomb.setText("");
            apell.setText("");
            edad.setText("");
            corre.setText("");
            direc.setText("");

            if(cantidad==1)
            {
                Toast.makeText(this, "Eliminado!!!", Toast.LENGTH_SHORT).show();

            }else{
                Toast.makeText(this, "Codigo No Existe", Toast.LENGTH_SHORT).show();

            }



        }else{
            Toast.makeText(this, "Debes de Introducir el codigo,Para eliminar", Toast.LENGTH_SHORT).show();

        }

    }


    public void modificar(View view)
    {
        Adminsql admin=new Adminsql(this,"administracion",null,1);
        SQLiteDatabase BaseDatabase=admin.getWritableDatabase();
        String ident=codigo.getText().toString();
        String nombr=nomb.getText().toString();
        String apelli=apell.getText().toString();
        String eda=edad.getText().toString();
        String email=corre.getText().toString();
        String direcc=direc.getText().toString();

        if (!ident.isEmpty() && !nombr.isEmpty() && !apelli.isEmpty() && !eda.isEmpty() && !email.isEmpty() && !direcc.isEmpty())
        {
            ContentValues registro=new ContentValues();
            registro.put("ide",ident);
            registro.put("nombre",nombr);
            registro.put("apellido",apelli);
            registro.put("edad",eda);
            registro.put("correo",email);
            registro.put("direccion",direcc);

            int cantidad=BaseDatabase.update("archivos",registro,"ide="+ident,null);
            BaseDatabase.close();
            if (cantidad==1)
            {
                Toast.makeText(this, "Modificado Con exito!!", Toast.LENGTH_SHORT).show();

            }else
            {
                Toast.makeText(this, "No existe", Toast.LENGTH_SHORT).show();

            }

        }else
        {
            Toast.makeText(this, "Ingrese codigo para poder modificar", Toast.LENGTH_SHORT).show();
        }

    }

}