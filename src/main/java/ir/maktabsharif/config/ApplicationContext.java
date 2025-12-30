package ir.maktabsharif.config;



import ir.maktabsharif.repository.CardRepository;
import ir.maktabsharif.repository.TransactionRepository;
import ir.maktabsharif.repository.UserRepository;
import ir.maktabsharif.service.CardService;
import ir.maktabsharif.service.TransactionService;
import ir.maktabsharif.service.UserService;
import ir.maktabsharif.view.BankConsoleApp;

public class ApplicationContext {

    private static ApplicationContext instance;

    // Repositories
    private UserRepository userRepository;
    private CardRepository cardRepository;
    private TransactionRepository transactionRepository;

    // Services
    private UserService userService;
    private CardService cardService;
    private TransactionService transactionService;

    private ApplicationContext() {}

    public static ApplicationContext getInstance() {
        if (instance == null) {
            instance = new ApplicationContext();
        }
        return instance;
    }

    public BankConsoleApp createApp() {
        return new BankConsoleApp(
                getUserService(),
                getCardService(),
                getTransactionService()
        );
    }

    // ---------- Repositories ----------

//    lazy load:

    public UserRepository getUserRepository() {
        if (userRepository == null) {
            userRepository = new UserRepository();
        }
        return userRepository;
    }

    public CardRepository getCardRepository() {
        if (cardRepository == null) {
            cardRepository = new CardRepository();
        }
        return cardRepository;
    }

    public TransactionRepository getTransactionRepository() {
        if (transactionRepository == null) {
            transactionRepository = new TransactionRepository();
        }
        return transactionRepository;
    }

    // ---------- Services ----------

    public UserService getUserService() {
        if(userService == null) {
            userService = new UserService(getUserRepository());
        }
        return userService;
    }

    public CardService getCardService() {
        if (cardService == null) {
            cardService = new CardService(getCardRepository(), getUserRepository());
        }
        return cardService;
    }

    public TransactionService getTransactionService() {
        if (transactionService == null) {
            transactionService = new TransactionService(getTransactionRepository(), getCardRepository());
        }
        return transactionService;
    }
}
