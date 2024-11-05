package com.example.lab3;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class employeeControllerTest {

    @Test
    void calculateSalary() {
        employeeController x=new employeeController();
        assertEquals(x.calculateSalary(488999.00),48000);
    }
}