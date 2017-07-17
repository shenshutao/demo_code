package edu.nus.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class Product implements Serializable {
    private static final long serialVersionUID = 148908230946888888L;

    private long id;
    private String name;
    private long price;
}