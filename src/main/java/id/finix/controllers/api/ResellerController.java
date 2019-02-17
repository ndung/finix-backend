package id.finix.controllers.api;

import id.finix.Application;
import id.finix.component.PasswordUtil;
import id.finix.controllers.Response;
import id.finix.domain.ChatMessage;
import id.finix.domain.QrCode;
import id.finix.domain.Reseller;
import id.finix.services.chat.ChatMessageRequest;
import id.finix.services.user.*;

import java.util.*;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(Application.API_PATH + "/user")
public class ResellerController extends BaseController {

    Map<String, QrCode> qrCodeMap = new HashMap<>();
    @Autowired
    private ResellerService resellerService;
    Logger logger = Logger.getLogger(ResellerController.class);

    @RequestMapping(value = "/sign-in", method = RequestMethod.POST)
    public ResponseEntity<Response> signIn(@RequestBody SignInRequest requestSignIn) {
        try {
            Reseller rs = resellerService.signIn(requestSignIn);
            String token = createToken(rs);
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("Token", token);
            if (rs.getStatus()==1) {
                return getHttpStatus(new Response(rs), responseHeaders);
            }else if (rs.getStatus()==5) {
                return getHttpStatus(new Response("User diblokir, silakan ganti password untuk mengaktifkan akun kembali"), responseHeaders);
            }else{
                return getHttpStatus(new Response("User diblokir, silakan ganti password untuk mengaktifkan akun kembali"), responseHeaders);
            }
        } catch (Exception e) {
            return getHttpStatus(new Response(e.getMessage()));
        }
    }

    @RequestMapping(value = "/create-newpwd", method = RequestMethod.POST)
    public ResponseEntity<Response> createNewPassword(@RequestBody CreateNewPasswordRequest createNewPasswordRequest) {
        try {
            Reseller rs = resellerService.createNewPassword(createNewPasswordRequest);
            String token = createToken(rs);
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("Token", token);
            return getHttpStatus(new Response(rs), responseHeaders);
        } catch (Exception e) {
            return getHttpStatus(new Response(e.getMessage()));
        }
    }

    @RequestMapping(value = "/change-pwd", method = RequestMethod.POST)
    public ResponseEntity<Response> changePassword(@RequestHeader(Application.AUTH) String token,
                                                   @RequestBody ChangePasswordRequest request) {
        try {
            String userId = getUserId(token);
            if (userId==null){
                return FORBIDDEN;
            }
            Reseller rs = resellerService.changePassword(userId, request);
            return getHttpStatus(new Response(rs));

        } catch (Exception e) {
            return getHttpStatus(new Response(e.getMessage()));
        }
    }

    @RequestMapping(value = "/get-info", method = RequestMethod.GET)
    public ResponseEntity<Response> getInfo(@RequestHeader(Application.AUTH) String token) {
        try {
            String userId = getUserId(token);
            if (userId==null){
                return FORBIDDEN;
            }
            Reseller rs = resellerService.findOne(userId);
            return getHttpStatus(new Response(rs));

        } catch (Exception e) {
            return getHttpStatus(new Response(e.getMessage()));
        }
    }

    @RequestMapping(value = "/get-user/{id}", method = RequestMethod.POST)
    public ResponseEntity<Response> getReseller(@RequestHeader(Application.AUTH) String token,
                                                @PathVariable("id") String id) {
        try {
            String userId = getUserId(token);
            if (userId==null){
                return FORBIDDEN;
            }
            Reseller rs = resellerService.findOne(id);
            if (rs!=null) {
                return getHttpStatus(new Response(rs));
            }else{
                return getHttpStatus(new Response("User tujuan penerima tidak ditemukan"));
            }
        } catch (Exception e) {
            return getHttpStatus(new Response(e.getMessage()));
        }
    }

    @RequestMapping(value = "/get-downlines", method = RequestMethod.GET)
    public ResponseEntity<Response> getDownlines(@RequestHeader(Application.AUTH) String token) {
        try {
            String userId = getUserId(token);
            if (userId==null){
                return FORBIDDEN;
            }
            List<Reseller> list = resellerService.findDownlines(userId);
            if (list!=null) {
                return getHttpStatus(new Response(list));
            }else{
                return getHttpStatus(new Response("Tidak ada downline ditemukan"));
            }
        } catch (Exception e) {
            return getHttpStatus(new Response(e.getMessage()));
        }
    }

