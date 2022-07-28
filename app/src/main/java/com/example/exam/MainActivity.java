package com.example.exam;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.exam.ReaderContract.ContractEntries;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Button btnAgregar,btnConsultar;
    private EditText txtId,txtName,txtLastName,txtDirection,txtPhone,txtAge,txtLabor_Old,txtSalary;


    EmpleadoHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnAgregar = findViewById(R.id.btnAgregar);
        btnConsultar = findViewById(R.id.btnConsultar);

        txtId = findViewById(R.id.txtId);
        txtName = findViewById(R.id.txtNombre);
        txtLastName = findViewById(R.id.txtApellidos);
        txtDirection = findViewById(R.id.txtDireccion);
        txtPhone = findViewById(R.id.txtTelefono);
        txtAge = findViewById(R.id.txtEdad);
        txtLabor_Old = findViewById(R.id.txtAntiguedad);
        txtSalary = findViewById(R.id.txtSalario);

        helper = new EmpleadoHelper(this);

        btnAgregar.setOnClickListener(view -> {
            String name,last_name,direction,phone;
            int id,age;
            double labor_old,salary;

            id =Integer.parseInt(txtId.getText().toString());
            name =txtName.getText().toString();
            last_name = txtLastName.getText().toString();
            direction =txtDirection.getText().toString();
            phone = txtPhone.getText().toString();
            age = Integer.parseInt(txtAge.getText().toString());
            labor_old = Double.parseDouble(txtLabor_Old.getText().toString());
            salary = Double.parseDouble(txtSalary.getText().toString());

            SQLiteDatabase db = helper.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(ContractEntries.COLUMN_NUMERO_EMPLEADO,id);
            values.put(ContractEntries.COLUMN_APELLIDOS_EMPLEADO,last_name);
            values.put(ContractEntries.COLUMN_NOMBRE_EMPLEADO,name);
            values.put(ContractEntries.COLUMN_DIRECCION_EMPLEADO,direction);
            values.put(ContractEntries.COLUMN_TELEFONO_EMPLEADO,phone);
            values.put(ContractEntries.COLUMN_EDAD_EMPLEADO,age);
            values.put(ContractEntries.COLUMN_ANTIGUEDAD_EMPLEADO,labor_old);
            values.put(ContractEntries.COLUMN_SALARIO_EMPLEADO,salary);

            long newRowId = db.insert(ContractEntries.TABLE_EMPLEADO, null, values);
            Toast.makeText(MainActivity.this, "-> "+ newRowId, Toast.LENGTH_SHORT).show();

        });
        btnConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Listado.class);
                startActivity(intent);
            }
        });
    }


}