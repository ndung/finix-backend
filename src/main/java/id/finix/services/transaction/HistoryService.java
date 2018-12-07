package id.finix.services.transaction;

import id.finix.domain.AccountLog;
import id.finix.domain.Deposit;
import id.finix.domain.ProfitSettlement;
import id.finix.repositories.AccountLogRepository;
import id.finix.repositories.DepositRepository;
import id.finix.repositories.ProfitSettlementRepository;
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

    @Autowired
    private ProfitSettlementRepository profitSettlementRepository;

    @Autowired
    private DepositRepository depositRepository;

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

    public List<ProfitSettlement> getSettlementLogs(String userId){
        try {
            return profitSettlementRepository.getLogs(userId, new PageRequest(0, 1000));
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    public List<Deposit> getDepositLogs(String userId, int page){
        try {
            return depositRepository.getDeposits(userId, new PageRequest(page, 10));
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
}
