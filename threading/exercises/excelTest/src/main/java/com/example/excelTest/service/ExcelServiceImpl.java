package com.example.excelTest.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.List;
import java.util.ArrayList;


import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell; 
import java.io.File; 
import java.util.UUID;
import java.io.ByteArrayOutputStream;
import org.apache.commons.codec.binary.Base64;

public class ExcelServiceImpl {
    private static final int batchSize = 1000;
    private static final int totalRecords = 100000;

    public void makeExcel() throws IOException {
        try (// workbook object 
        XSSFWorkbook workbook = new XSSFWorkbook()) {
            // spreadsheet object 
            XSSFSheet spreadsheet 
                = workbook.createSheet(" Student Data "); 
  
            // This data needs to be written (Object[]) 
            Map<String, Object[]> studentData 
                = new LinkedHashMap<String, Object[]>(); 
  
            System.out.println("creating data");
            createData(studentData); 
            System.out.println("data creation done");
            Set<String> keyid = studentData.keySet(); 
  
            
            final long startTime = System.currentTimeMillis();
            // writing the data into the sheets... 

            // WAY 1 - THREADING
            // 1 - {}, 2 - {}...
            // make a batch of n/1000 threads
            // i.e - n = 10000 => 10 threads, each will write 1000 records
            //       n = 1M => 1000000 / 1000 => 1000 threads, each till write 1000 records
            //       
            
            // divide n request into n/1000
            List<Thread> threadList = new ArrayList<>();
            for(int p = 1; p <= totalRecords; p += batchSize + 1) {
                final int pp = p;
                Thread z = new Thread(() -> {
                    // size from p to p+batchSize
                    try {
                        for(int i = pp; i <= pp + batchSize; i++) {
                            // System.out.println("writing ::" + i);
                            makeRow(spreadsheet, studentData, i, String.valueOf(i));
                        }
                    } catch(Exception e) {
                        System.out.println("error while writing row :: " + e.getMessage());
                    }
                });
                threadList.add(z);
            }

            System.out.println("No of threads :: "+ threadList.size());
            for(Thread thread : threadList) {
                thread.run();
            }

            for(Thread thread : threadList) {
                thread.join();
            }

            // WAY 2 : sync
            // int rowid = 0; 
            // for (String key : keyid) { 
            //     makeRow(spreadsheet, studentData, rowid, key); 
            //     rowid++;
            // } 

            final long endTime = System.currentTimeMillis();

            System.out.println("Total execution time: " + (endTime - startTime) + "ms");
  
            // .xlsx is the format for Excel Sheets... 
            // writing the workbook into the file... 
            FileOutputStream out = new FileOutputStream( 
                new File("C:/Users/TanmayM/javaConceptualPrograms/threading/exercises/excelTest/test_" + (endTime - startTime) + "ms" + ".xlsx")); 
  
            workbook.write(out); 
            out.close();

            // String b64 = export(workbook);

        } catch (Exception e) {
            System.out.println("Error while making file ::" + e.getMessage());
        } finally {
            System.out.println("File made now");
        }
    
    }

    private void makeRow(XSSFSheet spreadsheet, Map<String, Object[]> studentData, int rowid, String key) {
        XSSFRow row;
        if(studentData.containsKey(key)) {
            row = spreadsheet.createRow(rowid); 
            Object[] objectArr = studentData.get(key); 
            int cellid = 0; 
      
            for (Object obj : objectArr) { 
                Cell cell = row.createCell(cellid++); 
                cell.setCellValue((String)obj); 
            }
        }
    }

    private void createData(Map<String, Object[]> studentData) {
        
        for(int i = 1; i <= totalRecords; i++) {
            Object[] obj = new Object[101];
            obj[0] = String.valueOf(i);
            for(int j = 1; j < 100; j++) {
                UUID randomUUID = UUID.randomUUID();
                obj[j] = randomUUID.toString();
            }
            studentData.put(String.valueOf(i), obj);
        }
    }

    public String export(XSSFWorkbook workbook) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		workbook.write(bos);
		workbook.close();
		byte[] bytes = bos.toByteArray();
		String base64c = Base64.encodeBase64String(bytes);
		bos.close();
		return base64c;

	}
}


// before threading - 81725ms (for 10k rows)