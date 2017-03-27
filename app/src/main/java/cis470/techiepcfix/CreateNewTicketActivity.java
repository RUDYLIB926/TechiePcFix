package cis470.techiepcfix;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

public class CreateNewTicketActivity extends AppCompatActivity {

    private EditText DateCreated, DateFixed;
    private Calendar myCalendar = Calendar.getInstance();
    private Date date1, date2;
    private ArrayList<Ticket> ticketList =new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_ticket);
        Bundle extras = getIntent().getExtras();
        //check if anything was passed in from other activities
        //it is null on launch
        if (extras != null) {
            ticketList = extras.getParcelableArrayList("TICKETS");
        }
        DateCreated = (EditText) findViewById(R.id.date_created);
        DateFixed = (EditText) findViewById(R.id.date_fixed);

//        for debugging
//        Ticket one = new Ticket(1,"Name1", myCalendar.getTime(), "things", "ture", myCalendar.getTime());
//        Ticket two = new Ticket(2, "Name2", myCalendar.getTime(), "things", "ture", myCalendar.getTime());
//        ticketList.add(one);
//        ticketList.add(two);
    }

    public void setDateCreated(View v){
        DatePickerDialog dateDialog = new DatePickerDialog(
                CreateNewTicketActivity.this,
                datePicker1,
                //first time around show current date
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));
        dateDialog.show();
    }

    public void setDateFixed(View v){
        DatePickerDialog dateDialog = new DatePickerDialog(
                CreateNewTicketActivity.this,
                datePicker2,
                //first time around show current date
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));
        dateDialog.show();
    }


    DatePickerDialog.OnDateSetListener datePicker1 = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, month);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    DateCreated.setText(month+1+"/"+dayOfMonth+"/"+year );
                    date1 = myCalendar.getTime();

                }
            };

    DatePickerDialog.OnDateSetListener datePicker2 = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, month);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    DateFixed.setText(month+1+"/"+dayOfMonth+"/"+year );
                    date2 = myCalendar.getTime();
                }
            };


    public void createNewTicket(View v) {

        Ticket newTicket = new Ticket();
        ticketList.add(newTicket);

        //TODO: add try catch blocks for null pointer values or if else condition
        newTicket.setTicketId(ticketList.indexOf(newTicket)+1);

        EditText Name = (EditText)findViewById(R.id.name);
        newTicket.setCustomerName(Name.getText().toString());

        newTicket.setTicketCreateDate(date1);

        EditText Problem = (EditText)findViewById(R.id.problem);
        newTicket.setProblem(Problem.getText().toString());

        EditText Status = (EditText)findViewById(R.id.status);
        newTicket.setStatus(Status.getText().toString());

        newTicket.setFixDate(date2);

        Intent intent = new Intent(CreateNewTicketActivity.this, ListTicketsActivity.class);
        intent.putParcelableArrayListExtra("TICKETS",ticketList );
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_action_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.action_addTicket){
            Intent intent = new Intent(CreateNewTicketActivity.this, CreateNewTicketActivity.class);
            intent.putParcelableArrayListExtra("TICKETS",ticketList );
            startActivity(intent);
            finish();
            return true;
        }
        if(item.getItemId()==R.id.action_ticketList){
            Intent intent = new Intent(CreateNewTicketActivity.this, ListTicketsActivity.class);
            intent.putParcelableArrayListExtra("TICKETS",ticketList );
            startActivity(intent);
            finish();
            return true;
        }
        if(item.getItemId()==R.id.action_settings){
            Toast.makeText(this, "There are no settings yet, Sorry.", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

