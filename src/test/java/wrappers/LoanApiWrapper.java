package wrappers;


import com.wandoo.homework.model.beans.LoanBean;
import com.wandoo.homework.model.requestbeans.ImportLoanRequestBean;
import com.wandoo.homework.model.responsebeans.BaseResponseBean;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.util.List;

public class LoanApiWrapper extends BaseApiWrapper{

    public LoanApiWrapper(int localPort) {
        super.localPort = localPort;
    }

    public BaseResponseBean importLoan(ImportLoanRequestBean bean) {
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target(getTarget("loan")).path("import");

        return webTarget.request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(bean, MediaType.APPLICATION_JSON))
                .readEntity(BaseResponseBean.class);
    }

    @SuppressWarnings("unchecked")
    public BaseResponseBean<LoanBean> getLoanWithId(Long id) {
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target(getTarget("loan")).path("get").path(id.toString());

        return (BaseResponseBean<LoanBean>)webTarget.request(MediaType.APPLICATION_JSON)
                .get()
                .readEntity(BaseResponseBean.class);
    }

    @SuppressWarnings("unchecked")
    public BaseResponseBean<List<LoanBean>> listInvestableLoans() {
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target(getTarget("loan")).path("list-investable");

        return (BaseResponseBean<List<LoanBean>>)webTarget.request(MediaType.APPLICATION_JSON)
                .get()
                .readEntity(BaseResponseBean.class);
    }
}
