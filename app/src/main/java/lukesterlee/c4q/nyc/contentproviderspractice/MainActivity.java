package lukesterlee.c4q.nyc.contentproviderspractice;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends ActionBarActivity {

    @Bind(R.id.editText_title) EditText mEditTextTitle;
    @Bind(R.id.editText_description) EditText mEditTextDescription;
    @Bind(R.id.button_create_event) Button mButtonCreateEvent;

    @Bind(R.id.start_month) EditText mEditTextStartMonth;
    @Bind(R.id.start_day) EditText mEditTextStartDay;
    @Bind(R.id.start_hour) EditText mEditTextStartHour;

    @Bind(R.id.end_month) EditText mEditTextEndMonth;
    @Bind(R.id.end_day) EditText mEditTextEndDay;
    @Bind(R.id.end_hour) EditText mEditTextEndHour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        mButtonCreateEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                insertEvent();

//                Intent intent = new Intent(Intent.ACTION_INSERT);
//                intent.setData(CalendarContract.CONTENT_URI);
//                intent.putExtra(CalendarContract.Events.TITLE, mEditTextTitle.getText().toString());
//                intent.putExtra(CalendarContract.Events.DESCRIPTION, mEditTextDescription.getText().toString());
//                intent.putExtra(CalendarContract.Events.EVENT_LOCATION, mEditTextLocation.getText().toString());
//                intent.putExtra(Intent.EXTRA_EMAIL, "luksterlee@gmail.com");
//                startActivity(intent);
            }
        });
    }

    public void insertEvent() {
        long calendarId = 1;

        Calendar beginTime = Calendar.getInstance();
        beginTime.set(2015, Integer.parseInt(mEditTextStartMonth.getText().toString())-1,
                Integer.parseInt(mEditTextStartDay.getText().toString()),
                Integer.parseInt(mEditTextStartHour.getText().toString()), 0);
        long startMillis = beginTime.getTimeInMillis();

        Calendar endTime = Calendar.getInstance();
        endTime.set(2015, Integer.parseInt(mEditTextEndMonth.getText().toString())-1,
                Integer.parseInt(mEditTextEndDay.getText().toString()),
                Integer.parseInt(mEditTextEndHour.getText().toString()), 0);
        long endMillis = endTime.getTimeInMillis();

        ContentValues values = new ContentValues();
        values.put(CalendarContract.Events.DTSTART, startMillis);
        values.put(CalendarContract.Events.DTEND, endMillis);
        values.put(CalendarContract.Events.TITLE, mEditTextTitle.getText().toString());
        values.put(CalendarContract.Events.DESCRIPTION, mEditTextDescription.getText().toString());
        values.put(CalendarContract.Events.CALENDAR_ID, "1");
        values.put(CalendarContract.Events.EVENT_TIMEZONE, "America/New_York");

        Uri uri = getContentResolver().insert(CalendarContract.Events.CONTENT_URI, values);


        Toast.makeText(this, "saved!!!", Toast.LENGTH_SHORT).show();
    }

}