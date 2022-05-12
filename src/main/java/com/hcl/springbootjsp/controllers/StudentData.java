package com.hcl.springbootjsp.controllers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentData {
    private int id;
    private String firstName;
    private String lastName;
    private String contactNum;
}