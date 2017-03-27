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

public class UpdateTicketActivity extends AppCompatActivity {


    Ticket ticket = new Ticket();
    EditText Name, DateCreated, Problem, Status, DateFixed;
    static ArrayList<Ticket> ticketList =new ArrayList<>();
    Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_ticket);

        Bundle extras = getIntent().getExtras();
        ticketList = extras.getParcelableArrayList("TICKETS");
        ticket = extras.getParcelable("TICKET");


        Name = (EditText)findViewById(R.id.update_name);
        Name.setText(ticket.getCustomerName());

        DateCreated = (EditText)findViewById(R.id.update_date_created);
        String date_created = ticket.dateToString(ticket.getTicketCreateDate());
        DateCreated.setText(date_created);

        Problem = (EditText)findViewById(R.id.update_problem);
        Problem.setText(ticket.getProblem());

        Status = (EditText)findViewById(R.id.update_status);
        Status.setText(ticket.getStatus());

        DateFixed = (EditText)findViewById(R.id.update_date_fixed);
        String date_fixed = ticket.dateToString(ticket.getFixDate());
        DateFixed.setText(date_fixed);

    }

    public void setDateCreated(View v){
        DateCreated.setText("");
        DatePickerDialog dateDialog = new DatePickerDialog(
                UpdateTicketActivity.this,
                datePicker1,
                //first time around show current date
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));
        dateDialog.show();
    }

    public void setDateFixed(View v){
        DateFixed.setText("");
        DatePickerDialog dateDialog = new DatePickerDialog(
                UpdateTicketActivity.this,
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
                }
            };

    public void updateTicket(View v) {

        ticket.setTicketId(ticketList.indexOf(ticket)+1);

        ticket.setCustomerName(Name.getText().toString());

        String date_created = new String(DateCreated.toString());
        ticket.setTicketCreateDate(ticket.stringToDate(date_created));

        ticket.setProblem(Problem.getText().toString());

        ticket.setStatus(Status.getText().toString());

        String date_fixed = new String(DateFixed.toString());
        ticket.setFixDate(ticket.stringToDate(date_fixed));

        Intent intent = new Intent(UpdateTicketActivity.this, ListTicketsActivity.class);
        intent.putParcelableArrayListExtra("TICKETS",ticketList );
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_action_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.action_addTicket){
            Intent intent = new Intent(UpdateTicketActivity.this, CreateNewTicketActivity.class);
            intent.putParcelableArrayListExtra("TICKETS",ticketList );
            startActivity(intent);
            return true;
        }
        if(item.getItemId()==R.id.action_ticketList){
            Intent intent = new Intent(UpdateTicketActivity.this, ListTicketsActivity.class);
            intent.putParcelableArrayListExtra("TICKETS",ticketList );
            startActivity(intent);
            return true;
        }
        if(item.getItemId()==R.id.action_settings){
            Toast.makeText(this, "There are no settings yet, Sorry.", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

