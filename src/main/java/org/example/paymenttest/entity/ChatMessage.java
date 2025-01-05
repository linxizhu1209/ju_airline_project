package org.example.paymenttest.entity;

import com.fasterxml.jackson.databind.DatabindException;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ChatMessage {

    private String content;
    private String sender;
    private Date time;


}
