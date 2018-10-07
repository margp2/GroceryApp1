package com.example.android.groceryapp1;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.android.groceryapp1.data.GroceryContract;
import com.example.android.groceryapp1.data.GroceryDbHelper;

public class CatalogActivity extends AppCompatActivity {

    private GroceryDbHelper mDbHelper;

    public static final String LOG_TAG = CatalogActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_catalog );

        mDbHelper = new GroceryDbHelper ( this );
    }

    @Override
    protected void onStart(){
        super.onStart ();
        displayDatabaseInfo();
    }

    private void displayDatabaseInfo() {
        SQLiteDatabase db = mDbHelper.getReadableDatabase ();

        String[] projection = {GroceryContract.GroceryEntry._ID, GroceryContract.GroceryEntry.COLUMN_GROCERY_NAME, GroceryContract.GroceryEntry.COLUMN_GROCERY_PRICE, GroceryContract.GroceryEntry.COLUMN_GROCERY_QUANTITY, GroceryContract.GroceryEntry.COLUMN_GROCERY_SUPPLIER_NAME, GroceryContract.GroceryEntry.COLUMN_GROCERY_SUPPLER_PHONE};
        Log.v ( LOG_TAG, "Projection created" );

        Cursor cursor = db.query ( GroceryContract.GroceryEntry.TABLE_NAME, projection, null, null, null, null, null );
        Log.v ( LOG_TAG, "Able to query the database" );

        TextView displayView = findViewById ( R.id.text_view_inventory );

        try {
            displayView.setText ( "The grocery inventory contain " + cursor.getCount () + " groceries. \n\n" );
            displayView.append ( GroceryContract.GroceryEntry._ID + " - " + GroceryContract.GroceryEntry.COLUMN_GROCERY_NAME + " - " + GroceryContract.GroceryEntry.COLUMN_GROCERY_PRICE + " - " + GroceryContract.GroceryEntry.COLUMN_GROCERY_QUANTITY + " - " + GroceryContract.GroceryEntry.COLUMN_GROCERY_SUPPLIER_NAME + " - " + GroceryContract.GroceryEntry.COLUMN_GROCERY_SUPPLER_PHONE + ".\n" );
            Log.v ( LOG_TAG, " Can display the data table" );

            int idColumnIndex = cursor.getColumnIndex ( GroceryContract.GroceryEntry._ID );
            int nameColumnIndex = cursor.getColumnIndex ( GroceryContract.GroceryEntry.COLUMN_GROCERY_NAME );
            int priceColumnIndex = cursor.getColumnIndex ( GroceryContract.GroceryEntry.COLUMN_GROCERY_PRICE );
            int quantityColumnIndex = cursor.getColumnIndex ( GroceryContract.GroceryEntry.COLUMN_GROCERY_QUANTITY );
            int suppNameColumnIndex = cursor.getColumnIndex ( GroceryContract.GroceryEntry.COLUMN_GROCERY_SUPPLIER_NAME );
            int suppPhoneColumnIndex = cursor.getColumnIndex ( GroceryContract.GroceryEntry.COLUMN_GROCERY_SUPPLER_PHONE );
            Log.v ( LOG_TAG, "The indexing method works!" );

            while (cursor.moveToNext ()) {
                int currentID = cursor.getInt ( idColumnIndex );
                String currentName = cursor.getString ( nameColumnIndex );
                int currentPrice = cursor.getInt ( priceColumnIndex );
                int currentQuantity = cursor.getInt ( quantityColumnIndex );
                String currentSuppName = cursor.getString ( suppNameColumnIndex );
                int currentSuppPhone = cursor.getInt ( suppPhoneColumnIndex );

                displayView.append ( ("\n" + currentID + " - " + currentName + " - " + currentPrice + " - " + currentQuantity + " - " + currentSuppName + " - " + currentSuppPhone + " - ") );
            }

        } finally {
            cursor.close ();
        }
    }
        private void insertGrocery(){
            SQLiteDatabase db = mDbHelper.getWritableDatabase ();

            ContentValues values = new ContentValues (  );
            values.put ( GroceryContract.GroceryEntry.COLUMN_GROCERY_NAME, "Grocery" );
            values.put ( GroceryContract.GroceryEntry.COLUMN_GROCERY_PRICE, 0 );
            values.put ( GroceryContract.GroceryEntry.COLUMN_GROCERY_QUANTITY, 0 );
            values.put ( GroceryContract.GroceryEntry.COLUMN_GROCERY_SUPPLIER_NAME, "supplier" );
            values.put ( GroceryContract.GroceryEntry.COLUMN_GROCERY_SUPPLER_PHONE, 0 );

            long newRowId = db.insert ( GroceryContract.GroceryEntry.TABLE_NAME, null, values );
            Log.v ( LOG_TAG, "Values are able to be inserted" );
        }

}
