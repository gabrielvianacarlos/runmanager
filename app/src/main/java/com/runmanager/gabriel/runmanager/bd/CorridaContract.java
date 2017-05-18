package com.runmanager.gabriel.runmanager.bd;

import android.provider.BaseColumns;

/**
 * Created by gabriel on 18/05/17.
 */

public final class CorridaContract {

    private CorridaContract(){}

    public static class CorridaEntry implements BaseColumns {
        public static final String TABLE_NAME = "corrida";
        public static final String COLUMN_NAME_DISTANCIA = "distancia";
        public static final String COLUMN_NAME_DATA = "data";
        public static final String COLUMN_NAME_STATUS = "status";

    }
}
