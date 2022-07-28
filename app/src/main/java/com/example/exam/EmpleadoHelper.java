package com.example.exam;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.exam.ReaderContract.ContractEntries;

public class EmpleadoHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "patito.db";

    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE "+ ContractEntries.TABLE_EMPLEADO +" ("
            +ContractEntries.COLUMN_NUMERO_EMPLEADO+" INTEGER  PRIMARY KEY , "
            +ContractEntries.COLUMN_APELLIDOS_EMPLEADO+" TEXT, "
            +ContractEntries.COLUMN_NOMBRE_EMPLEADO+" TEXT, "
            +ContractEntries.COLUMN_DIRECCION_EMPLEADO+" TEXT, "
            +ContractEntries.COLUMN_TELEFONO_EMPLEADO+"  TEXT,"
            +ContractEntries.COLUMN_EDAD_EMPLEADO+" INTEGER, "
            +ContractEntries.COLUMN_ANTIGUEDAD_EMPLEADO+" DOUBLE, "
            +ContractEntries.COLUMN_SALARIO_EMPLEADO+" DOUBLE)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ContractEntries.TABLE_EMPLEADO;
    public EmpleadoHelper (Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
