package consulting.saladinobelisario.controller.services;

import consulting.saladinobelisario.model.EmailTreeItem;

import jakarta.mail.Folder;
import jakarta.mail.Store;
import java.util.List;

public class ServiceManager {

    public void submitFetchFoldersJob(Store store, EmailTreeItem<String> emailTreeItem, List<Folder> folderList){
        FetchFoldersService fetchFoldersService = new FetchFoldersService(store, emailTreeItem, folderList);
        fetchFoldersService.start();
    }
}
