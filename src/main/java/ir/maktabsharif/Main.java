package ir.maktabsharif;

import ir.maktabsharif.config.ApplicationContext;
import ir.maktabsharif.config.JpaUtil;
import ir.maktabsharif.view.BankConsoleApp;

public class Main {
    public static void main(String[] args) {

//        ApplicationContext ctx = ApplicationContext.getInstance();
//
//        BankConsoleApp app = new BankConsoleApp(
//                ctx.getUserService(),
//                ctx.getCardService(),
//                ctx.getTransactionService()
//        );

        // baraye behtar shodan un khate bala ro bordam tu ApplicationContext va be createApp tabdilesh kardam
        try {
            BankConsoleApp app = ApplicationContext.getInstance().createApp();

            app.run();

        } finally {
            JpaUtil.close();
        }
    }
}