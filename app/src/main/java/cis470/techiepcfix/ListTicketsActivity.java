package cis470.techiepcfix;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListTicketsActivity extends AppCompatActivity {

    private ArrayList<Ticket> ticketList = new ArrayList<>();
    private ArrayAdapter<Ticket> adapter;
    private ListView Ticket_List;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_tickets);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            ticketList = extras.getParcelableArrayList("TICKETS");
        }else{
            Ticket ticket = new Ticket(0, "not real", null, "no problem", "no status", null);
            ticketList.add(ticket);
        }


        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ticketList);

        Ticket_List = (ListView) findViewById(R.id.ticket_list);
        Ticket_List.setAdapter(adapter);
        registerForContextMenu(Ticket_List);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu,
                                    View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.update_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();

        if(item.getItemId()==R.id.update_update){
            Intent intent = new Intent(ListTicketsActivity.this, UpdateTicketActivity.class);
            intent.putParcelableArrayListExtra("TICKETS",ticketList );
            int sendTicketIndex = info.position;
            intent.putExtra("MyTicketIndex", sendTicketIndex);
            startActivity(intent);
            finish();
            return true;
        }

        if(item.getItemId()==R.id.update_delete){
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            deleteTicket(info.position);
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            break;
                    }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(ListTicketsActivity.this);
            builder.setTitle("Delete Ticket").setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();

            return true;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_action_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.action_addTicket){
            Intent intent = new Intent(ListTicketsActivity.this, CreateNewTicketActivity.class);
            intent.putParcelableArrayListExtra("TICKETS",ticketList );
            startActivity(intent);
            finish();
            return true;
        }
        if(item.getItemId()==R.id.action_ticketList){
            Intent intent = new Intent(ListTicketsActivity.this, ListTicketsActivity.class);
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

    public void deleteTicket(int position){
        Ticket ticket1;
        ticketList.remove(position);
        for(int i=0; i<ticketList.size(); i++){
            ticket1 = ticketList.get(i);
            ticket1.setTicketId(i+1);
        }
        adapter = new ArrayAdapter<>(ListTicketsActivity.this, android.R.layout.simple_list_item_1, ticketList);
        Ticket_List = (ListView) findViewById(R.id.ticket_list);
        Ticket_List.setAdapter(adapter);
    }
}
