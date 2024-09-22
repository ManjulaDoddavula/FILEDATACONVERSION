package DataConversion.DataConversion;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

@RestController
@RequestMapping("api/files")
public class Controller {

	@Autowired
    private Service service;
	
	@PostMapping(value = "/upload-csv", consumes = "multipart/form-data")
    public ResponseEntity<?> uploadCSV(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Please upload a valid file.");
        }

        try {
            List<Map<String, String>> jsonList = service.convertFileToJson(file);
            return ResponseEntity.ok(jsonList);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error processing the file: " + e.getMessage());
        }
    }
	
	//file uploaded successfully
//	 @PostMapping(value = "/upload-csv", consumes = "multipart/form-data")
//	    public ResponseEntity<String> uploadCSV(@RequestParam("file") MultipartFile file) {
//	        if (file.isEmpty()) {
//	            return ResponseEntity.badRequest().body("Please upload a valid CSV file.");
//	        }
//
//	        // Your file processing code goes here
//
//	        return ResponseEntity.ok("File uploaded successfully.");
//	    }
	 
//	@PostMapping("/upload-csv")
//    public ResponseEntity<String> uploadCSV(@RequestParam("file") MultipartFile file) {
//        if (file.isEmpty()) {
//            return ResponseEntity.badRequest().body("Please upload a valid CSV file.");
//        }
//
//        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
//            CSVReader csvReader = new CSVReader(reader);
//            List<String[]> records = csvReader.readAll();
//            
//            JSONArray jsonArray = new JSONArray();
//            
//            String[] headers = records.get(0); // First row is usually the header
//            
//            for (int i = 1; i < records.size(); i++) {
//                String[] row = records.get(i);
//                JSONObject jsonObject = new JSONObject();
//                
//                for (int j = 0; j < headers.length; j++) {
//                    jsonObject.put(headers[j], row[j]);
//                }
//                
//                jsonArray.put(jsonObject);
//            }
//
//            return ResponseEntity.ok(jsonArray.toString());
//
//        } catch (Exception e) {
//            return ResponseEntity.status(500).body("Error processing the file: " + e.getMessage());
//        }
//    }
	
//	@PostMapping("/upload")
//    public ResponseEntity<List<Map<String, String>>> uploadExcelFile(@RequestParam("file") MultipartFile file) {
//        try {
//            // Convert Excel to JSON
//            List<Map<String, String>> jsonData = service.convertExcelToJson(file);
//            return ResponseEntity.ok(jsonData); // Returning JSON response
//
//        } catch (IOException e) {
//            return ResponseEntity.status(500).body(null);
//        }
//    }
	
//    @PostMapping("/upload")
//    public ResponseEntity<InputStreamResource> uploadCsvFile(@RequestParam("file") MultipartFile file) {
//        try {
//            // Convert CSV to JSON
//            List<Map<String, String>> jsonData = service.convertCsvToJson(file);
//            String jsonFilePath = service.writeJsonFile(jsonData, "output.json");
//
//            // Download the generated JSON file
//            File jsonFile = new File(jsonFilePath);
//            InputStreamResource resource = new InputStreamResource(new FileInputStream(jsonFile));
//
//            HttpHeaders headers = new HttpHeaders();
//            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=output.json");
//
//            return ResponseEntity.ok()
//                    .headers(headers)
//                    .contentLength(jsonFile.length())
//                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
//                    .body(resource);
//
//        } catch (Exception e) {
//            return ResponseEntity.status(500).build();
//        }
//    }

}