    @RequestMapping(value = "/add-downline/{downline}", method = RequestMethod.POST)
    public ResponseEntity<Response> addNewDownline(@RequestHeader(Application.AUTH) String token,
                                                   @PathVariable("downline") String downline) {
        try {
            String userId = getUserId(token);
            if (userId==null){
                return FORBIDDEN;
            }
            Reseller rs = resellerService.findOne(downline);

            if (rs==null) {
                rs = resellerService.addNewDownline(downline, userId);
                List<Reseller> list = resellerService.findDownlines(userId);
                return getHttpStatus(new Response(list));
            }else{
                return getHttpStatus(new Response("Nomor handphone sudah terdaftar"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return getHttpStatus(new Response(e.getMessage()));
        }
    }

    @RequestMapping(value = "/remove-downline/{downline}", method = RequestMethod.POST)
    public ResponseEntity<Response> removeDownline(@RequestHeader(Application.AUTH) String token,
                                                   @PathVariable("downline") String downline) {
        try {
            String userId = getUserId(token);
            if (userId==null){
                return FORBIDDEN;
            }
            Reseller rs = resellerService.findOne(downline);

            if (rs!=null) {
                rs.setUpline(null);
                rs = resellerService.updateReseller(rs);
                List<Reseller> list = resellerService.findDownlines(userId);
                return getHttpStatus(new Response(list));
            }else{
                return getHttpStatus(new Response("Nomor handphone tidak terdaftar"));
            }
        } catch (Exception e) {
            return getHttpStatus(new Response(e.getMessage()));
        }
    }

    @RequestMapping(value = "/use-fingerprint/{bool}", method = RequestMethod.POST)
    public ResponseEntity<Response> useFingerprint(@RequestHeader(Application.AUTH) String token,
                                                   @PathVariable("bool") String bool) {
        try {
            String userId = getUserId(token);
            if (userId==null){
                return FORBIDDEN;
            }
            Reseller rs = resellerService.findOne(userId);

            if (rs!=null) {
                rs.setUseFingerprint(bool);
                rs = resellerService.updateReseller(rs);
                return getHttpStatus(new Response(rs));
            }else{
                return getHttpStatus(new Response("Nomor handphone tidak terdaftar"));
            }
        } catch (Exception e) {
            return getHttpStatus(new Response(e.getMessage()));
        }
    }

    @RequestMapping(value = "/update-facebook", method = RequestMethod.POST)
    public ResponseEntity<Response> updateFacebookInfo(@RequestHeader(Application.AUTH) String token,
                                                       @RequestBody String facebookInfo) {
        try {
            String userId = getUserId(token);
            if (userId==null){
                return FORBIDDEN;
            }
            Reseller rs = resellerService.findOne(userId);

            if (rs!=null) {
                rs.setFacebookInfo(facebookInfo);
                rs = resellerService.updateReseller(rs);
                return getHttpStatus(new Response(rs));
            }else{
                return getHttpStatus(new Response("Nomor handphone tidak terdaftar"));
            }
        } catch (Exception e) {
            return getHttpStatus(new Response(e.getMessage()));
        }
    }

    @RequestMapping(value = "/gen-qr", method = RequestMethod.POST)
    public ResponseEntity<Response> generateQrCode(@RequestHeader(Application.AUTH) String token) {
        try {
            String userId = getUserId(token);
            if (userId==null){
                return FORBIDDEN;
            }
            String code = PasswordUtil.create();
            QrCode qrCode = new QrCode();
            qrCode.setId(userId);
            qrCode.setCode(code);
            qrCode.setTime(new Date());
            while (qrCodeMap.containsKey(code)) {
                code = PasswordUtil.create();
            }
            qrCodeMap.put(code, qrCode);

            return getHttpStatus(new Response(qrCode));
        } catch (Exception e) {
            return getHttpStatus(new Response(e.getMessage()));
        }
    }

    @RequestMapping(value = "/get-qr", method = RequestMethod.POST)
    public ResponseEntity<Response> getQrCode(@RequestHeader(Application.AUTH) String token,
                                              @RequestBody String qrCode) {
        try {
            String userId = getUserId(token);
            if (userId==null){
                return FORBIDDEN;
            }

            QrCode res = new QrCode();
            QrCode code = qrCodeMap.get(qrCode);
            if (code != null) {
                res.setId(code.getId());
                return getHttpStatus(new Response(qrCode));
            }else{
                return getHttpStatus(new Response("Mohon refresh/ generate ulang QR code, lalu scan kembali"));
            }

        } catch (Exception e) {
            return getHttpStatus(new Response(e.getMessage()));
        }
    }

    @RequestMapping(value = "/update-account", method = RequestMethod.POST)
    public ResponseEntity<Response> updateAccount(@RequestHeader(Application.AUTH) String token,
                                                  @RequestParam("name") String name,
                                                  @RequestParam("address") String address,
                                                  @RequestParam("province") String province,
                                                  @RequestParam("city") String city,
                                                  @RequestParam("district") String district,
                                                  @RequestParam("subdistrict") String subdistrict,
                                                  @RequestParam("zipcode") String zipcode,
                                                  @RequestParam("gender") String gender,
                                                  @RequestParam("identityNo") String identityNo,
                                                  @RequestParam("email") String email,
                                                  @RequestParam("mobileNumber") String mobileNumber,
                                                  @RequestParam("birthDate") String birthDate,
                                                  @RequestParam("referralCode") String referralCode,
                                                  @RequestParam("h2hAccount") String h2hAccount,
                                                  @RequestParam("pp") Optional<MultipartFile> pp,
                                                  @RequestParam("id") Optional<MultipartFile> id) {
        String userId = getUserId(token);
        if (userId==null){
            return FORBIDDEN;
        }
        try {
            Reseller rs = resellerService.findOne(userId);
            if (rs!=null) {
                UpdateAccountRequest request = new UpdateAccountRequest();
                request.setName(name);
                request.setAddress(address);
                request.setProvince(province);
                request.setCity(city);
                request.setDistrict(district);
                request.setSubdistrict(subdistrict);
                request.setZipcode(zipcode);
                request.setGender(gender);
                request.setIdentityNo(identityNo);
                request.setEmail(email);
                request.setMobileNumber(mobileNumber);
                request.setBirthDate(birthDate);
                request.setReferralCode(referralCode);
                request.setH2hAccount(h2hAccount);
                MultipartFile ppFile = null;
                if (pp.isPresent()){
                    ppFile = pp.get();
                }
                MultipartFile idFile = null;
                if (id.isPresent()){
                    idFile = id.get();
                }
                rs = resellerService.update(rs, request, ppFile, idFile);
                return getHttpStatus(new Response(rs));
            }else{
                return getHttpStatus(new Response("Nomor handphone tidak terdaftar"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return getHttpStatus(new Response(e.getMessage()));
        }
    }
}

