package com.example.excelTest;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.excelTest.service.ExcelServiceImpl;

@SpringBootApplication
public class ExcelTestApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(ExcelTestApplication.class, args);

		ExcelServiceImpl excelServiceImpl = new ExcelServiceImpl();
		excelServiceImpl.makeExcel();
	}

}
