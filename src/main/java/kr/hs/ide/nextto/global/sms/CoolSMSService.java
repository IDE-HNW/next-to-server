package kr.hs.ide.nextto.global.sms;

import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class CoolSMSService {

    @Value("${coolsms.api}")
    private String apiKey;

    @Value("${coolsms.secret}")
    private String apiSecret;

    @Value("${coolsms.sender}")
    private String sender;

    public void certifiedPhoneNumber(String phoneNumber, String message)
            throws CoolsmsException {
        Message coolsms = new Message(apiKey, apiSecret);
        HashMap<String, String> params = new HashMap<>();
        params.put("to", phoneNumber);
        params.put("from", sender);
        params.put("type", "SMS");
        params.put("text", message);

        coolsms.send(params);
    }
}
