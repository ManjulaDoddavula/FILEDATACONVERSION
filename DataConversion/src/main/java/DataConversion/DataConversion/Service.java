package DataConversion.DataConversion;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;

@org.springframework.stereotype.Service
public class Service {

	public List<Map<String, String>> convertFileToJson(MultipartFile file) throws IOException {
        String fileType = getFileType(file.getOriginalFilename());
        if (fileType.equals("csv")) {
            return convertCsvToJson(file);
        } else if (fileType.equals("excel")) {
            return convertExcelToJson(file);
        } else {
            throw new IllegalArgumentException("Unsupported file type: " + fileType);
        }
    }

    private String getFileType(String fileName) {
        if (fileName.endsWith(".csv")) {
            return "csv";
        } else if (fileName.endsWith(".xls") || fileName.endsWith(".xlsx")) {
            return "excel";
        }
        return "unknown";
    }

    private List<Map<String, String>> convertCsvToJson(MultipartFile file) throws IOException {
        List<Map<String, String>> jsonList = new ArrayList<>();

        try (CSVReader csvReader = new CSVReader(new InputStreamReader(file.getInputStream()))) {
            String[] headers = csvReader.readNext(); // Read header
            String[] row;
            while ((row = csvReader.readNext()) != null) {
                Map<String, String> jsonObject = new HashMap<>();
                for (int i = 0; i < headers.length; i++) {
                    jsonObject.put(headers[i].trim(), row[i]);
                }
                jsonList.add(jsonObject);
            }
        } catch (Exception e) {
            throw new IOException("Failed to process the CSV file", e);
        }

        return jsonList;
    }

    private List<Map<String, String>> convertExcelToJson(MultipartFile file) throws IOException {
        List<Map<String, String>> jsonList = new ArrayList<>();

        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0); // Reading the first sheet
            Row headerRow = sheet.getRow(0); // Assuming the first row is the header

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row currentRow = sheet.getRow(i);
                if (currentRow != null) {
                    Map<String, String> jsonObject = new HashMap<>();

                    for (int j = 0; j < headerRow.getLastCellNum(); j++) {
                        Cell headerCell = headerRow.getCell(j);
                        Cell valueCell = currentRow.getCell(j);

                        String header = headerCell.getStringCellValue().trim();
                        String value = getCellValueAsString(valueCell);

                        jsonObject.put(header, value);
                    }
                    jsonList.add(jsonObject);
                }
            }
        } catch (Exception e) {
            throw new IOException("Failed to process the Excel file", e);
        }

        return jsonList;
    }

    private String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    return String.valueOf(cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }
	
	
	//File uploading successfully
//	public List<Map<String, String>> convertExcelToJson(MultipartFile file) throws IOException {
//        List<Map<String, String>> jsonList = new ArrayList<>();
//        
//        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
//            Sheet sheet = workbook.getSheetAt(0); // Reading the first sheet
//            Row headerRow = sheet.getRow(0); // Assuming the first row is the header
//
//            // Loop through all rows, starting from the second row (index 1) for actual data
//            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
//                Row currentRow = sheet.getRow(i);
//                if (currentRow != null) {
//                    Map<String, String> jsonObject = new HashMap<>();
//                    
//                    // Loop through each cell in the row
//                    for (int j = 0; j < headerRow.getLastCellNum(); j++) {
//                        Cell headerCell = headerRow.getCell(j);
//                        Cell valueCell = currentRow.getCell(j);
//
//                        // Convert the cell content to string (you can also handle other types explicitly)
//                        String header = headerCell.getStringCellValue().trim();
//                        String value = getCellValueAsString(valueCell);
//
//                        jsonObject.put(header, value);
//                    }
//                    jsonList.add(jsonObject);
//                }
//            }
//        } catch (Exception e) {
//            System.err.println("Error while processing Excel file: " + e.getMessage());
//            e.printStackTrace();
//            throw new IOException("Failed to process the Excel file", e); // Handle the exception more clearly
//        }
//
//        return jsonList;
//    }
//
//    private String getCellValueAsString(Cell cell) {
//        if (cell == null) {
//            return "";
//        }
//        switch (cell.getCellType()) {
//            case STRING:
//                return cell.getStringCellValue();
//            case NUMERIC:
//                if (DateUtil.isCellDateFormatted(cell)) {
//                    return cell.getDateCellValue().toString();
//                } else {
//                    return String.valueOf(cell.getNumericCellValue());
//                }
//            case BOOLEAN:
//                return String.valueOf(cell.getBooleanCellValue());
//            case FORMULA:
//                return cell.getCellFormula();
//            default:
//                return "";
//        }
//    }

	
	
	
//	 public List<Map<String, String>> convertCsvToJson(MultipartFile file) throws Exception {
//	        List<Map<String, String>> jsonList = new ArrayList<>();
//	        try (CSVReader csvReader = new CSVReader(new InputStreamReader(file.getInputStream()))) {
//	            String[] headers = csvReader.readNext(); // Read the headers (first row)
//	            String[] values;
//	            
//	            while ((values = csvReader.readNext()) != null) {
//	                Map<String, String> jsonObject = new HashMap<>();
//	                for (int i = 0; i < headers.length; i++) {
//	                    //jsonObject.put(headers[i], values[i]);
//	                	jsonObject.put(headers[i].trim(), values[i].trim()); // Ensure proper trimming
//	                }
//	                jsonList.add(jsonObject);
//	            }
//	        } catch (IOException e) {
//	            throw new Exception("Error processing CSV file", e);
//	        }
//	        return jsonList;
//	    }
//
//	    public String writeJsonFile(List<Map<String, String>> data, String filename) throws IOException {
//	        ObjectMapper objectMapper = new ObjectMapper();
//	        File jsonFile = new File(filename);
//	        objectMapper.writeValue(jsonFile, data);
//	        return jsonFile.getAbsolutePath();
//	    }
}