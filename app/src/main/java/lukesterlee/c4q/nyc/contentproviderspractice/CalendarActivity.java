package lukesterlee.c4q.nyc.contentproviderspractice;

import android.app.Activity;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by Willee on 7/21/15.
 */
public class CalendarActivity extends Activity {


    private ListView mListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        mListView = (ListView) findViewById(R.id.listView);

        fetchCalendars();
        fetchEvents();
        insertEvent();
    }

    public void fetchCalendars() {
        Uri uri = CalendarContract.Calendars.CONTENT_URI;
        String[] columns = new String[]{
                CalendarContract.Calendars._ID,
                CalendarContract.Calendars.ACCOUNT_NAME,
                CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,
                CalendarContract.Calendars.OWNER_ACCOUNT
        };

        Cursor cursor = getContentResolver().query(
                uri,
                columns,
                CalendarContract.Calendars.ACCOUNT_NAME + " = ?",
                new String[]{"lukesterlee@gmail.com"},
                null);

        while(cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndex(CalendarContract.Calendars._ID));
            String accountName = cursor.getString(1);
            String displayName = cursor.getString(2);
            String owner = cursor.getString(3);
            Log.v("ContentProvider" , "ID : " + id + " ACCOUNT NAME : " + accountName + " DISPLAY NAME : " + displayName + " OWNER : " + owner);
        }
    }

    public void fetchEvents() {
        Uri uri = CalendarContract.Events.CONTENT_URI;
        String[] columns = new String[]{
                CalendarContract.Events._ID,
                CalendarContract.Events.CALENDAR_ID,
                CalendarContract.Events.ORGANIZER,
                CalendarContract.Events.TITLE,
                CalendarContract.Events.EVENT_LOCATION,
                CalendarContract.Events.DESCRIPTION,
                CalendarContract.Events.DTSTART,
                CalendarContract.Events.DTEND
        };

        long current = System.currentTimeMillis();

        Calendar calendar = Calendar.getInstance();
        calendar.set(2015, Calendar.JULY, 21);
        long today = calendar.getTimeInMillis();

        String filter = CalendarContract.Events.CALENDAR_ID + " = ? AND " + CalendarContract.Events.DTSTART + " > ?";
        String[] filterArgs = new String[]{"5", Long.toString(today)};
        String sortOrder = CalendarContract.Events.DTSTART + " DESC";

        Cursor cursor = getContentResolver().query(
                uri,
                columns,
                filter,
                filterArgs,
                sortOrder
        );

        ListAdapter listAdapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_2,
                cursor,
                new String[]{CalendarContract.Events._ID, CalendarContract.Events.TITLE},
                new int[]{android.R.id.text1, android.R.id.text2},
                0);
        mListView.setAdapter(listAdapter);

    }

    public void insertEvent() {
        long calendarId = 1;

        Calendar beginTime = Calendar.getInstance();
        beginTime.set(2015, Calendar.JULY, 21, 19, 0);
        long startMillis = beginTime.getTimeInMillis();

        Calendar endTime = Calendar.getInstance();
        endTime.set(2015, Calendar.JULY, 21, 22, 0);
        long endMillis = endTime.getTimeInMillis();

        ContentValues values = new ContentValues();
        values.put(CalendarContract.Events.DTSTART, startMillis);
        values.put(CalendarContract.Events.DTEND, endMillis);
        values.put(CalendarContract.Events.TITLE, "Access Code lalala");
        values.put(CalendarContract.Events.DESCRIPTION, "testing lalalal");
        values.put(CalendarContract.Events.CALENDAR_ID, "1");
        values.put(CalendarContract.Events.EVENT_TIMEZONE, "America/New_York");

        Uri uri = getContentResolver().insert(CalendarContract.Events.CONTENT_URI, values);


        Toast.makeText(this, "saved!!!", Toast.LENGTH_SHORT).show();
    }

    public void updateEvent() {

        ContentValues values = new ContentValues();
        values.put(CalendarContract.Events.TITLE, "Bladjflakjds;f");

        // content://com.android.calendar/events/3225
        Uri uri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, 1);
        getContentResolver().update(uri, values, null, null);
    }

    public void deleteEvent() {
        Uri uri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, 1);
        getContentResolver().delete(uri, null, null);
    }
}
