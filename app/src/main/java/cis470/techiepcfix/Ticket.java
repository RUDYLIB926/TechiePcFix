package cis470.techiepcfix;

/**
 * Created by rlibe on 3/22/2017.
 */

import android.os.Parcel;
import android.os.Parcelable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Ticket implements Parcelable {
    private int ticketId;
    private String customerName;
    private Date ticketCreateDate;
    private String problem;
    private String status;
    private Date fixDate;
    public Ticket(){
    }
    public Ticket(int ticketId, String customerName, Date ticketCreateDate, String problem,
                  String status, Date fixDate) {
        this.ticketId = ticketId;
        this.customerName = customerName;
        this.ticketCreateDate = ticketCreateDate;
        this.problem = problem;
        this.status = status;
        this.fixDate = fixDate;
    }
    protected Ticket(Parcel in) {
        this.ticketId = in.readInt();
        this.customerName = in.readString();
        this.ticketCreateDate= new Date(in.readLong());
        this.problem = in.readString();
        this.status = in.readString();
        this.fixDate= new Date(in.readLong());
    }
    public static final Creator<Ticket> CREATOR = new Creator<Ticket>() {
        @Override
        public Ticket createFromParcel(Parcel in) {
            return new Ticket(in);
        }
        @Override
        public Ticket[] newArray(int size) {
            return new Ticket[size];
        }
    };
    public int getTicketId() {
        return ticketId;
    }
    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }
    public String getCustomerName() {
        return customerName;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public Date getTicketCreateDate() {
        return ticketCreateDate;
    }
    public void setTicketCreateDate(Date ticketCreateDate) {
        this.ticketCreateDate = ticketCreateDate;
    }
    public String getProblem() {
        return problem;
    }
    public void setProblem(String problem) {
        this.problem = problem;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public Date getFixDate() {
        return fixDate;
    }
    public void setFixDate(Date fixDate) {
        this.fixDate = fixDate;
    }
    @Override
    public String toString() {
        String temp="";
        temp= "Ticket Id: " + ticketId +"\n"+
                "Customer Name: " + customerName+"\n"+
                "Created Date: " + dateToString(ticketCreateDate)+"\n"+
                "Problem: " + problem+"\n"+
                "Status: " + status+"\n"+
                "Fix Date: " + dateToString(fixDate);
        return temp;
    }
    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ticketId);
        dest.writeString(customerName);
        dest.writeLong(this.ticketCreateDate.getTime());
        dest.writeString(problem);
        dest.writeString(status);
        dest.writeLong(fixDate.getTime());
    }

    public String dateToString(Date date)
    {
        SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
        String strDate = format.format(date);
        return strDate;
    }

    public Date stringToDate(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
        Date date=null;
        try {
            date = formatter.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}// end Ticket.java
