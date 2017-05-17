package wrappers;


import com.wandoo.homework.beans.CustomerBean;
import com.wandoo.homework.requestbeans.RegisterCustomerRequestBean;
import com.wandoo.homework.requestbeans.InvestmentRequestBean;
import com.wandoo.homework.responsebeans.BaseResponseBean;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

public class CustomerApiWrapper extends BaseApiWrapper{

    public CustomerApiWrapper(int localPort) {
        super.localPort = localPort;
    }

    public BaseResponseBean registerCustomer(RegisterCustomerRequestBean bean) {
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target(getTarget("customer")).path("register");

        return webTarget.request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(bean, MediaType.APPLICATION_JSON))
                .readEntity(BaseResponseBean.class);
    }

    @SuppressWarnings("unchecked")
    public BaseResponseBean<CustomerBean> getLastRegisteredCustomer() {
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target(getTarget("customer")).path("get-last");

        return (BaseResponseBean<CustomerBean>)webTarget.request(MediaType.APPLICATION_JSON)
                .get()
                .readEntity(BaseResponseBean.class);
    }

    public BaseResponseBean invest(InvestmentRequestBean bean) {
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target(getTarget("customer")).path("invest");

        return webTarget.request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(bean, MediaType.APPLICATION_JSON))
                .readEntity(BaseResponseBean.class);
    }
}
