package today.theworldover.boxlight;

/**
 * Created by william on 11/29/14.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by william on 11/26/14.
 */
// TO USE:
// Change the package (at top) to match your project.
// Search for "TODO", and make the appropriate changes.
public class DBAdapter {

    /////////////////////////////////////////////////////////////////////
    //	Constants & Data
    /////////////////////////////////////////////////////////////////////
    // For logging:
    private static final String TAG = "DBAdapter";

    // DB Fields
    public static final String KEY_ROWID = "_id";
    public static final int COL_ROWID = 0;
    /*
     * CHANGE 1:
     */
    // TODO: Setup your fields here:
    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_GRADE = "grade";
    public static final String KEY_LETTER = "letter";
    public static final String KEY_INFO = "info";
    public static final String KEY_QUOTE = "quote";
    public static final String KEY_DEMO = "demo";
    public static final String KEY_SERVICE = "service";
    public static final String KEY_PRODEV = "professionaldev";
    public static final String KEY_NOTE = "note";
    public static final String KEY_RATING = "rating";

    // TODO: Setup your field numbers here (0 = KEY_ROWID, 1=...)
    public static final int COL_NAME = 1;
    public static final int COL_EMAIL = 2;
    public static final int COL_GRADE = 3;
    public static final int COL_LETTER = 4;
    public static final int COL_INFO = 5;
    public static final int COL_QUOTE = 6;
    public static final int COL_DEMO = 7;
    public static final int COL_SERVICE = 8;
    public static final int COL_PRODEV = 9;
    public static final int COL_NOTE = 10;
    public static final int COL_RATING = 11;


    public static final String[] ALL_KEYS = new String[] {KEY_ROWID, KEY_NAME, KEY_EMAIL, KEY_GRADE, KEY_LETTER, KEY_INFO, KEY_QUOTE, KEY_DEMO, KEY_SERVICE, KEY_PRODEV, KEY_NOTE, KEY_RATING};

    // DB info: it's name, and the table we are using (just one).
    public static final String DATABASE_NAME = "BoxLight Tablet";
    public static final String DATABASE_TABLE = "mainTable";
    // Track DB version if a new version of your app changes the format.
    public static final int DATABASE_VERSION = 9;

    private static final String DATABASE_CREATE_SQL =
            "create table " + DATABASE_TABLE
                    + " (" + KEY_ROWID + " integer primary key autoincrement, "

			/*
			 * CHANGE 2:
			 */
                    // TODO: Place your fields here!
                    // + KEY_{...} + " {type} not null"
                    //	- Key is the column name you created above.
                    //	- {type} is one of: text, integer, real, blob
                    //		(http://www.sqlite.org/datatype3.html)
                    //  - "not null" means it is a required field (must be given a value).
                    // NOTE: All must be comma separated (end of line!) Last one must have NO comma!!
                    + KEY_NAME + " text not null, "
                    + KEY_EMAIL + " string not null, "
                    + KEY_GRADE + " string not null, "
                    + KEY_LETTER + " integer not null, "
                    + KEY_INFO + " integer not null, "
                    + KEY_QUOTE + " integer not null, "
                    + KEY_DEMO + " integer not null, "
                    + KEY_SERVICE + " integer not null, "
                    + KEY_PRODEV + " integer not null, "
                    + KEY_NOTE + " string not null, "
                    + KEY_RATING + " integer not null"

                    // Rest  of creation:
                    + ");";

    // Context of application who uses us.
    private final Context context;

    private DatabaseHelper myDBHelper;
    private SQLiteDatabase db;

    /////////////////////////////////////////////////////////////////////
    //	Public methods:
    /////////////////////////////////////////////////////////////////////

    public DBAdapter(Context ctx) {
        this.context = ctx;
        myDBHelper = new DatabaseHelper(context);
    }

    // Open the database connection.
    public DBAdapter open() {
        db = myDBHelper.getWritableDatabase();
        return this;
    }

    // Close the database connection.
    public void close() {
        myDBHelper.close();
    }

    // Add a new set of values to the database.
    public long insertRow(String name, String email, String grade, int letter, int info, int quote, int demo, int service, int proDev, String note, float rating) {
		/*
		 * CHANGE 3:
		 */
        // TODO: Update data in the row with new fields.
        // TODO: Also change the function's arguments to be what you need!
        // Create row's data:
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NAME, name);
        initialValues.put(KEY_EMAIL, email);
        initialValues.put(KEY_GRADE, grade);
        initialValues.put(KEY_LETTER, letter);
        initialValues.put(KEY_INFO, info);
        initialValues.put(KEY_QUOTE, quote);
        initialValues.put(KEY_DEMO, demo);
        initialValues.put(KEY_SERVICE, service);
        initialValues.put(KEY_PRODEV, proDev);
        initialValues.put(KEY_NOTE, note);
        initialValues.put(KEY_RATING, rating);

        // Insert it into the database.
        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    // Delete a row from the database, by rowId (primary key)
    public boolean deleteRow(long rowId) {
        String where = KEY_ROWID + "=" + rowId;
        return db.delete(DATABASE_TABLE, where, null) != 0;
    }

    public void deleteAll() {
        Cursor c = getAllRows();
        long rowId = c.getColumnIndexOrThrow(KEY_ROWID);
        if (c.moveToFirst()) {
            do {
                deleteRow(c.getLong((int) rowId));
            } while (c.moveToNext());
        }
        c.close();
    }

    // Return all data in the database.
    public Cursor getAllRows() {
        String where = null;
        Cursor c = 	db.query(true, DATABASE_TABLE, ALL_KEYS,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    // Get a specific row (by rowId)
    public Cursor getRow(long rowId) {
        String where = KEY_ROWID + "=" + rowId;
        Cursor c = 	db.query(true, DATABASE_TABLE, ALL_KEYS,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    // Change an existing row to be equal to new data.
    public boolean updateRow(long rowId, String name, String email, String grade, int letter, int info, int quote, int demo, int service, int proDev, String note, float rating) {
        String where = KEY_ROWID + "=" + rowId;

		/*
		 * CHANGE 4:
		 */
        // TODO: Update data in the row with new fields.
        // TODO: Also change the function's arguments to be what you need!
        // Create row's data:
        ContentValues newValues = new ContentValues();
        newValues.put(KEY_NAME, name);
        newValues.put(KEY_EMAIL, email);
        newValues.put(KEY_GRADE, grade);
        newValues.put(KEY_LETTER, letter);
        newValues.put(KEY_INFO, info);
        newValues.put(KEY_QUOTE, quote);
        newValues.put(KEY_DEMO, demo);
        newValues.put(KEY_SERVICE, service);
        newValues.put(KEY_PRODEV, proDev);
        newValues.put(KEY_NOTE, note);
        newValues.put(KEY_RATING, rating);

        // Insert it into the database.
        return db.update(DATABASE_TABLE, newValues, where, null) != 0;
    }



    /////////////////////////////////////////////////////////////////////
    //	Private Helper Classes:
    /////////////////////////////////////////////////////////////////////

    /**
     * Private class which handles database creation and upgrading.
     * Used to handle low-level database access.
     */
    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);

        }

        @Override
        public void onCreate(SQLiteDatabase _db) {
            _db.execSQL(DATABASE_CREATE_SQL);
        }

        @Override
        public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading application's database from version " + oldVersion
                    + " to " + newVersion + ", which will destroy all old data!");

            // Destroy old database:
            _db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);

            // Recreate new database:
            onCreate(_db);
        }
    }
}


