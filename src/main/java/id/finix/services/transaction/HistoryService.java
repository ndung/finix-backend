package id.finix.services.transaction;

import id.finix.domain.AccountLog;
import id.finix.repositories.AccountLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class HistoryService {

    @Autowired
    private AccountLogRepository accountLogRepository;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public List<AccountLog> getAccountLogs(String userId, String date){
        try {
            Date from = sdf.parse(date + " 00:00:00");
            Date to = sdf.parse(date+" 23:59:59");
            return accountLogRepository.getAccountLogs(userId, from, to, new PageRequest(0, 1000));
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
}
