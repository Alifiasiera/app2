import java.util.Date;

public class Expense extends Transaction{
    private String description;
    private double amount;

    public Expense(int id, String description, double amount, Date date) {
        super(id, date);
        this.description = description;
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Deskripsi: " + description + ", Total: " + formatRupiah() + ", Tanggal: " + date;
    }

    public String formatRupiah(){
        return String.format("Rp.%,.2f", amount).replaceAll(",", ".");
    }
}
