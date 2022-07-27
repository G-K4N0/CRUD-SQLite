package com.example.exam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.exam.ReaderContract.ContractEntries;

public class Edit_Delete extends AppCompatActivity {
    EmpleadoHelper helper;

    private Button btnEditar,btnEliminar;
    private EditText txtId,txtName,txtLastName,txtDirection,txtPhone,txtAge,txtLabor_Old,txtSalary;
    String name,last_name,direction,phone;
    int id_em,age;
    double labor_old,salary;
    String dato="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_delete);

         helper= new EmpleadoHelper(this);

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

                String sql = "delete from "+ContractEntries.TABLE_EMPLEADO+" where "+ContractEntries.COLUMN_NUMERO_EMPLEADO+"="+id_em;
                db.execSQL(sql);
                db.close();
            }
        });

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });

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
}