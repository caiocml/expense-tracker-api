package br.com.caiocesar.expense.tracker.api.responses;

public class SavedReceiptResponse {

    private final String fileLocation;

    public SavedReceiptResponse(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    public String getFileLocation() {
        return fileLocation;
    }

}
