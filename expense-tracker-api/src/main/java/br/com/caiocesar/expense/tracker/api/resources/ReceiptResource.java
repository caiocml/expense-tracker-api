package br.com.caiocesar.expense.tracker.api.resources;

import br.com.caiocesar.expense.tracker.api.exceptions.BusinessException;
import br.com.caiocesar.expense.tracker.api.responses.SavedReceiptResponse;
import br.com.caiocesar.expense.tracker.api.services.FileSaverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/receipts")
public class ReceiptResource {

    @Autowired
    private FileSaverService fileSaverService;

    @PostMapping("save")
    public ResponseEntity<?> saveReceipt(@RequestParam("file") MultipartFile receipt) throws IOException {

        if(receipt == null)
            throw new BusinessException("Receipt can not be null!");

        String fileLocation = fileSaverService.save(receipt);

        return ResponseEntity
                .ok(new SavedReceiptResponse(fileLocation));
    }


}
