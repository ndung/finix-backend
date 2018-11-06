package id.finix.controllers.api;

import id.finix.Application;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;

@RestController
@RequestMapping(Application.API_PATH)
public class FileController extends BaseController {
    @Value("${file.location}")
    private String location;

    @RequestMapping(value = "/files/{subfolder}/{fileName:.+}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getFile(@PathVariable("subfolder") String subfolder,
            @PathVariable("fileName") String fileName) {
        HttpHeaders headers = new HttpHeaders();
        DataInputStream in;

        try {
            File file = new File(location + "/" + subfolder + "/" + fileName);
            byte[] media = new byte[(int) file.length()];
            BufferedInputStream br = new BufferedInputStream(new FileInputStream(file));
            br.read(media);

            headers.setCacheControl(CacheControl.noCache().getHeaderValue());
            ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(media, headers, HttpStatus.OK);
            return responseEntity;

        } catch (Exception e) {
            e.printStackTrace();
            ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(null, headers, HttpStatus.BAD_REQUEST);
            return responseEntity;
        }
    }
}
