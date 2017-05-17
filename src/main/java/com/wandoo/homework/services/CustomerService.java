package com.wandoo.homework.services;

import com.wandoo.homework.base.AppDefaults;
import com.wandoo.homework.beans.CustomerBean;
import com.wandoo.homework.exceptions.CustomerNotFoundException;
import com.wandoo.homework.exceptions.DuplicateObjectException;
import com.wandoo.homework.exceptions.FailedInvestmentException;
import com.wandoo.homework.exceptions.LoanNotFoundException;
import com.wandoo.homework.model.Customer;
import com.wandoo.homework.model.Investment;
import com.wandoo.homework.model.Loan;
import com.wandoo.homework.repositories.CustomerRepository;
import com.wandoo.homework.repositories.InvestmentRepository;
import com.wandoo.homework.repositories.LoanRepository;
import com.wandoo.homework.requestbeans.CustomerRequestBean;
import com.wandoo.homework.requestbeans.InvestmentRequestBean;
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

    public void registerCustomer(CustomerRequestBean customerRequestBean) throws DuplicateObjectException {
        if (customerRepository.getByEmail(customerRequestBean.getEmail()).isPresent()) {
            throw new DuplicateObjectException(AppDefaults.CUSTOMER_ALREADY_REGISTERED_WITH_EMAIL);
        }

        Customer customer = new Customer();
        customer.setFirstName(customerRequestBean.getFirstName());
        customer.setLastName(customerRequestBean.getLastName());
        customer.setEmail(customerRequestBean.getEmail());
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

    public void invest(InvestmentRequestBean investmentRequestBean) throws CustomerNotFoundException, LoanNotFoundException, FailedInvestmentException {
        Optional<Customer> customer = customerRepository.get(investmentRequestBean.getCustomerId());
        if (!customer.isPresent()) {
            throw new CustomerNotFoundException(AppDefaults.CANNOT_FIND_CUSTOMER_ID);
        }

        Optional<Loan> loan = loanRepository.get(investmentRequestBean.getLoanId());
        if (!loan.isPresent()) {
            throw new LoanNotFoundException(AppDefaults.CANNOT_FIND_LOAN_ID);
        }

        if (!loan.get().isInvestable()) {
            throw new FailedInvestmentException(AppDefaults.LOAN_NOT_INVESTABLE);
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
