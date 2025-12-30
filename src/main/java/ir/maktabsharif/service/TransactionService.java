package ir.maktabsharif.service;



import ir.maktabsharif.models.Card;
import ir.maktabsharif.models.PayaBatchItem;
import ir.maktabsharif.models.Transaction;
import ir.maktabsharif.models.enums.TransactionStatus;
import ir.maktabsharif.models.enums.TransactionsType;
import ir.maktabsharif.repository.CardRepository;
import ir.maktabsharif.repository.TransactionRepository;

import java.util.List;

public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final CardRepository cardRepository;

//    private static Long batchCounter = 1L;



    public TransactionService(TransactionRepository transactionRepository, CardRepository cardRepository) {
        this.transactionRepository = transactionRepository;
        this.cardRepository = cardRepository;
    }

//    private Long generateBatchId() {
//        return batchCounter++;
//    }


    private void recordTransaction(Card fromCard, Card toCard, Long amount, Long fee, String type, String status, Long batchId, String description) {

        Transaction transaction = new Transaction();
        transaction.setFromCard(fromCard);
        transaction.setToCard(toCard);
        transaction.setAmount(amount);
        transaction.setFee(fee);
        transaction.setType(type);
        transaction.setStatus(status);
        transaction.setBatchId(batchId);
        transaction.setDescription(description);

        transactionRepository.save(transaction);
    }


    private void manageFromAndToCardBalance (Card fromCard, Card toCard, Long totalDebit, Long amount) {
        Long fromCardNewBalance = fromCard.getBalance() - totalDebit;
        Long toCardNewBalance = toCard.getBalance() + amount;

        cardRepository.updateBalance(fromCard.getId(), fromCardNewBalance);
        cardRepository.updateBalance(toCard.getId(), toCardNewBalance);
    }


    private Card findCardOrPrintError(String cardNumber, String roleMessage) {
        Card card = cardRepository.findByCardNumber(cardNumber);

        if (card == null) {
            System.out.println(roleMessage);
        }

        return card;
    }



//    private void checkValidCardNumber (Card fromCard, Card toCard) {
//        if (fromCard == null) {
//            System.out.println("Sender card not found / not correct!");
//            return;
//        }
//
//        if (toCard == null) {
//            System.out.println("Receiver card not found / not correct!");
//            return;
//        }
//    }


//******************************************//
//             transferCardToCard           //
//******************************************//

    public void transferCardToCard(String fromCardNumber, String toCardNumber, Long amount) {

//        Card fromCard = cardRepository.findByCardNumber(fromCardNumber);
//        Card toCard = cardRepository.findByCardNumber(toCardNumber);
//
//        if (fromCard == null) {
//            System.out.println("Sender card not found/ not correct!");
//            return;
//        }
//
//        if (toCard == null) {
//            System.out.println("Receiver card not found/ not correct!");
//            return;
//        }

        Card fromCard = findCardOrPrintError(fromCardNumber, "Sender card not found / not correct!");
        if (fromCard == null) {
            return;
        }

        Card toCard = findCardOrPrintError(toCardNumber, "Receiver card not found / not correct!");
        if (toCard == null) {
            return;
        }



        String fromCardBank = fromCard.getBankName();
        String toCardBank = toCard.getBankName();
        boolean isSameBank = fromCardBank.equals(toCardBank);

//      age bishtar az 15 M bud:
        if (amount > 15_000_000L) {
            recordTransaction(fromCard, toCard, amount, 0L, String.valueOf(TransactionsType.CARD_TO_CARD), String.valueOf(TransactionStatus.FAILED), null, "Amount greater than 15M");

            System.out.println("Amount should not be greater than 15M");
            return;
        }


//      mohasebe karmozd va kole mablaghi ke bayad az ferestande kam beshe:
        Long fee = calculateCardToCardFee(amount, isSameBank);
        Long totalDebit = amount + fee;


//      age mojudi kafi nabud:
        if (fromCard.getBalance() < totalDebit) {
            recordTransaction(fromCard, toCard, amount, 0L, String.valueOf(TransactionsType.CARD_TO_CARD), String.valueOf(TransactionStatus.FAILED), null, "Insufficient balance");

            System.out.println("Insufficient balance");
            return;
        }


//      age hamechi ok bud:
//      kamo ziad kardane mojudi baraye ferestande va girande

//        Long fromCardNewBalance = fromCard.getBalance() - totalDebit;
//        Long toCardNewBalance = toCard.getBalance() + amount;
//
//        cardRepository.updateBalance(fromCard.getId(), fromCardNewBalance);
//        cardRepository.updateBalance(toCard.getId(), toCardNewBalance);

        manageFromAndToCardBalance(fromCard, toCard, totalDebit, amount);

        recordTransaction(fromCard, toCard, amount, fee, String.valueOf(TransactionsType.CARD_TO_CARD), String.valueOf(TransactionStatus.SUCCESS), null, "Transaction successful");
        System.out.println("Transaction successful");

//        Transaction transaction = new Transaction();
//
//        transaction.setFromCardId(fromCard.getId());
//        transaction.setToCardId(toCard.getId());
//        transaction.setAmount(amount);
//        transaction.setFee(fee);
//        transaction.setType(String.valueOf(TransactionsType.CARD_TO_CARD));
//
//        if (amount > 15000000L) {
//            System.out.println("amount should not bigger than 15 M");
//            transaction.setStatus(String.valueOf(TransactionStatus.FAILED));
//        }
//
//        if (fromCard.getBalance() < totalDebit) {
//            System.out.println("Insufficient balance");
//            transaction.setStatus(String.valueOf(TransactionStatus.FAILED));
//        }
//
//        transaction.setStatus(String.valueOf(TransactionStatus.SUCCESS));
//        transaction.setBatchId(null);
//
//        transactionRepository.save(transaction);

    }


    private Long calculateCardToCardFee (Long amount, boolean isSameBank) { // Autoboxing:  long → Long

        if (isSameBank) {
            return 0L;
        } else if(amount <= 10000000L) {
            return 720L;
        } else {
            long diff = amount - 10000000L; // cheghadr bishtar az 10 million darim?
            long extraMillions = (long)Math.ceil(diff/1000000.0); // chand million ezafe darim? round be bala.

            return 1000L + (extraMillions * 100L);
        }
    }


