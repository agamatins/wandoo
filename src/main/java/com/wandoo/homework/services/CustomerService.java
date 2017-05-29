package com.wandoo.homework.services;

import com.wandoo.homework.base.AppDefaults;
import com.wandoo.homework.model.Customer;
import com.wandoo.homework.model.Investment;
import com.wandoo.homework.model.Loan;
import com.wandoo.homework.model.beans.CustomerBean;
import com.wandoo.homework.model.requestbeans.InvestmentRequestBean;
import com.wandoo.homework.model.requestbeans.RegisterCustomerRequestBean;
import com.wandoo.homework.repositories.CustomerRepository;
import com.wandoo.homework.repositories.InvestmentRepository;
import com.wandoo.homework.repositories.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

import static com.wandoo.homework.base.BigDecimalUtils.amount;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private InvestmentRepository investmentRepository;

    public void registerCustomer(RegisterCustomerRequestBean registerCustomerRequestBean) throws Exception {
        if (customerRepository.getByEmail(registerCustomerRequestBean.getEmail()).isPresent()) {
            throw new Exception(AppDefaults.CUSTOMER_ALREADY_REGISTERED_WITH_EMAIL);
        }

        Customer customer = new Customer();
        customer.setFirstName(registerCustomerRequestBean.getFirstName());
        customer.setLastName(registerCustomerRequestBean.getLastName());
        customer.setEmail(registerCustomerRequestBean.getEmail());
        customerRepository.createCustomer(customer);
    }

    public Optional<CustomerBean> get(Long id) {
        return customerRepository.get(id).map(Customer::toBean);
    }

    public Optional<CustomerBean> getLastRegistered() {
        return customerRepository.getLastRegistered().map(Customer::toBean);
    }

    public Optional<CustomerBean> getByEmail(String email) {
        return customerRepository.getByEmail(email).map(Customer::toBean);
    }

    public void invest(InvestmentRequestBean investmentRequestBean) throws Exception {
        Optional<Customer> customer = customerRepository.get(investmentRequestBean.getCustomerId());
        if (!customer.isPresent()) {
            throw new Exception(AppDefaults.CANNOT_FIND_CUSTOMER_ID);
        }

        Optional<Loan> loan = loanRepository.get(investmentRequestBean.getLoanId());
        if (!loan.isPresent()) {
            throw new Exception(AppDefaults.CANNOT_FIND_LOAN_ID);
        }

        if (!loan.get().isInvestable()) {
            throw new Exception(AppDefaults.LOAN_NOT_INVESTABLE);
        }

        BigDecimal investableAmount = getActualInvestmentAmount(investmentRequestBean.getAmount(), loan.get().getAllowedInvestmentAmount());

        Investment investment = new Investment();
        investment.setLoan(loan.get());
        investment.setCustomer(customer.get());
        investment.setAmount(investableAmount.setScale(2, RoundingMode.HALF_UP));
        investmentRepository.createInvestment(investment);
    }

    private BigDecimal getActualInvestmentAmount(BigDecimal requestedAmount, BigDecimal maxAllowedAmount) {
        return amount(requestedAmount).gt(maxAllowedAmount) ? maxAllowedAmount : requestedAmount;
    }

}
