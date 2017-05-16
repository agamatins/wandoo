package wrappers;


import com.wandoo.homework.beans.CustomerBean;
import com.wandoo.homework.requestbeans.CustomerRequestBean;
import com.wandoo.homework.responsebeans.BaseResponseBean;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

public class ClientApiWrapper extends BaseApiWrapper{

    public ClientApiWrapper(int localPort) {
        super.localPort = localPort;
    }

    public BaseResponseBean registerCustomer(CustomerRequestBean bean) {
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
}
