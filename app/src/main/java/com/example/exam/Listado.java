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
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.exam.ReaderContract.ContractEntries;

import java.util.ArrayList;

public class Listado extends AppCompatActivity {

    private ListView lstListado;

    ArrayList<String> listado;
    ArrayList<Empleado> listaEmpleado;
    EmpleadoHelper helper;
    ArrayAdapter adapter;

    private Button btnOrden,btnNomina,btnSueldo,btnPromedio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado);

        btnOrden = findViewById(R.id.btnOrden);
        btnNomina = findViewById(R.id.btnNomina);
        btnSueldo = findViewById(R.id.btnSueldo);
        btnPromedio = findViewById(R.id.btnPromedio);
        lstListado = findViewById(R.id.lstListado);
        helper = new EmpleadoHelper(this);

        lstListado = findViewById(R.id.lstListado);
        lstListado.setOnItemClickListener((adapterView, view, position, id) -> {
            String name, last_name, direction, phone;
            int id_em, age;
            double labor_old, salary;
            name = listaEmpleado.get(position).getNombre();
            last_name = listaEmpleado.get(position).getApellidos();
            direction = listaEmpleado.get(position).getDireccion();
            phone = listaEmpleado.get(position).getTelefono();
            id_em = listaEmpleado.get(position).getId();
            age = listaEmpleado.get(position).getEdad();
            labor_old = listaEmpleado.get(position).getAntiguedad();
            salary = listaEmpleado.get(position).getSalario();

            Intent intent = new Intent(Listado.this, Edit_Delete.class);
            intent.putExtra("id", id_em);
            intent.putExtra("name", name);
            intent.putExtra("last_name", last_name);
            intent.putExtra("direction", direction);
            intent.putExtra("phone", phone);
            intent.putExtra("age", age);
            intent.putExtra("labor_old", labor_old);
            intent.putExtra("salary", salary);
            startActivity(intent);
        });
        actualizar();
    }

    @SuppressLint("Range")
    private ArrayList<String> lista() {
        ArrayList<String> datos;

        SQLiteDatabase db = helper.getReadableDatabase();
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
        while (cursor.moveToNext()) {
            String dato = "";
            String name, last_name, direction, phone;
            int id, age;
            double labor_old, salary;

            id = cursor.getInt(cursor.getColumnIndex(ContractEntries.COLUMN_NUMERO_EMPLEADO));
            name = cursor.getString(cursor.getColumnIndex(ContractEntries.COLUMN_NOMBRE_EMPLEADO));
            last_name = cursor.getString(cursor.getColumnIndex(ContractEntries.COLUMN_APELLIDOS_EMPLEADO));
            direction = cursor.getString(cursor.getColumnIndex(ContractEntries.COLUMN_DIRECCION_EMPLEADO));
            phone = cursor.getString(cursor.getColumnIndex(ContractEntries.COLUMN_TELEFONO_EMPLEADO));
            age = cursor.getInt(cursor.getColumnIndex(ContractEntries.COLUMN_EDAD_EMPLEADO));
            labor_old = cursor.getDouble(cursor.getColumnIndex(ContractEntries.COLUMN_ANTIGUEDAD_EMPLEADO));
            salary = cursor.getDouble(cursor.getColumnIndex(ContractEntries.COLUMN_SALARIO_EMPLEADO));

            dato = "ID: " + id + " Nombre: " + name + " Apellido: " +
                    last_name + " Direccion: " + direction + " Telefono: "
                    + phone + " Edad: " + age + " Antiguedad: " + labor_old + " Salario: " + salary;
            listaEmpleado.add(new Empleado(id, last_name, name, direction, phone, age, labor_old, salary));
            datos.add(dato);
        }
        cursor.close();
        return datos;
    }

    private void actualizar() {
        listado = new ArrayList<>();
        listaEmpleado = new ArrayList<>();
        listado = lista();
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listado);
        lstListado.setAdapter(adapter);

        btnOrden.setOnClickListener(v -> {
            listado = new ArrayList<>();
            listaEmpleado = new ArrayList<>();
            listado = ordenar();
            adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, listado);
            lstListado.setAdapter(adapter);
        });

        btnPromedio.setOnClickListener(v -> promedio());
        btnSueldo.setOnClickListener(v -> salarioMayor());
        btnNomina.setOnClickListener(v -> nomina());
    }
    private void promedio()
    {
        ArrayList<String> datos;

        String [] columnsGet = {"AVG("+ContractEntries.COLUMN_EDAD_EMPLEADO+" )", ContractEntries.COLUMN_NOMBRE_EMPLEADO};
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(
                ContractEntries.TABLE_EMPLEADO,
                columnsGet,
                null,
                null,
                null,
                null,
                null
        );
        datos = new ArrayList<>();
        while (cursor.moveToNext()) {
            int age = cursor.getInt(0);
            datos.add(String.valueOf(age));
        }
        cursor.close();
        Toast.makeText(this, "Promedio -->" + datos.get(0).toString(), Toast.LENGTH_LONG).show();

    }
    private ArrayList<String> ordenar()
    {
        ArrayList<String> datos;

        String [] columnsGet = {ContractEntries.COLUMN_NOMBRE_EMPLEADO};
        String order = ContractEntries.COLUMN_NOMBRE_EMPLEADO + " ASC";
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(
                ContractEntries.TABLE_EMPLEADO,
                columnsGet,
                null,
                null,
                null,
                null,
                order
        );

        datos = new ArrayList<>();
        while (cursor.moveToNext()) {

            String name = cursor.getString(0);
            datos.add(name);
        }
        cursor.close();

        return datos;
    }
    private void salarioMayor()
    {
        ArrayList<String> datos;

        String [] columnsGet = {"MAX("+ContractEntries.COLUMN_SALARIO_EMPLEADO+" )", ContractEntries.COLUMN_NOMBRE_EMPLEADO};
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(
                ContractEntries.TABLE_EMPLEADO,
                columnsGet,
                null,
                null,
                null,
                null,
                null
        );
        datos = new ArrayList<>();
        while (cursor.moveToNext()) {
            int age = cursor.getInt(0);
            String n = cursor.getString(1);
            datos.add(age + " : "+ n);
        }
        cursor.close();
        Toast.makeText(this, "-->" + datos.get(0).toString(), Toast.LENGTH_LONG).show();

    }
    private void nomina()
    {
        ArrayList<String> datos;

        String [] columnsGet = {"SUM("+ContractEntries.COLUMN_SALARIO_EMPLEADO+" )"};
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(
                ContractEntries.TABLE_EMPLEADO,
                columnsGet,
                null,
                null,
                null,
                null,
                null
        );
        datos = new ArrayList<>();
        while (cursor.moveToNext()) {
            int cal = cursor.getInt(0);

            datos.add(String.valueOf(cal));
        }
        cursor.close();
        Toast.makeText(this, "Nomina -->" + datos.get(0), Toast.LENGTH_LONG).show();
    }
}