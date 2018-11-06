package id.finix.services.topup;

import id.finix.domain.BankAccount;
import id.finix.domain.Deposit;
import id.finix.repositories.BankAccountRepository;
import id.finix.repositories.DepositRepository;
import id.finix.services.file.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class AddBalanceService {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private DepositRepository depositRepository;

    @Autowired
    private FileService fileService;

    public List<BankAccount> getBankAccounts(){
        return bankAccountRepository.getBankAccounts();
    }

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public Deposit saveDeposit(String userId, TopupRequest request){
        Deposit deposit = new Deposit();
        BankAccount bankAccount = bankAccountRepository.findOne(request.getBankAccountId());
        int amount = request.getAmount()+depositRepository.getUniqueTicketNumber();
        deposit.setCredit(Double.valueOf(amount));
        deposit.setDatetime(new Date());
        deposit.setDebit(0d);
        deposit.setPaymentOption("TRANSFER");
        String description = "[TRANSFER ke "+bankAccount.getBankName()+", "+ bankAccount.getBankId() +" an. "+bankAccount.getAccountName()+"] ";
        if (request.getDescription()!=null){
            description = description + request.getDescription();
        }
        deposit.setDescription(description);
        deposit.setFlag(0);
        deposit.setResellerId(userId);
        deposit.setUserDeposit(userId);
        deposit.setStatus(0);
        deposit.setStan("X"+depositRepository.getStanDeposit());
        return depositRepository.saveAndFlush(deposit);
    }

    public Deposit uploadProof(long id, MultipartFile multipartFile){
        Deposit deposit = depositRepository.findOne(id);
        if (deposit!=null) {
            String path = fileService.upload("proof_deposit", deposit.getStan(), multipartFile);
            System.out.println("path:"+path);
            if (path!=null) {
                deposit.setProof(path);
                return depositRepository.saveAndFlush(deposit);
            }
        }
        return deposit;
    }

    public List<Deposit> getUnapprovedDeposit(String userId) throws Exception {
        Date now = new Date();
        Date startDate = sdf.parse(sdf.format(now));
        return depositRepository.getUnapprovedDeposit(userId, startDate, now, new PageRequest(0,1));
    }

}
