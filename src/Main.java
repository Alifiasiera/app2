import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final ExpenseService expenseService = new ExpenseService();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean exit = false;

        while (!exit) {
            System.out.println("Aplikasi Tracking Pengeluaran");
            System.out.println("1. Tambah Data Pengeluaran");
            System.out.println("2. Lihat Data Pengeluaran");
            System.out.println("3. Ubah Data Pengeluaran");
            System.out.println("4. Hapus Data Pengeluaran");
            System.out.println("5. Keluar");
            System.out.print("Masukkan pilihan Anda: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    addExpense();
                    break;
                case 2:
                    viewAllExpenses();
                    break;
                case 3:
                    updateExpense();
                    break;
                case 4:
                    deleteExpense();
                    break;
                case 5:
                    exit = true;
                    System.out.println("Terima kasih telah menggunakan aplikasi ini!");
                    break;
                default:
                    System.out.println("Pilihan tidak valid!");
            }
        }

        scanner.close();
    }

    private static void addExpense() {
        System.out.print("Masukkan deskripsi pengeluaran: ");
        String description = scanner.nextLine();

        System.out.print("Masukkan total pengeluaran: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Consume the newline character

        System.out.print("Masukkan tanggal pengeluaran (yyyy-MM-dd): ");
        String dateString = scanner.nextLine();
        Date date = parseDate(dateString);

        Expense newExpense = new Expense(0, description, amount, date);
        expenseService.addExpense(newExpense);

        System.out.println("Pengeluaran berhasil ditambahkan!");
    }

    private static void viewAllExpenses() {
        List<Expense> expenses = expenseService.getAllExpenses();

        if (expenses.isEmpty()) {
            System.out.println("Tidak ada data pengeluaran");
        } else {
            System.out.println("Daftar Pengeluaran");
            for (Expense expense : expenses) {
                System.out.println(expense);
            }
        }
    }

    private static void updateExpense() {
        System.out.print("Masukkan ID pengeluaran yang ingin diubah: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        Expense existingExpense = expenseService.getExpenseById(id);

        if (existingExpense != null) {
            System.out.print("Masukkan deskripsi baru (tekan Enter untuk tidak mengubah): ");
            String newDescription = scanner.nextLine();
            if (!newDescription.isEmpty()) {
                existingExpense.setDescription(newDescription);
            }

            System.out.print("Masukkan total baru (tekan Enter untuk tidak mengubah): ");
            String newAmountString = scanner.nextLine();
            if (!newAmountString.isEmpty()) {
                double newAmount = Double.parseDouble(newAmountString);
                existingExpense.setAmount(newAmount);
            }

            System.out.print("Masukkan tanggal baru (yyyy-MM-dd) (tekan Enter untuk tidak mengubah): ");
            String newDateString = scanner.nextLine();
            if (!newDateString.isEmpty()) {
                Date newDate = parseDate(newDateString);
                existingExpense.setDate(newDate);
            }

            expenseService.updateExpense(existingExpense);
            System.out.println("Pengeluaran berhasil diubah!");
        } else {
            System.out.println("Pengeluaran tidak ditemukan dengan ID: " + id);
        }
    }

    private static void deleteExpense() {
        System.out.print("Masukkan ID pengeluaran yang ingin dihapus: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        Expense existingExpense = expenseService.getExpenseById(id);

        if (existingExpense != null) {
            expenseService.deleteExpense(id);
            System.out.println("Menghapus pengeluaran dengan ID: " + id);
        } else {
            System.out.println("Pengeluaran tidak ditemukan dengan ID: " + id);
        }
    }

    private static Date parseDate(String dateString) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
        } catch (ParseException e) {
            // tanggal saat ini
            return new Date();
        }
    }
}
