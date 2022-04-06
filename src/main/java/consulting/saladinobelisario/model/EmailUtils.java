package consulting.saladinobelisario.model;

import java.util.Properties;

public class EmailUtils {


    public static void addPropertiesToAccount(EmailAccount emailAccount) {
        if (emailAccount.getAddress().contains("gmail")) {
            Properties properties = new Properties();
            properties.put("mail.store.protocol", "imaps");
            properties.put("mail.transport.protocol", "smtps");
            properties.put("mail.smtps.host", "smtp.gmail.com");
            properties.put("mail.smtps.auth", "true");
            properties.put("incomingHost", "imap.gmail.com");
            properties.put("outgoingHost", "smtp.gmail.com");

            emailAccount.setProperties(properties);
        }

    }
}
