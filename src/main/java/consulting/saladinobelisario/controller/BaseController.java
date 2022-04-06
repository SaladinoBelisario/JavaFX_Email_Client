package consulting.saladinobelisario.controller;

import consulting.saladinobelisario.EmailManager;
import consulting.saladinobelisario.view.ViewFactory;

import java.util.Objects;

public abstract class BaseController {

    protected ViewFactory viewFactory;
    protected EmailManager emailManager;
    private String fxmlName;


    public BaseController(ViewFactory viewFactory, EmailManager emailManager, String fxmlName) {
        this.viewFactory = viewFactory;
        this.emailManager = emailManager;
        this.fxmlName = fxmlName;
    }

    public String getFxmlName(){
        return  this.fxmlName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseController that = (BaseController) o;
        return Objects.equals(fxmlName, that.fxmlName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fxmlName);
    }
}
