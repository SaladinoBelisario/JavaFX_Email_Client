package consulting.saladinobelisario.controller.services;

import javafx.concurrent.Service;

import java.util.ArrayList;
import java.util.List;

public class ServiceQueue {

    private List<Service> services;

    public  ServiceQueue(){
        services = new ArrayList<Service>();
    }

    public void submitService(Service service){
        services.add(service);
    }
}
