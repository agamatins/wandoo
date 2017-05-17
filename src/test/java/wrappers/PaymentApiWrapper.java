package wrappers;

import com.wandoo.homework.beans.PaymentBean;
import com.wandoo.homework.requestbeans.ImportPaymentRequestBean;
import com.wandoo.homework.responsebeans.BaseResponseBean;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

public class PaymentApiWrapper extends BaseApiWrapper{

    public PaymentApiWrapper(int localPort) {
        super.localPort = localPort;
    }

    public BaseResponseBean importPayment(ImportPaymentRequestBean bean) {
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target(getTarget("payment")).path("import");

        return webTarget.request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(bean, MediaType.APPLICATION_JSON))
                .readEntity(BaseResponseBean.class);
    }

    @SuppressWarnings("unchecked")
    public BaseResponseBean<PaymentBean> getPaymentWithId(Long id) {
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target(getTarget("payment")).path("get").path(id.toString());

        return (BaseResponseBean<PaymentBean>)webTarget.request(MediaType.APPLICATION_JSON)
                .get()
                .readEntity(BaseResponseBean.class);
    }
}
