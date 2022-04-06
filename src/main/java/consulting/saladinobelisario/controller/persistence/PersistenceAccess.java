package consulting.saladinobelisario.controller.persistence;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PersistenceAccess {

    private final String VALID_ACCOUNTS_LOCATION = System.getenv("APPDATA") + "\\validAccounts.ser";

    /**
     * Call on program start
     * @return List<ValidAccount>
     */
    @SuppressWarnings("unchecked")
    public List<ValidAccount> loadFromPersistence(){
        List<ValidAccount> resultList = new ArrayList<>();
        try {
            FileInputStream fileIn = new FileInputStream(VALID_ACCOUNTS_LOCATION);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            List<ValidAccount> persistedList = (List<ValidAccount>) in.readObject();
            decodePasswords(persistedList);
            resultList.addAll(persistedList);
        } catch (FileNotFoundException e) {
            System.err.println("No persistence file found");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultList;
    }

    public void saveToPersistence(List<ValidAccount> validAccounts){
        try {
            File file = new File(VALID_ACCOUNTS_LOCATION);
            FileOutputStream fileOut = new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            encodePasswords(validAccounts);
            out.writeObject(validAccounts);
            out.close();
            fileOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void encodePasswords(List<ValidAccount> validAccounts){
        for (ValidAccount validAccount: validAccounts){
            String originalPassword = validAccount.getPassword();
            validAccount.setPassword(WordScrambler.base64Encode(originalPassword));
        }
    }

    private void decodePasswords(List<ValidAccount> validAccounts){
        for (ValidAccount validAccount: validAccounts){
            String originalPassword = validAccount.getPassword();
            validAccount.setPassword(WordScrambler.base64Decode(originalPassword));
        }
    }
}
