package consulting.saladinobelisario.controller;

public enum EmailSendingResult{
    SUCCESS,
    FAILED_BY_TRANSPORT,
    FAILED_BY_PROVIDER,
    FAILED_BY_OTHER_REASON;
}
