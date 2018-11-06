package id.finix.services.file;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

@Service
public class FileService {

    @Value("${file.location}")
    private String location;

    public String upload(String subfolder, String id, MultipartFile file) {

        try {
            byte[] bytes = file.getBytes();
            String path = location+"/"+subfolder+"/";
            File folder = new File(path);
            if (!folder.exists()){
                folder.mkdir();
            }
            String fileName = path +id+"-"+file.getOriginalFilename();
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(fileName)));
            stream.write(bytes);
            stream.close();
            return subfolder+"/"+id+"-"+file.getOriginalFilename();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;

    }

}
