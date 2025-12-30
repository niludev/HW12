package ir.maktabsharif.view;



import ir.maktabsharif.models.User;
import ir.maktabsharif.models.Card;
import ir.maktabsharif.service.CardService;
import ir.maktabsharif.service.TransactionService;
import ir.maktabsharif.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BankConsoleApp {

    private final UserService userService;
    private final CardService cardService;
    private final TransactionService transactionService;

    private final Scanner scanner = new Scanner(System.in);

    private User loggedInUser;


    public BankConsoleApp(UserService userService, CardService cardService, TransactionService transactionService) {
        this.userService = userService;
        this.cardService = cardService;
        this.transactionService = transactionService;
    }

    public void run() {
        System.out.println("===== Electronic banking =====");
        mainMenu();
    }

    private void mainMenu() {

        while (true) {
            System.out.println("--- Main Menu ---");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");

            System.out.print("Choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    registerMenu();
                    break;
                case "2":
                    loginMenu();
                    break;
                case "3":
                    System.out.println("Exit from app ...");
                    return;
                default:
                    System.out.println("Wrong option!");
            }
//            if(choice.equals("1")) {
//                registerMenu();
//                return;
//            } else if (choice.equals("2")) {
//                loginMenu();
//                return;
//            } else if (choice.equals("3")) {
//                System.out.println("Exit from app ...");
//                return;
//            } else {
//                System.out.println("Wrong option!");
//                return;
//            }
        }
    }

    private void registerMenu() {
        System.out.println("--- Register ---");

        System.out.println("username: ");
        String username = scanner.nextLine();

        System.out.println("password: ");
        String password = scanner.nextLine();

        User user = userService.register(username, password);

        if (user == null) {
            System.out.println("Registration failed (probably a duplicate username).");
        } else {
            System.out.println("Registration was successful. Welcome " + user.getUsername() + "!");
        }

    }

    private void loginMenu() {
        System.out.println("--- Login ---");

        System.out.println("username: ");
        String username = scanner.nextLine();

        System.out.println("password: ");
        String password = scanner.nextLine();

        User user = userService.login(username, password);

        if (user == null) {
            System.out.println("Login failed. (username not found or wrong password)");
            return;
        }

        System.out.println("Login was successful. Welcome " + user.getUsername() + "!");

        loggedInUser = user;

        loggedInMenu();
    }

    private void loggedInMenu() {
        while (true) {
            System.out.println("--- User Menu ---");
            System.out.println("1. Card Operations");
            System.out.println("2. Transaction Operations");
            System.out.println("3. Logout");

            System.out.print("Choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    cardOperationsMenu();
                    break;
                case "2":
                    transactionOperationsMenu();
                    break;
                case "3":
                    handleLogout();
                    return;
                default:
                    System.out.println("Wrong option!");
            }
        }
    }

    private void handleLogout() {
        userService.logOut();
        System.out.println("User " + loggedInUser.getUsername() + " logged out.");
        loggedInUser = null;
    }

    private void cardOperationsMenu() {
        while (true) {
            System.out.println("--- Card Operations Menu ---");
            System.out.println("1. Register Card");
            System.out.println("2. Delete Card");
            System.out.println("3. Show Card by Number");
            System.out.println("4. Show Cards by Bank Name");
            System.out.println("5. Show All Cards");
            System.out.println("6. Back");

            System.out.print("Choice: ");
            String choice = scanner.nextLine();

            switch (choice) {

                case "1":
                    registerCard();
                    break;
                case "2":
                    deleteCard();
                    break;
                case "3":
                    showCardByNumber();
                    break;
                case "4":
                    showCardsByBankName();
                    break;
                case "5":
                    showAllCards();
                    break;
                case "6":
//                    loggedInMenu();
                    return;
                default:
                    System.out.println("Wrong option!");
            }
        }
    }


    public void registerCard() {
        if (loggedInUser == null) {
            System.out.println("You must log in first.");
            return;
        }

        System.out.println("--- Register Card ---");

        System.out.print("Card Number: ");
        String cardNumber = scanner.nextLine().trim();

        System.out.print("Bank Name: ");
        String bankName = scanner.nextLine().trim();

        System.out.print("Initial Balance (Number): ");
        String balanceInput = scanner.nextLine().trim();

        Long balance = null;
        try {
            balance = Long.parseLong(balanceInput);
        } catch (NumberFormatException e) {
            System.out.println("The inventory amount is invalid.");
            return;
        }

        Card card = cardService.registerCard(cardNumber, bankName, balance, loggedInUser.getId());

        System.out.println("Card registered:");
        System.out.println("ID: " + card.getId() + " | Number: " + card.getCardNumber() + " | Bank: " + card.getBankName() + " | Balance: " + card.getBalance());
    }

    private void deleteCard() {
        System.out.println("--- Removing card ---");

        System.out.print("Card number: ");
        String cardNumber = scanner.nextLine().trim();

        Card deletedCard = cardService.deleteCardByCardNumber(cardNumber);

        if (deletedCard == null) {
            System.out.println("There is no card with the card number " + cardNumber);
        } else {
            System.out.println("The card with number " + cardNumber + " has been deleted.");
        }
    }

    private void showCardByNumber() {
        System.out.println("--- Display card by number ---");

        System.out.print("Card number: ");
        String cardNumber = scanner.nextLine().trim();

        Card card = cardService.getCardByCardNumber(cardNumber);

        if (card == null) {

            System.out.println("No card with this number found.");
            return;
        }

        System.out.println("Card found:");
        System.out.println(card);
    }

    private void showCardsByBankName() {
        System.out.println("--- Display cards by bank name ---");

        System.out.print("Bank Name: ");
        String bankName = scanner.nextLine().trim();

        List<Card> cards = cardService.getCardsByBankName(bankName);

        if (cards == null || cards.isEmpty()) {
            System.out.println("No cards found for this bank.");
            return;
        }

        System.out.println("Bank cards " + bankName + ":");

        for (Card card : cards) {
            System.out.println("ID: " + card.getId() + " | Number: " + card.getCardNumber() + " | Balance: " + card.getBalance() + " | userId: " + card.getUserId());
        }
    }

    private void showAllCards() {
        System.out.println("--- Show all cards ---");

        List<Card> cards = cardService.getAllCards();

        if (cards == null || cards.isEmpty()) {

            System.out.println("No cards are registered in the system.");
            return;
        }

        for (Card card : cards) {
            System.out.println(card);
        }
    }


    private void transactionOperationsMenu() {
        while (true) {
            System.out.println("--- Financial Operations Menu ---");
            System.out.println("1. Card to Card Transfer");
            System.out.println("2. PAYA Single Transfer");
            System.out.println("3. PAYA Batch Transfer");
            System.out.println("4. SATNA Transfer");
            System.out.println("5. Back");

            System.out.print("Choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    cardToCardTransfer();
                    break;
                case "2":
                    payaSingleTransfer();
                    break;
                case "3":
                    payaBatchTransfer();
                    break;
                case "4":
                    satnaTransfer();
                    break;
                case "5":
//                    loggedInMenu();
                    return;
                default:
                    System.out.println("Wrong choice!");
            }
        }
    }

    private void cardToCardTransfer() {
        if (loggedInUser == null) {
            System.out.println("You must log in first.");
            return;
        }

        System.out.println("--- Card to Card Transfer ---");

        System.out.print("Source Card Number: ");
        String fromCardNumber = scanner.nextLine().trim();

        System.out.print("Destination Card Number: ");
        String toCardNumber = scanner.nextLine().trim();

        System.out.print("Transfer Amount (Number): ");
        String amountInput = scanner.nextLine().trim();


        Long amount = null;
        try {
            amount = Long.parseLong(amountInput);
        } catch (NumberFormatException e) {
            System.out.println("The amount is invalid.");
            return;
        }

        transactionService.transferCardToCard(fromCardNumber, toCardNumber, amount);
    }

    private void payaSingleTransfer() {
        if (loggedInUser == null) {
            System.out.println("You must log in first.");
            return;
        }

        System.out.println("--- Paya Single Transfer ---");

        System.out.print("Source Card Number: ");
        String fromCardNumber = scanner.nextLine().trim();

        System.out.print("Destination Card Number: ");
        String toCardNumber = scanner.nextLine().trim();

        System.out.print("Transfer Amount (Number): ");
        String amountInput = scanner.nextLine().trim();


        Long amount = null;
        try {
            amount = Long.parseLong(amountInput);
        } catch (NumberFormatException e) {
            System.out.println("The amount is invalid.");
            return;
        }

        transactionService.transferPayaSingle(fromCardNumber, toCardNumber, amount);
    }

    private void payaBatchTransfer() {

        if (loggedInUser == null) {
            System.out.println("You must log in first.");
            return;
        }

        System.out.println("--- Paya Batch Transfer ---");

        System.out.print("Source card number: ");
        String fromCardNumber  = scanner.nextLine().trim();

        System.out.println("How many Batch items do you want to have?");
        String countInput = scanner.nextLine().trim();
        int numberOfItems;
        try {
            numberOfItems = Integer.parseInt(countInput);
        } catch (NumberFormatException e) {
            System.out.println("Invalid number.");
            return;
        }

        List<PayaBatchItem> items = new ArrayList<>();

        for (int i = 0; i < numberOfItems; i++) {
            System.out.print("Destination card number: ");
            String toCardNumber = scanner.nextLine().trim();

            System.out.print("Amount of this item (number): ");
            String amountInput = scanner.nextLine().trim();

            Long amount = null;
            try {
                amount = Long.parseLong(amountInput);
            } catch (NumberFormatException e) {
                System.out.println("Amount is invalid. (Skipping this item.)");
                continue;
            }

            PayaBatchItem item = new PayaBatchItem(toCardNumber, amount);
            items.add(item);
        }

        if (items.isEmpty()) {
            System.out.println("No items registered for PAYA Batch.");
            return;
        }

        transactionService.transferPayaBatch(fromCardNumber, items);
    }

    private void satnaTransfer() {

        if (loggedInUser == null) {
            System.out.println("You must log in first.");
            return;
        }

        System.out.println("--- Satna Transfer ---");
        System.out.println("Minimum amount: 50,000,000 Maximum amount: 200,000,000");

        System.out.print("Source Card Number: ");
        String fromCardNumber = scanner.nextLine().trim();

        System.out.print("Destination Card Number: ");
        String toCardNumber = scanner.nextLine().trim();

        System.out.print("Transfer Amount (Number): ");
        String amountInput = scanner.nextLine().trim();


        Long amount = null;
        try {
            amount = Long.parseLong(amountInput);
        } catch (NumberFormatException e) {
            System.out.println("The amount is invalid.");
            return;
        }
        transactionService.transferSatna(fromCardNumber, toCardNumber, amount);
    }


}
