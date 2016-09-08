package com.example.contactstest;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Contacts;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView contacts;
    private String[] columns = {Contacts._ID,
            Contacts.DISPLAY_NAME,
            Phone.NUMBER,
            Phone.CONTACT_ID,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contacts = (TextView) findViewById(R.id.contacts);
        contacts.setText(getQueryData());
    }

    public String getQueryData() {
        StringBuilder sb = new StringBuilder();
        Cursor cursor = getContentResolver().query(Contacts.CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext()) {
            int idindex = cursor.getColumnIndex(columns[0]);
            int displaynameindex = cursor.getColumnIndex(columns[1]);
            String displayname = cursor.getString(displaynameindex);
            int id = cursor.getInt(idindex);
            Cursor phone = getContentResolver().query(Phone.CONTENT_URI, null, columns[3] + "=" + id, null, null);
            while (phone.moveToNext()) {
                int phonenumberindex = phone.getColumnIndex(columns[2]);
                String phonenumner = phone.getString(phonenumberindex);
                sb.append(id + displayname + ":" + phonenumner + "\n");
            }
        }
        cursor.close();
        return sb.toString();

    }
}
