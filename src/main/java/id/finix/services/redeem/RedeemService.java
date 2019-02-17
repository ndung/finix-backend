package id.finix.services.redeem;

import id.finix.domain.Gift;
import id.finix.domain.RedeemLog;
import id.finix.repositories.GiftRepository;
import id.finix.repositories.RedeemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RedeemService {

    @Autowired
    RedeemRepository redeemRepository;

    @Autowired
    GiftRepository giftRepository;

    public List<Gift> getGifts(){
        return giftRepository.findAll();
    }

    public Gift getGift(long id){
        return giftRepository.findOne(id);
    }

    public void redeem(String userId, Gift gift){
        RedeemLog redeem = new RedeemLog();
        redeem.setResellerId(userId);
        redeem.setGift(gift);
        redeem.setTimestamp(new Date());
        redeemRepository.save(redeem);
    }

    public List<RedeemLog> getLogs(String userId){
        return redeemRepository.getLogs(userId, new PageRequest(0, 1000));
    }
}
