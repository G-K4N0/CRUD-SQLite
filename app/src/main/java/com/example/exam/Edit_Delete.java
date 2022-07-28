package com.example.exam;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.exam.ReaderContract.ContractEntries;

import java.util.ArrayList;

public class Edit_Delete extends AppCompatActivity {
    EmpleadoHelper helper;
    ArrayList<Empleado> listaEmpleado;

    private Button btnEditar,btnEliminar,btnBuscar;
    private EditText txtId,txtName,txtLastName,txtDirection,txtPhone,txtAge,txtLabor_Old,txtSalary;
    String name,last_name,direction,phone;
    int id_em,age;
    double labor_old,salary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_delete);

         helper= new EmpleadoHelper(this);

        btnBuscar = findViewById(R.id.btnBuscar);
        btnEditar = findViewById(R.id.btnEditar);
        btnEliminar = findViewById(R.id.btnEliminar);

        txtId = findViewById(R.id.txt_Id);
        txtName = findViewById(R.id.txt_Nombre);
        txtLastName = findViewById(R.id.txt_Apellidos);
        txtDirection = findViewById(R.id.txt_Direccion);
        txtPhone = findViewById(R.id.txt_Telefono);
        txtAge = findViewById(R.id.txt_Edad);
        txtLabor_Old = findViewById(R.id.txt_Antiguedad);
        txtSalary = findViewById(R.id.txt_Salario);

        Bundle bundle =getIntent().getExtras();

        if (bundle != null)
        {


            id_em = bundle.getInt("id");
            name =bundle.getString("name");
            last_name = bundle.getString("last_name");
            direction = bundle.getString("direction");
            phone = bundle.getString("phone");
            age= bundle.getInt("age");
            labor_old = bundle.getDouble("labor_old");
            salary = bundle.getDouble("salary");
        }
        pasarDatos();

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = helper.getWritableDatabase();

                String selection = ContractEntries.COLUMN_NUMERO_EMPLEADO + " LIKE ?";
                String [] args = new String[]{String.valueOf(id_em)};

                db.delete(ContractEntries.TABLE_EMPLEADO,selection,args);

            }
        });

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = helper.getWritableDatabase();


                String name_,last_name_,direction_,phone_;
                int age_;
                double labor_old_,salary_;

                last_name_=txtLastName.getText().toString();
                name_ = txtName.getText().toString();
                direction_ = txtDirection.getText().toString();
                phone_ = txtPhone.getText().toString();
                age_ = Integer.parseInt(txtAge.getText().toString());
                labor_old_ = Double.parseDouble(txtLabor_Old.getText().toString());
                salary_ = Double.parseDouble(txtSalary.getText().toString());

                ContentValues values = new ContentValues();
                values.put(ContractEntries.COLUMN_APELLIDOS_EMPLEADO,last_name_);
                values.put(ContractEntries.COLUMN_NOMBRE_EMPLEADO,name_);
                values.put(ContractEntries.COLUMN_DIRECCION_EMPLEADO,direction_);
                values.put(ContractEntries.COLUMN_TELEFONO_EMPLEADO,phone_);
                values.put(ContractEntries.COLUMN_EDAD_EMPLEADO,age_);
                values.put(ContractEntries.COLUMN_ANTIGUEDAD_EMPLEADO,labor_old_);
                values.put(ContractEntries.COLUMN_SALARIO_EMPLEADO,salary_);

                String selection = ContractEntries.COLUMN_NUMERO_EMPLEADO + " LIKE ?";

                String [] args = {String.valueOf(id_em)};

                db.update(ContractEntries.TABLE_EMPLEADO,values,selection,args);

            }
        });
        btnBuscar.setOnClickListener(v -> buscarNombre());

    }
    private void pasarDatos()
    {
        txtId.setText(String.valueOf(id_em));
        txtName.setText(name);
        txtLastName.setText(last_name);
        txtDirection.setText(direction);
        txtPhone.setText(phone);
        txtAge.setText(String.valueOf(age));
        txtLabor_Old.setText(String.valueOf(labor_old));
        txtSalary.setText(String.valueOf(salary));
    }
    @SuppressLint("Range")
    private void buscarNombre()
    {
        try {
            String nombre = txtName.getText().toString();
            listaEmpleado = new ArrayList<>();

            SQLiteDatabase db = helper.getReadableDatabase();

            String selection = ContractEntries.COLUMN_NOMBRE_EMPLEADO + " LIKE ?";
            String [] args = {nombre};
            Cursor cursor = db.query(
                    ContractEntries.TABLE_EMPLEADO,
                    null,
                    selection,
                    args,
                    null,
                    null,
                    null
            );

            while (cursor.moveToNext()) {
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

                listaEmpleado.add(new Empleado(id, last_name, name, direction, phone, age, labor_old, salary));
                Toast.makeText(this, "->" + last_name, Toast.LENGTH_SHORT).show();

                txtId.setText(String.valueOf(id));
                txtLastName.setText(last_name);
                txtDirection.setText(direction);
                txtPhone.setText(phone);
                txtAge.setText(String.valueOf(age));
                txtLabor_Old.setText(String.valueOf(labor_old));
                txtSalary.setText(String.valueOf(String.valueOf(salary)));
            }
            cursor.close();
        }catch (Exception e){
            Toast.makeText(this, "Busca por nombre", Toast.LENGTH_SHORT).show();
        }

    }
}