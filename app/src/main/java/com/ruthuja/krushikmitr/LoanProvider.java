package com.ruthuja.krushikmitr;

public class LoanProvider {
    private String name;
    private String interestRate;
    private String loanAmountRange;
    private String contactInfo;

    public LoanProvider(String name, String interestRate, String loanAmountRange, String contactInfo) {
        this.name = name;
        this.interestRate = interestRate;
        this.loanAmountRange = loanAmountRange;
        this.contactInfo = contactInfo;
    }

    public String getName() {
        return name;
    }

    public String getInterestRate() {
        return interestRate;
    }

    public String getLoanAmountRange() {
        return loanAmountRange;
    }

    public String getContactInfo() {
        return contactInfo;
    }
}
