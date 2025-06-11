import java.util.*;

interface DocVerifier {
    boolean verify(List<String> providedDocs, List<String> requiredDocs);
}

interface AccOperations {
    void openAcc();
    void showRequiredDocs();
}

abstract class BankAcc implements AccOperations {
    String customerName;
    String branchName;
    String AccType;

    BankAcc(String customerName, String branchName, String AccType) {
        this.customerName = customerName;
        this.branchName = branchName;
        this.AccType = AccType;
    }

    public void showRequiredDocs() {
        System.out.println("\nRequired Documents for " + AccType + " Acc:");
    }
}

class SavingsAcc extends BankAcc {
    static final List<String> requiredDocs = Arrays.asList(
            "Proof of Identity", "Proof of Address", "PAN Card", "Photograph", "Initial Deposit"
    );

    SavingsAcc(String customerName, String branchName) {
        super(customerName, branchName, "Savings");
    }

    @Override
    public void openAcc() {
        System.out.println("\nSavings Account opened for " + customerName + " at " + branchName + " branch.");
    }

    @Override
    public void showRequiredDocs() {
        super.showRequiredDocs();
        for (String doc : requiredDocs) {
            System.out.println("- " + doc);
        }
    }
}

class CurrentAcc extends BankAcc {
    static final List<String> requiredDocs = Arrays.asList(
            "Proof of Identity", "Proof of Address", "PAN Card", "Business Registration Certificate",
            "Partnership Deed/Certificate of Incorporation", "Board Resolution", "GST Certificate", "Photograph", "Initial Deposit"
    );

    CurrentAcc(String customerName, String branchName) {
        super(customerName, branchName, "Current");
    }

    @Override
    public void openAcc() {
        System.out.println("\nCurrent Account opened for " + customerName + " at " + branchName + " branch.");
    }

    @Override
    public void showRequiredDocs() {
        super.showRequiredDocs();
        for (String doc : requiredDocs) {
            System.out.println("- " + doc);
        }
    }
}

public class SravanDwadasi_BankApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.print("\nEnter your name: ");
            String name = sc.nextLine();

            System.out.print("Enter branch name: ");
            String branch = sc.nextLine();

            System.out.print("Enter Account type (Savings/Current): ");
            String accType = sc.nextLine();

            BankAcc Acc = null;

            switch (accType.trim().toLowerCase()) {
                case "savings":
                    Acc = new SavingsAcc(name, branch);
                    break;
                case "current":
                    Acc = new CurrentAcc(name, branch);
                    break;
                default:
                    System.out.println("Invalid Account type. Try again.");
                    continue;
            }

            Acc.showRequiredDocs();

            System.out.println("\nEnter the Documents you are submitting (comma separated):");
            String docsInput = sc.nextLine();
            List<String> providedDocs = Arrays.asList(docsInput.split("\\s*,\\s*"));

            DocVerifier verifier = (provided, required) -> {
                for (String doc : required) {
                    boolean found = false;
                    for (String p : provided) {
                        if (p.equalsIgnoreCase(doc)) {
                            found = true;
                            break;
                        }
                    }
                    if (!found) return false;
                }
                return true;
            };

            List<String> requiredDocs = (Acc instanceof SavingsAcc)
                    ? SavingsAcc.requiredDocs
                    : CurrentAcc.requiredDocs;

            if (verifier.verify(providedDocs, requiredDocs)) {
                Acc.openAcc();
            } else {
                System.out.println("\nAccount cannot be opened. Missing required Documents.");
            }

            System.out.print("\nDo you want to open another Account? (yes/no): ");
            String again = sc.nextLine();
            if (!again.equalsIgnoreCase("yes")) {
                break;
            }
        }
        sc.close();
    }
}