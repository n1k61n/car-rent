package com.example.carrent.dtos.message;

import com.example.carrent.models.User;
import lombok.Data;

@Data
public class MessageDto {
    private String firstName;
    private String lastName;
    private String email;
    private String message;
}
