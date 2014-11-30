package today.theworldover.boxlight;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import today.theworldover.boxlight.R;

/**
 * Created by william on 11/26/14.
 */
public class EmailScreen extends Activity {

    DBAdapter myDb;
    EditText edtTextName;
    EditText edtTextEmail;
    String grade;
    RatingBar mRate;
    CheckBox newLetter, info, quote, demo, service, proDev;
    //SeeRecords recSet;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.email_screen);

        edtTextName = (EditText) findViewById(R.id.name);
        edtTextEmail = (EditText) findViewById(R.id.email_address);
        mRate = (RatingBar) findViewById(R.id.ratingBar);

        //String name = edtTextName.getText().toString();
        //String email = edtTextEmail.getText().toString();
        Button submit = (Button) findViewById(R.id.submit_button);
        openDB();
        checkExternalMedia();
        submit.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                //sendEmail();
                AddRecord();
                // after sending the email, clear the fields
                edtTextName.setText("");
                edtTextEmail.setText("");

                Toast.makeText(EmailScreen.this, getString(R.string.thank_you), Toast.LENGTH_LONG).show();
                Cursor cursor = myDb.getAllRows();
                displayRecordSet(cursor);
                finish();
            }
        });

        //writeToSD();

    }



    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        //Check which radio button is checked
        switch(view.getId()) {
            case R.id.rbk:
                if (checked) { grade = "Kindergarten";} break;
            case R.id.rb1:
                if (checked) { grade = "1st";} break;
            case R.id.rb2:
                if (checked) { grade = "2nd";} break;
            case R.id.rb3:
                if (checked) { grade = "3rd";} break;
            case R.id.rb4:
                if (checked) { grade = "4th"; } break;
            case R.id.rb5:
                if (checked) { grade = "5th";} break;
            case R.id.rb6:
                if (checked) { grade = "6th";} break;
            case R.id.rb7:
                if (checked) { grade = "7th";} break;
            case R.id.rb8:
                if (checked) { grade = "8th";} break;
            case R.id.rb9:
                if (checked) { grade = "9th";} break;
            case R.id.rb10:
                if (checked) { grade = "10th"; } break;
            case R.id.rb11:
                if (checked) { grade = "11th";} break;
            case R.id.rb12:
                if (checked) { grade = "12th";} break;
            default:
                grade = "Not Specified";
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeDB();
    }

    private void openDB() {
        myDb = new DBAdapter(this);
        myDb.open();
    }

    private void closeDB() {
        myDb.close();
    }



    public void AddRecord() {
        //displayText("Clicked add record!");
        //EditText edtTextName = (EditText) findViewById(R.id.name);
        //EditText edtTextEmail = (EditText) findViewById(R.id.email_address);
        String name = edtTextName.getEditableText().toString();
        String email = edtTextEmail.getEditableText().toString();
        String thisGrade = grade;

        float rate = mRate.getRating();

        long newId = myDb.insertRow(name, email, thisGrade, rate);

        String thisEntry = "Record " + newId + " Name: " + name + " Email: " + email + " Grade: " + grade + " Interest rating: " + rate;
        Log.v("Hey billyyy! ", "" + thisEntry);

        // Query for the record we just added.
        // Use the ID:
        Cursor cursor = myDb.getRow(newId);
        displayRecordSet(cursor);
    }


    //this is a test
   /* private void displayText(String message) {
        TextView textView = (TextView) findViewById(R.id.textDisplay);
        textView.setText(message);
    }*/

    public void displayRecordSet(Cursor cursor) {
        String message = "";
        // populate the message from the cursor

        // Reset cursor to start, checking to see if there's data:
        if (cursor.moveToFirst()) {
            do {
                // Process the data:
                int id = cursor.getInt(DBAdapter.COL_ROWID);
                String name = cursor.getString(DBAdapter.COL_NAME);
                String email = cursor.getString(DBAdapter.COL_EMAIL);
                String grade = cursor.getString(DBAdapter.COL_GRADE);
                int rating = cursor.getInt(DBAdapter.COL_RATING);

                // Append data to the message:
                message += "id=" + id
                        +", name=" + name
                        +", email=" + email
                        +", grade=" + grade
                        +", rating=" + rating
                        +"\n";
            } while(cursor.moveToNext());
            //Toast.makeText(EmailScreen.this, message, Toast.LENGTH_LONG).show();
            Log.v("test", message);
            writeToSD(message);
        }

        // Close the cursor to avoid a resource leak.
        cursor.close();

        //displayText(message);
    }
    //  ---can't figure out why this shit is giving me errors

    private void checkExternalMedia() {
        boolean mExternalStorageAvailable;
        boolean mExternalStorageWritable;
        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            mExternalStorageAvailable = mExternalStorageWritable = true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            mExternalStorageAvailable = true;
            mExternalStorageWritable = false;
        } else {
            mExternalStorageAvailable = mExternalStorageWritable = false;
        }
        Log.v("sd card", "\nExternal readable= " + mExternalStorageAvailable + "writable= " + mExternalStorageWritable);
    }

    private void writeToSD(String message) {

        File root = android.os.Environment.getExternalStorageDirectory();

        Log.v("Root Dir: ", "" + root);
        
        File dir = new File(root.getAbsolutePath());
        dir.mkdirs();
        File file = new File(dir, "boxlight_lacue.txt");

        try {
            FileOutputStream f = new FileOutputStream(file);
            PrintWriter pw = new PrintWriter(f);
            pw.println(R.string.app_name);
            pw.println(message);
            pw.flush();
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

//    protected void sendEmail() {
//        String[] recipient = {edtTextEmail.getText().toString() };
//
//        File fileLocation = new File (Environment.getExternalStorageDirectory(), "/mnt/external_sd/file.txt");
//        Uri U = Uri.fromFile(fileLocation);
//        Intent email = new Intent(Intent.ACTION_SEND, Uri.parse("mailto:"));
//        email.setType("text/plain");
//
//        email.putExtra(Intent.EXTRA_EMAIL, recipient);
//        email.putExtra(Intent.EXTRA_SUBJECT, "Its that thing you were looking for...");
//        email.putExtra(Intent.EXTRA_TEXT, "Hey hey, I can't believe it worked!/n/n");
//        email.putExtra(Intent.EXTRA_STREAM, U);
//
//
//        try {
//            startActivity(Intent.createChooser(email, "Choose an email client from..."));
//        } catch (ActivityNotFoundException ex) {
//            Toast.makeText(EmailScreen.this, "No email client found.", Toast.LENGTH_LONG).show();
//        }
//        Toast.makeText(EmailScreen.this, getString(R.string.thank_you), Toast.LENGTH_LONG).show();
//        Log.i("Finished sending email...", "");
//    }
}
