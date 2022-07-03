package com.example.springbootexceptionhandling.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;


@AllArgsConstructor @NoArgsConstructor @Getter @Setter
@Entity
@Table(name = "customer_tbl")
public class Customer {
    @Id
    @GeneratedValue
    private int customerId;

    @NotBlank(message="Customer name is required")
    @Size(min = 3, message = "Name is too short")
    @Size(max = 25, message = "Name is too long")
    private String customerName;

    @Email(message = "Invalid email id")
    @NotBlank(message = "Email id is required")
    @Column(unique = true)
    //if duplicate, throw exception during saving/updating record
    private String emailId;

    //https://stackoverflow.com/questions/16699007/regular-expression-to-match-standard-10-digit-phone-number
    @Pattern(regexp =
            "^(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]?\\d{3}[\\s.-]?\\d{4}$",
            message = "Invalid phone number")
    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    @Min(value = 12, message = "Age must be greater than or equal to 12")
    @Max(value = 150, message = "Age must be less than or equal to 150")
    @NotNull(message = "Age is required")
    private int customerAge;

}

//@NotBlank and @NotEmpty works with String only
//@NotBlank = @NotNull + @NotEmpty