package com.example.carrent.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Chat {
    private String from;
    private String to;
    private String text;
    private String sessionId;
    private String content;

}
