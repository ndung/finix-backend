package id.finix.services.user;

import java.util.Date;
import java.util.List;

import id.finix.domain.Parameter;
import id.finix.domain.Reseller;
import id.finix.repositories.ParameterRepository;
import id.finix.repositories.ResellerRepository;
import id.finix.component.PasswordUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ResellerService {

    Logger logger = LoggerFactory.getLogger(ResellerService.class);

    @Autowired
    private ResellerRepository resellerRepository;

    @Autowired
    private ParameterRepository parameterRepository;

    SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static boolean isPasswordValid(String password, boolean isDigit, boolean isCase, int minLength, int maxLength) {

        String PASSWORD_PATTERN = "";

        if (isDigit) PASSWORD_PATTERN += "(?=.*\\d)";
        if (isCase) PASSWORD_PATTERN += "(?=.*[a-z])";
        PASSWORD_PATTERN += ".{" + minLength + "," + maxLength + "}";
        PASSWORD_PATTERN = "(" + PASSWORD_PATTERN + ")";

        Pattern pattern = Pattern.compile(PASSWORD_PATTERN, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public Reseller signIn(SignInRequest request) throws Exception {
        Reseller rs = resellerRepository.findOne(request.getUsername());
        if (rs == null) {
            throw new Exception("User tidak ditemukan");
        }
        if (!PasswordUtil.checkPublicKey(request.getPublicKey(), request.getUsername(), rs.getPassword())) {
            throw new Exception("Password salah");
        }
        return rs;
    }

    public Reseller createNewPassword(CreateNewPasswordRequest request) throws Exception {
        Reseller rs = resellerRepository.findOne(request.getUsername());
        if (rs == null) {
            rs = new Reseller();
            rs.setId(request.getUsername());
        }
        if (isPasswordValid(request.getNewPassword(), true, true, 8, 100)){
            throw new Exception("Password tidak valid. Minimal terdiri dari 1 huruf, 1 angka, dan 8 karakter");
        }
        rs.setPassword(PasswordUtil.md5Hash(request.getNewPassword()));
        return resellerRepository.saveAndFlush(rs);
    }

    public Reseller changePassword(String userId, ChangePasswordRequest request) throws Exception {
        Reseller rs = resellerRepository.findOne(userId, PasswordUtil.md5Hash(request.getOldPassword()));
        if (rs == null) {
            throw new Exception("User tidak ditemukan");
        }
        if (!isPasswordValid(request.getNewPassword(), true, true, 8, 100)) {
            throw new Exception("Password tidak valid. Minimal terdiri dari 1 huruf, 1 angka, dan 8 karakter");
        }
        rs.setPassword(PasswordUtil.md5Hash(request.getNewPassword()));
        return resellerRepository.saveAndFlush(rs);
    }

    public Reseller findOne(String userId) {
        return resellerRepository.findOne(userId);
    }

    public List<Reseller> findDownlines(String userId){
        return resellerRepository.findDownlines(userId);
    }

    public Reseller addNewDownline(String phoneNumber, String upline){

        Reseller rs = new Reseller();
        rs.setId(phoneNumber);
        rs.setBalance(0d);
        rs.setLiabilities(0d);
        rs.setMobileNumber(phoneNumber);
        rs.setTrxFee(0d);
        rs.setJoinedDate(new Date());
        rs.setStatus(-1);
        rs.setUpline(upline);
        String password = PasswordUtil.create();
        rs.setPassword(PasswordUtil.md5Hash(password));
        rs.setUseFingerprint(Boolean.FALSE.toString());
        rs.setUser(phoneNumber);
        Parameter sd = parameterRepository.findOne("DEFAULT_MEMBER_GROUP");
        rs.setSubdealerId(sd.getValue());
        rs.setVerified("N");
        Parameter parameter = parameterRepository.findOne("SMS_NEW_REGISTER");

        return resellerRepository.saveAndFlush(rs);
    }

    public Reseller updateReseller(Reseller rs){
        return resellerRepository.saveAndFlush(rs);
    }

    public static void main(String[] args) {
        String a = "123456";
        System.out.println(isPasswordValid(a, true, true, 6, 20));
    }
}
