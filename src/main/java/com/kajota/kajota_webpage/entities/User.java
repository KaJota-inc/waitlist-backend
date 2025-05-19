package com.kajota.kajota_webpage.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
public class User {
    private final String firstname;
    private final String email;
    private final String mobileNumber;
    private final Boolean keepUpToDate;
    private final List<String> kajotaUsages;
}
