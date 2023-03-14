package entelect.training.incubator.spring.loyalty.client;

import com.baeldung.springsoap.client.gen.CaptureRewardsRequest;
import com.baeldung.springsoap.client.gen.CaptureRewardsResponse;
import com.baeldung.springsoap.client.gen.RewardsBalanceRequest;
import com.baeldung.springsoap.client.gen.RewardsBalanceResponse;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import java.math.BigDecimal;

public class LoyaltyClient extends WebServiceGatewaySupport {
    public CaptureRewardsResponse captureRewardsRequest(String passportNumber, BigDecimal amount) {
        CaptureRewardsRequest request = new CaptureRewardsRequest();
        request.setPassportNumber(passportNumber);
        request.setAmount(amount);

        CaptureRewardsResponse response = (CaptureRewardsResponse) getWebServiceTemplate()
                .marshalSendAndReceive(request);
        return response;
    }

    public RewardsBalanceResponse rewardsBalance(String passportNumber) {
        RewardsBalanceRequest request = new RewardsBalanceRequest();
        request.setPassportNumber(passportNumber);


        RewardsBalanceResponse response = (RewardsBalanceResponse) getWebServiceTemplate()
                .marshalSendAndReceive(request);
        return response;
    }

}

