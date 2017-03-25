package cis470.techiepcfix;

import android.content.Intent;
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

    static ArrayList<Ticket> ticketList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_tickets);
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            ticketList= extras.getParcelableArrayList("TICKETS");
        }

        ArrayAdapter<Ticket> adapter = new ArrayAdapter<>(this, R.layout.tickets, ticketList);

        ListView Ticket_List = (ListView) findViewById(R.id.ticket_list);
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
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        if(item.getItemId()==R.id.update_update){
            Intent intent = new Intent(ListTicketsActivity.this, UpdateTicketActivity.class);
            intent.putExtra("TICKET", info.position);
            startActivity(intent);
            return true;
        }
        if(item.getItemId()==R.id.update_delete){
            ticketList.remove(info.position);
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
        if(item.getItemId()==R.id.action_addTieckt){
            startActivity(new Intent(ListTicketsActivity.this, CreateNewTicketActivity.class));
            return true;
        }
        if(item.getItemId()==R.id.action_ticketList){
            startActivity(new Intent(ListTicketsActivity.this, ListTicketsActivity.class));
            return true;
        }
        if(item.getItemId()==R.id.action_settings){
            Toast.makeText(this, "There are no settings yet, Sorry.", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
