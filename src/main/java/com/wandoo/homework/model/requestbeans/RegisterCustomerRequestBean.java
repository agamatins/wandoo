package com.wandoo.homework.model.requestbeans;

import com.wandoo.homework.base.AppDefaults;
import com.wandoo.homework.base.MessageType;
import com.wandoo.homework.base.ValidationMessage;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class RegisterCustomerRequestBean {

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
                Pattern.compile("^[a-zA-z]+([ '-][a-zA-Z]+)*", Pattern.CASE_INSENSITIVE);

        List<ValidationMessage> validationErrors = new ArrayList<>();
        if (StringUtils.isBlank(this.getFirstName())) {
            validationErrors.add(new ValidationMessage(MessageType.ERROR, AppDefaults.CANNOT_BE_EMPTY, "firstName"));
        } else if (!VALID_NAMES_REGEX.matcher(this.getFirstName()).matches()) {
            validationErrors.add(new ValidationMessage(MessageType.ERROR, AppDefaults.INCORRECT_FORMAT, "firstName"));
        } else if (this.getFirstName().length() > AppDefaults.NAME_MAX_LENGTH) {
            validationErrors.add(new ValidationMessage(MessageType.ERROR, AppDefaults.TOO_LONG, "firstName"));
        }

        if (StringUtils.isBlank(this.getLastName())) {
            validationErrors.add(new ValidationMessage(MessageType.ERROR, AppDefaults.CANNOT_BE_EMPTY, "lastName"));
        } else if (!VALID_NAMES_REGEX.matcher(this.getLastName()).matches()) {
            validationErrors.add(new ValidationMessage(MessageType.ERROR, AppDefaults.INCORRECT_FORMAT, "lastName"));
        } else if (this.getLastName().length() > AppDefaults.NAME_MAX_LENGTH) {
            validationErrors.add(new ValidationMessage(MessageType.ERROR, AppDefaults.TOO_LONG, "lastName"));
        }

        if (StringUtils.isBlank(this.getEmail())) {
            validationErrors.add(new ValidationMessage(MessageType.ERROR, AppDefaults.CANNOT_BE_EMPTY, "email"));
        } else if (!VALID_EMAIL_ADDRESS_REGEX.matcher(this.getEmail()).matches()) {
            validationErrors.add(new ValidationMessage(MessageType.ERROR, AppDefaults.INCORRECT_FORMAT, "email"));
        }

        return validationErrors;
    }
}
