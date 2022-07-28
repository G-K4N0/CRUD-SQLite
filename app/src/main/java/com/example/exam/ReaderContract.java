package com.example.exam;


import android.provider.BaseColumns;

public final class ReaderContract {

    public static class ContractEntries implements BaseColumns {
        public static final String TABLE_EMPLEADO = "empleado";

        public static final String COLUMN_NUMERO_EMPLEADO = "id";
        public static final String COLUMN_NOMBRE_EMPLEADO = "nombre";
        public static final String COLUMN_APELLIDOS_EMPLEADO="apellidos";
        public static final String COLUMN_DIRECCION_EMPLEADO="direccion";
        public static final String COLUMN_TELEFONO_EMPLEADO ="telefono";
        public static final String COLUMN_EDAD_EMPLEADO ="edad";
        public static final String COLUMN_ANTIGUEDAD_EMPLEADO ="antiguedad";
        public static final String COLUMN_SALARIO_EMPLEADO ="salario";

    }
}
