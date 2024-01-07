import java.util.Date;

public class Transaction {
    protected int id;
    protected Date date;

    public Transaction(int id, Date date) {
        this.id = id;
        this.date = date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }
}