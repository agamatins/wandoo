package com.wandoo.homework.requestbeans;

import com.wandoo.homework.base.MessageType;
import com.wandoo.homework.base.ValidationMessage;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class CustomerRequestBean {

    private String firstName;
    private String lastName;
    private String email;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<ValidationMessage> validate() {
        final Pattern VALID_EMAIL_ADDRESS_REGEX =
                Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

        final Pattern VALID_NAMES_REGEX =
                Pattern.compile("^[A-Za-z]*$");

        List<ValidationMessage> validationErrors = new ArrayList<>();
        if (StringUtils.isBlank(this.getFirstName())) {
            validationErrors.add(new ValidationMessage(MessageType.ERROR, "first name cannot be empty", "firstName"));
        } else if (!VALID_NAMES_REGEX.matcher(this.getFirstName()).matches()) {
            validationErrors.add(new ValidationMessage(MessageType.ERROR, "first name should contain only letters", "firstName"));
        }

        if (StringUtils.isBlank(this.getLastName())) {
            validationErrors.add(new ValidationMessage(MessageType.ERROR, "last name cannot be empty", "lastName"));
        } else if (!VALID_NAMES_REGEX.matcher(this.getLastName()).matches()) {
            validationErrors.add(new ValidationMessage(MessageType.ERROR, "last name should contain only letters", "lastName"));
        }

        if (StringUtils.isBlank(this.getEmail())) {
            validationErrors.add(new ValidationMessage(MessageType.ERROR, "email cannot be empty", "email"));
        } else if (!VALID_EMAIL_ADDRESS_REGEX.matcher(this.getEmail()).matches()) {
            validationErrors.add(new ValidationMessage(MessageType.ERROR, "incorrest email format", "email"));
        }

        return validationErrors;
    }
}
