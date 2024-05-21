import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Transaction {
    private String type;
    private long amount;

    public Transaction(String type, long amount) {
        this.type = type;
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public long getAmount() {
        return amount;
    }
}

class BankDetails {
    private String accno;
    private String name;
    private String acc_type;
    private long balance;
    private List<Transaction> transactions;
    Scanner sc = new Scanner(System.in);

    public BankDetails() {
        transactions = new ArrayList<>();
    }

    public void openAccount() {
        System.out.print("Enter Account No: ");
        accno = sc.next();
        System.out.print("Enter Account type: ");
        acc_type = sc.next();
        System.out.print("Enter Name: ");
        name = sc.next();
        System.out.print("Enter Balance: ");
        balance = sc.nextLong();
    }

    public void showAccount() {
        System.out.println("Name of account holder: " + name);
        System.out.println("Account no.: " + accno);
        System.out.println("Account type: " + acc_type);
        System.out.println("Balance: " + balance);
    }

    public void deposit() {
        long amt;
        System.out.println("Enter the amount you want to deposit: ");
        amt = sc.nextLong();
        balance = balance + amt;
        transactions.add(new Transaction("Deposit", amt));
    }

    public void withdrawal() {
        long amt;
        System.out.println("Enter the amount you want to withdraw: ");
        amt = sc.nextLong();
        if (balance >= amt) {
            balance = balance - amt;
            transactions.add(new Transaction("Withdrawal", amt));
            System.out.println("Balance after withdrawal: " + balance);
        } else {
            System.out.println("Your balance is less than " + amt + "\tTransaction failed...!!");
        }
    }

    public boolean search(String ac_no) {
        if (accno.equals(ac_no)) {
            showAccount();
            return (true);
        }
        return (false);
    }

    public long getBalance() {
        return balance;
    }

    public String getAccNo() {
        return accno;
    }

    public void transferAmount(BankDetails receiver) {
        System.out.println("Enter amount to transfer: ");
        long amount = sc.nextLong();
        if (balance >= amount) {
            balance -= amount;
            receiver.balance += amount;
            transactions.add(new Transaction("Transfer", amount));
            System.out.println("Transfer successful!");
        } else {
            System.out.println("Insufficient funds to transfer.");
        }
    }

    public void showTransactionHistory() {
        System.out.println("Transaction History for Account No.: " + accno);
        System.out.println("Type \t\tAmount");
        for (Transaction transaction : transactions) {
            System.out.println(transaction.getType() + "\t\t" + transaction.getAmount());
        }
    }
}

public class BankingApp {
    public static void main(String arg[]) {
        Scanner sc = new Scanner(System.in);

        System.out.print("How many number of customers do you want to input? ");
        int n = sc.nextInt();
        BankDetails[] C = new BankDetails[n];
        for (int i = 0; i < C.length; i++) {
            C[i] = new BankDetails();
            C[i].openAccount();
        }

        int ch;
        do {
            System.out.println("\n ***Banking System Application***");
            System.out.println("1. Display all account details \n 2. Search by Account number\n 3. Deposit the amount \n 4. Withdraw the amount \n 5. Check Balance \n 6. Transfer amount \n 7. Transaction History \n 8. Exit ");
            System.out.println("Enter your choice: ");
            ch = sc.nextInt();
            switch (ch) {
                case 1:
                    for (int i = 0; i < C.length; i++) {
                        C[i].showAccount();
                    }
                    break;
                case 2:
                    System.out.print("Enter account no. you want to search: ");
                    String ac_no = sc.next();
                    boolean found = false;
                    for (int i = 0; i < C.length; i++) {
                        found = C[i].search(ac_no);
                        if (found) {
                            break;
                        }
                    }
                    if (!found) {
                        System.out.println("Search failed! Account doesn't exist..!!");
                    }
                    break;
                case 3:
                    System.out.print("Enter Account no. : ");
                    ac_no = sc.next();
                    found = false;
                    for (int i = 0; i < C.length; i++) {
                        found = C[i].search(ac_no);
                        if (found) {
                            C[i].deposit();
                            break;
                        }
                    }
                    if (!found) {
                        System.out.println("Search failed! Account doesn't exist..!!");
                    }
                    break;
                case 4:
                    System.out.print("Enter Account No : ");
                    ac_no = sc.next();
                    found = false;
                    for (int i = 0; i < C.length; i++) {
                        found = C[i].search(ac_no);
                        if (found) {
                            C[i].withdrawal();
                            break;
                        }
                    }
                    if (!found) {
                        System.out.println("Search failed! Account doesn't exist..!!");
                    }
                    break;
                case 5:
                    System.out.print("Enter Account No : ");
                    ac_no = sc.next();
                    found = false;
                    for (int i = 0; i < C.length; i++) {
                        if (C[i].getAccNo().equals(ac_no)) {
                            System.out.println("Balance: " + C[i].getBalance());
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        System.out.println("Search failed! Account doesn't exist..!!");
                    }
                    break;
                case 6:
                    System.out.print("Enter sender's Account No : ");
                    String senderAc = sc.next();
                    System.out.print("Enter receiver's Account No : ");
                    String receiverAc = sc.next();
                    BankDetails sender = null;
                    BankDetails receiver = null;
                    boolean senderFound = false;
                    boolean receiverFound = false;
                    for (int i = 0; i < C.length; i++) {
                        if (C[i].getAccNo().equals(senderAc)) {
                            sender = C[i];
                            senderFound = true;
                        }
                        if (C[i].getAccNo().equals(receiverAc)) {
                            receiver = C[i];
                            receiverFound = true;
                        }
                        if (senderFound && receiverFound) {
                            break;
                        }
                    }
                    if (sender == null || receiver == null) {
                        System.out.println("One or both accounts not found.");
                    } else {
                        sender.transferAmount(receiver);
                    }
                    break;
                case 7:
                    System.out.print("Enter Account No : ");
                    ac_no = sc.next();
                    found = false;
                    for (int i = 0; i < C.length; i++) {
                        if (C[i].getAccNo().equals(ac_no)) {
                            C[i].showTransactionHistory();
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        System.out.println("Search failed! Account doesn't exist..!!");
                    }
                    break;
                case 8:
                    System.out.println("See you soon...");
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        } while (ch != 8);
    }
}
