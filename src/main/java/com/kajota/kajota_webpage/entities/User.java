package com.kajota.kajota_webpage.entities;

import lombok.Data;

import java.util.List;

@Data
public class User {
    private final String firstname;
    private final String email;
    private final String mobileNumber;
    private final Boolean keepUpToDate;
    private final List<String> kajotaUsages;

    public User(
            String firstname,
            String email,
            String mobileNumber,
            boolean keepUpToDate,
            List<String> kajotaUsages
        )
    {
        this.firstname = firstname;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.keepUpToDate = keepUpToDate;
        this.kajotaUsages = kajotaUsages;
    }
}