//******************************************//
//             transferPayaSingle           //
//******************************************//

    public void transferPayaSingle (String fromCardNumber, String toCardNumber, Long amount) {
//
//        Card fromCard = cardRepository.findByCardNumber(fromCardNumber);
//        Card toCard = cardRepository.findByCardNumber(toCardNumber);
//
//
//        if (fromCard == null) {
//            System.out.println("Sender card not found / not correct!");
//            return;
//        }
//
//        if (toCard == null) {
//            System.out.println("Receiver card not found / not correct!");
//            return;
//        }

        Card fromCard = findCardOrPrintError(fromCardNumber, "Sender card not found / not correct!");
        if (fromCard == null) {
            return;
        }

        Card toCard = findCardOrPrintError(toCardNumber, "Receiver card not found / not correct!");
        if (toCard == null) {
            return;
        }


        if (amount > 50000000L) {
            recordTransaction( fromCard, toCard, amount, 0L, String.valueOf(TransactionsType.PAYA_SINGLE), String.valueOf(TransactionStatus.FAILED), null, "Amount greater than 50M" );
            System.out.println("Amount should not be greater than 50M for PAYA single");
            return;
        }


        //      mohasebe karmozd va kole mablaghi ke bayad az ferestande kam beshe:
        Long fee = amount / 1000;
        Long totalDebit = amount + fee;


        //      age mojudi kafi nabud:
        if (fromCard.getBalance() < totalDebit) {
            recordTransaction(fromCard, toCard, amount, 0L, String.valueOf(TransactionsType.PAYA_SINGLE), String.valueOf(TransactionStatus.FAILED), null, "Insufficient balance");

            System.out.println("Insufficient balance");
            return;
        }

        //      age hamechi ok bud:
//      kamo ziad kardane mojudi baraye ferestande va girande
        manageFromAndToCardBalance(fromCard, toCard, totalDebit, amount);

        recordTransaction( fromCard, toCard, amount, fee, String.valueOf(TransactionsType.PAYA_SINGLE), String.valueOf(TransactionStatus.SUCCESS), null, "PAYA single transfer successful" );

        System.out.println("PAYA single transfer completed successfully");
    }



//******************************************//
//             transferPayaBatch            //
//******************************************//

