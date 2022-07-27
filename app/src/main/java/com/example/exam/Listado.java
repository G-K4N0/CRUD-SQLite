package com.example.exam;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.example.exam.ReaderContract.ContractEntries;

import java.util.ArrayList;

public class Listado extends AppCompatActivity {

    private ListView lstListado;

    ArrayList<String>listado;
    ArrayList<Empleado>listaEmpleado;
    EmpleadoHelper helper;
    ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado);
        lstListado = findViewById(R.id.lstListado);
        helper = new EmpleadoHelper(this);

        listado = new ArrayList<>();
        listaEmpleado = new ArrayList<>();
        listado = lista();
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,listado);
        lstListado.setAdapter(adapter);

        lstListado = findViewById(R.id.lstListado);
        lstListado.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String name,last_name,direction,phone;
                int id_em,age;
                double labor_old,salary;
               name = listaEmpleado.get(position).getNombre();
               last_name= listaEmpleado.get(position).getApellidos();
                direction = listaEmpleado.get(position).getDireccion();
                phone = listaEmpleado.get(position).getTelefono();
                id_em = listaEmpleado.get(position).getId();
                age = listaEmpleado.get(position).getEdad();
                labor_old = listaEmpleado.get(position).getAntiguedad();
                salary = listaEmpleado.get(position).getSalario();

                Intent intent = new Intent(Listado.this, Edit_Delete.class);
                intent.putExtra("id",id_em);
                intent.putExtra("name",name);
                intent.putExtra("last_name",last_name);
                intent.putExtra("direction",direction);
                intent.putExtra("phone",phone);
                intent.putExtra("age",age);
                intent.putExtra("labor_old",labor_old);
                intent.putExtra("salary",salary);
                startActivity(intent);
            }
        });
    }
    @SuppressLint("Range")
    private ArrayList<String> lista()
    {
        ArrayList<String> datos;

        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.query(
                ContractEntries.TABLE_EMPLEADO,
                null,
                null,
                null,
                null,
                null,
                null
        );
        datos = new ArrayList<>();
        while (cursor.moveToNext()){
            String dato = "";
            String name,last_name,direction,phone;
            int id,age;
            double labor_old,salary;

            id = cursor.getInt(cursor.getColumnIndex(ContractEntries.COLUMN_NUMERO_EMPLEADO));
            name = cursor.getString(cursor.getColumnIndex(ContractEntries.COLUMN_NOMBRE_EMPLEADO));
            last_name = cursor.getString(cursor.getColumnIndex(ContractEntries.COLUMN_APELLIDOS_EMPLEADO));
            direction = cursor.getString(cursor.getColumnIndex(ContractEntries.COLUMN_DIRECCION_EMPLEADO));
            phone = cursor.getString(cursor.getColumnIndex(ContractEntries.COLUMN_TELEFONO_EMPLEADO));
            age = cursor.getInt(cursor.getColumnIndex(ContractEntries.COLUMN_EDAD_EMPLEADO));
            labor_old =  cursor.getDouble(cursor.getColumnIndex(ContractEntries.COLUMN_ANTIGUEDAD_EMPLEADO));
            salary =  cursor.getDouble(cursor.getColumnIndex(ContractEntries.COLUMN_SALARIO_EMPLEADO));

            dato = "-> " + id + " : " +name +" : "+ last_name +" : "+ direction +" : "+ phone +" : "+age+" : "+labor_old+" : "+salary;
            listaEmpleado.add(new Empleado(id,last_name,name,direction,phone,age,labor_old,salary));
            datos.add(dato);
        }
        cursor.close();
return datos;
    }
}