//    چند تراکنش رو در یک دسته (Batch) گروه‌بندی می کنیم
//    از یه حساب به چندین جساب مختلف ----> چند تراکنش
//    گروهی از تراکنش ها
//    batch_id یکسان در دیتابیس
    public void transferPayaBatch(String fromCardNumber, List<PayaBatchItem> items) {
//        Card fromCard = cardRepository.findByCardNumber(fromCardNumber);
//
//        if (fromCard == null) {
//            System.out.println("Sender card not found / not correct!");
//            return;
//        }

        Card fromCard = findCardOrPrintError(fromCardNumber, "Sender card not found / not correct!");
        if (fromCard == null) {
            return;
        }

        if (items == null || items.isEmpty()) {
            System.out.println("No PAYA batch items provided!");
            return;
        }

        Long fee = calculatePayaBatchFee(items.size());
        Long totalAmount = 0L;

        for (PayaBatchItem item : items) {
            Card toCard = cardRepository.findByCardNumber(item.getToCardNumber());

            if (toCard == null) {
                System.out.println("Receiver card not found for item with toCardNumber = " + item.getToCardNumber());
                continue;
            }

            totalAmount += item.getAmount();
        }

//        age hame girande ha null budan:
//        chon dar edame totalAmount = fee mishe vali hich transactioni etefagh nemiofte vali fee az ferestande kam mishe pas:
        if (totalAmount == 0L) {
            System.out.println("No valid receiver cards found for PAYA batch");
            return;
        }

        Long totalDebit = totalAmount + fee;

//            Long batchId = generateBatchId();

//            uniqe id:
        Long batchId = System.currentTimeMillis();


        if (fromCard.getBalance() < totalDebit) {

            for (PayaBatchItem item : items) {
                Card toCard = cardRepository.findByCardNumber(item.getToCardNumber());

                if (toCard == null) {
                    System.out.println("Receiver card not found for item with toCardNumber = " + item.getToCardNumber());
                    continue;
                }

                recordTransaction(fromCard, toCard, item.getAmount(), 0L, String.valueOf(TransactionsType.PAYA_BATCH), String.valueOf(TransactionStatus.FAILED), batchId, "Insufficient balance for PAYA batch");
            }
            System.out.println("Insufficient balance for PAYA batch");
            return;
        }

//        age hamechi khub bud:
        for (PayaBatchItem item : items) {
            Card toCard = cardRepository.findByCardNumber(item.getToCardNumber());

            if (toCard == null) {
                System.out.println("Receiver card not found for item with toCardNumber = " + item.getToCardNumber());
                continue;
            }

            Long toCardNewBalance = toCard.getBalance() + item.getAmount();
            cardRepository.updateBalance(toCard.getId(), toCardNewBalance);

            recordTransaction(fromCard, toCard, item.getAmount(), fee, String.valueOf(TransactionsType.PAYA_BATCH), String.valueOf(TransactionStatus.SUCCESS), batchId, "PAYA batch transfer successful");
        }

        Long fromCardNewBalance = fromCard.getBalance() - totalDebit;
        cardRepository.updateBalance(fromCard.getId(), fromCardNewBalance);

        System.out.println("PAYA batch transfer successful");
    }

    private Long calculatePayaBatchFee (int count) {
        if (count <= 10) {
            return 12000L;
        } else {
            int extra = count - 10;
            return 12000L + (extra * 1200L);
        }
    }


//******************************************//
//             transferSatna                //
//******************************************//

    public void transferSatna (String fromCardNumber, String toCardNumber, Long amount) {
//        Card fromCard = cardRepository.findByCardNumber(fromCardNumber);
//        Card toCard = cardRepository.findByCardNumber(toCardNumber);
//
//        if (fromCard == null) {
//            System.out.println("Sender card not found/ not correct!");
//            return;
//        }
//
//        if (toCard == null) {
//            System.out.println("Receiver card not found/ not correct!");
//            return;
//        }

        Card fromCard = findCardOrPrintError(fromCardNumber, "Sender card not found / not correct!");
        if (fromCard == null) {
            return;
        }

        Card toCard = findCardOrPrintError(toCardNumber, "Receiver card not found / not correct!");
        if (toCard == null) {
            return;
        }

        if (amount <= 50000000L) {

            recordTransaction(fromCard, toCard, amount, 0L, String.valueOf(TransactionsType.SATNA), String.valueOf(TransactionStatus.FAILED), null, "Amount less than or equal to 50M");

            System.out.println("Amount must be greater than 50M");
            return;

        } else if (amount > 200000000L) {

            recordTransaction(fromCard, toCard, amount, 0L, String.valueOf(TransactionsType.SATNA), String.valueOf(TransactionStatus.FAILED), null, "Amount greater than 200M");

            System.out.println("Amount should not be greater than 200M");
            return;
        }


        Long fee = (amount * 2) / 1000;
        Long totalDebit = amount + fee;

        if (fromCard.getBalance() < totalDebit) {
            recordTransaction(fromCard, toCard, amount, 0L, String.valueOf(TransactionsType.SATNA), String.valueOf(TransactionStatus.FAILED), null, "Insufficient balance");

            System.out.println("Insufficient balance");
            return;
        }


//        age hamechi ok bud:
        manageFromAndToCardBalance(fromCard, toCard, totalDebit, amount);

        recordTransaction(fromCard, toCard, amount, fee, String.valueOf(TransactionsType.SATNA), String.valueOf(TransactionStatus.SUCCESS), null, "Transaction successful");

    }

}
