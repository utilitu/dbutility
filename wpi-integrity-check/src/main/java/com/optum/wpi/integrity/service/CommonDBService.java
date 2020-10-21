package com.optum.wpi.integrity.service;

import com.optum.wpi.integrity.config.CommonConfig;
import com.optum.wpi.integrity.model.Mismatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Map;
import java.util.TreeMap;

@Service
public class CommonDBService {

    private static final Logger _LOGGER = LoggerFactory.getLogger(CommonDBService.class);
    private Map<String, String> srcTableCounts = new TreeMap<String, String>();
    private Map<String, String> tgtTableCounts = new TreeMap<String, String>();
    
    @Autowired
    private CommonConfig commonConfig;

    public Map<String, String> validateSchema() {
        Map<String, String> outputMap = new TreeMap<String, String>();
        
        /* Start of Schema Validation */
        //validateTABLES(outputMap);
        //validateCOLUMNS(outputMap);
        //validateKEY_COLUMN_USAGE(outputMap);
        //validatePARAMETERS(outputMap);
        //validateROUTINES(outputMap);
        //validateTABLE_CONSTRAINTS(outputMap);
        validateINDEXES(outputMap);
        /* End of Schema Validation */
        
        //ArrayList<String> tableNames = getTableNames(commonConfig.getJdbcTemplate(), commonConfig.getSelect_query7());
        
        /* Start of Record count Validations */
        /*
        ListIterator<String> itr1 = tableNames.listIterator();
        while(itr1.hasNext()){
        	String tableName = itr1.next();
        	
        	validateRecordCounts(outputMap, tableName);
        }*/
        /* End of Record count Validations */
        
        
        // Below implementation is for comparing 5 random rows for each table...  
        /*
        Map<String, String> uniqueKeys = getUniqueKeys(commonConfig.getJdbcTemplate(), commonConfig.getSelect_query8());        
        
        ListIterator<String> itr2 = tableNames.listIterator();
        while(itr2.hasNext()){
        	String tableName = itr2.next();
        	
        	String uniqueKey = uniqueKeys.get(tableName);
        	// Get 5 random Unique Key values
        	String randomCount = "5";
        	String queryString = "SELECT " + uniqueKey + " FROM `" + tableName + "` ORDER BY RAND() LIMIT " + randomCount + ";"; 
        	ArrayList<String> randomUniqueKeyValues = getRandomUniqueKeyValues(commonConfig.getJdbcTemplate(), queryString);
        	if(randomUniqueKeyValues.size() > 0){
            	Iterator<String> randomUniqueKeyValuesItr = randomUniqueKeyValues.iterator();
            	StringBuffer randomUniqueKeyValuesStr = new StringBuffer("");
            	if(randomUniqueKeyValuesItr.hasNext()){
            		randomUniqueKeyValuesStr.append("'").append((String)randomUniqueKeyValuesItr.next()).append("'");
            	}
            	while(randomUniqueKeyValuesItr.hasNext()){
            		randomUniqueKeyValuesStr.append(",").append("'").append((String)randomUniqueKeyValuesItr.next()).append("'");
            	}
            	
            	// Get the data string corresponding to the Unique Key values from src database
            	String randomRowsQueryString = "SELECT * FROM `" + tableName + "` WHERE " + uniqueKey + " IN (" + randomUniqueKeyValuesStr + ") ORDER BY " + uniqueKey + ";";
            	System.out.println(randomRowsQueryString);
            	validateRandomRowsFromTable(outputMap, tableName, randomCount, randomRowsQueryString);        		
        	}
        }*/
         
        /* End of Table Data Validation */
        
        return outputMap;
    }
    
    private String validateRecordCounts(Map<String, String> outputMap, String tableName){
    	String validationStatus = "Failed";
    	
    	String srcTblRecordCount = fetchRecordCount(commonConfig.getJdbcTemplate(), tableName);
    	srcTableCounts.put(tableName, srcTblRecordCount);
    	String tgtTblRecordCount = fetchRecordCount(commonConfig.getTJdbcTemplate(), tableName);
    	tgtTableCounts.put(tableName, tgtTblRecordCount);
    	
    	if(srcTblRecordCount.equals(tgtTblRecordCount)){
        	validationStatus = "Passed";
        }
    	System.out.println("{\"Comparing Record Count of table named " + tableName + " -> \":\"" + validationStatus + "\"}");
    	outputMap.put("Comparing Record Count of table named " + tableName + " -> ", validationStatus);
    	
    	return validationStatus;
    }
    
    private String fetchRecordCount(JdbcTemplate template, String tableName){
    	StringBuffer recordCount = new StringBuffer("");
    	String queryString = "SELECT COUNT(1) FROM `" + tableName + "` ;"; 
    	//ArrayList<String> tableNames = new ArrayList<String>(); 
    	template.query(queryString, new Object[]{}, new RowMapper<String>() {
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
            	String result = rs.getString(1);
            	for (int i = 1; i < rs.getMetaData().getColumnCount() + 1; i++) {
            		recordCount.append(rs.getObject(1));
            		break;
                  }
                return result;
            }
        });
    	System.out.println("Record count for " + tableName + " is " + recordCount);
    	return recordCount.toString();
    }
    
    public String validateTABLES(Map<String, String> outputMap){
    	String validationStatus = validate(commonConfig.getJdbcTemplate(), commonConfig.getTJdbcTemplate(), commonConfig.getSelect_query1());
    	
    	System.out.println("{\"Comparing INFORMATION_SCHEMA.TABLES -> \":\"" + validationStatus + "\"}");
    	outputMap.put("Comparing INFORMATION_SCHEMA.TABLES -> ", validationStatus);
    	
    	return validationStatus;
    }
    
    private String validateCOLUMNS(Map<String, String> outputMap){
    	String validationStatus = validate(commonConfig.getJdbcTemplate(), commonConfig.getTJdbcTemplate(), commonConfig.getSelect_query2());
    	
    	System.out.println("{\"Comparing INFORMATION_SCHEMA.COLUMNS -> \":\"" + validationStatus + "\"}");
    	outputMap.put("Comparing INFORMATION_SCHEMA.COLUMNS -> ", validationStatus);
    	
    	return validationStatus;
    }
    
    private String validateKEY_COLUMN_USAGE(Map<String, String> outputMap){
    	String validationStatus = validate(commonConfig.getJdbcTemplate(), commonConfig.getTJdbcTemplate(), commonConfig.getSelect_query3());
    	
    	System.out.println("{\"Comparing INFORMATION_SCHEMA.KEY_COLUMN_USAGE -> \":\"" + validationStatus + "\"}");
    	outputMap.put("Comparing INFORMATION_SCHEMA.KEY_COLUMN_USAGE -> ", validationStatus);
    	
    	return validationStatus;
    }
    
    private String validatePARAMETERS(Map<String, String> outputMap){
    	String validationStatus = validate(commonConfig.getJdbcTemplate(), commonConfig.getTJdbcTemplate(), commonConfig.getSelect_query4());
    	
    	System.out.println("{\"Comparing INFORMATION_SCHEMA.PARAMETERS -> \":\"" + validationStatus + "\"}");
    	outputMap.put("Comparing INFORMATION_SCHEMA.PARAMETERS -> ", validationStatus);
    	
    	return validationStatus;
    }
    
    private String validateROUTINES(Map<String, String> outputMap){
    	String validationStatus = validate(commonConfig.getJdbcTemplate(), commonConfig.getTJdbcTemplate(), commonConfig.getSelect_query5());
    	
    	System.out.println("{\"Comparing INFORMATION_SCHEMA.ROUTINES -> \":\"" + validationStatus + "\"}");
    	outputMap.put("Comparing INFORMATION_SCHEMA.ROUTINES -> ", validationStatus);
    	
    	return validationStatus;
    }
    
    private String validateTABLE_CONSTRAINTS(Map<String, String> outputMap){
    	String validationStatus = validate(commonConfig.getJdbcTemplate(), commonConfig.getTJdbcTemplate(), commonConfig.getSelect_query6());
    	
    	System.out.println("{\"Comparing INFORMATION_SCHEMA.TABLE_CONSTRAINTS -> \":\"" + validationStatus + "\"}");
    	outputMap.put("Comparing INFORMATION_SCHEMA.TABLE_CONSTRAINTS -> ", validationStatus);
    	
    	return validationStatus;
    }
    
    private String validateINDEXES(Map<String, String> outputMap){
    	String validationStatus = validate(commonConfig.getJdbcTemplate(), commonConfig.getTJdbcTemplate(), commonConfig.getSelect_query9());
    	
    	System.out.println("{\"Comparing INFORMATION_SCHEMA.STATISTICS -> \":\"" + validationStatus + "\"}");
    	outputMap.put("Comparing INFORMATION_SCHEMA.STATISTICS -> ", validationStatus);
    	
    	return validationStatus;
    }
    
    private String validateRandomRowsFromTable(Map<String, String> outputMap, String tableName, String randomCount, String queryString){
    	String validationStatus = validate(commonConfig.getJdbcTemplate(), commonConfig.getTJdbcTemplate(), queryString);
    	
    	System.out.println("{\"Comparing " + randomCount + " random records from table " + tableName + "-> \":\"" + validationStatus + "\"}");
    	outputMap.put("Comparing " + randomCount + " random records from table " + tableName + "-> ", validationStatus);
    	
    	return validationStatus;
    }
    
    private String validate(JdbcTemplate srcTemplate, JdbcTemplate tgtTemplate, String queryString){
    	String validationStatus = "Failed";
    	
    	String srcOutput = executeQuery(srcTemplate, queryString);
    	String tgtOutput = executeQuery(tgtTemplate, queryString);
    	
        if(srcOutput.equals(tgtOutput)){
        	validationStatus = "Passed";
        }
    	
    	return validationStatus;
    }
    
    private String executeQuery(JdbcTemplate template, String queryString){
    	StringBuffer output = new StringBuffer("");
    	template.query(queryString, new Object[]{}, new RowMapper<String>() {
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
            	String result = rs.getString(1);
            	for (int i = 1; i < rs.getMetaData().getColumnCount() + 1; i++) {
            		output.append(rs.getObject(i)).append(",");
                  }
            	output.append("\n");
                return result;
            }
        });
    	System.out.println("executeQuery output = " + output);
    	return output.toString();
    }
    
    private ArrayList<String> getTableNames(JdbcTemplate template, String queryString){
    	ArrayList<String> tableNames = new ArrayList<String>(); 
    	template.query(queryString, new Object[]{}, new RowMapper<String>() {
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
            	String result = rs.getString(1);
            	for (int i = 1; i < rs.getMetaData().getColumnCount() + 1; i++) {
            		tableNames.add(String.valueOf(rs.getObject(1)));
            		break;
                  }
                return result;
            }
        });
    	System.out.println("Count of table names fetched = " + tableNames.size());
    	return tableNames;
    }
    
    private Map<String, String> getUniqueKeys(JdbcTemplate template, String queryString){
    	Map<String, String> uniqueKeys = new TreeMap<String, String>();
        
    	template.query(queryString, new Object[]{}, new RowMapper<String>() {
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
            	String result = rs.getString(1);
            	for (int i = 1; i < rs.getMetaData().getColumnCount() + 1; i++) {
            		uniqueKeys.put(String.valueOf(rs.getObject(1)), String.valueOf(rs.getObject(2)));
            		break;
                  }
                return result;
            }
        });
    	System.out.println("Count of unique keys fetched = " + uniqueKeys.size());
    	return uniqueKeys;
    }
    
    private ArrayList<String> getRandomUniqueKeyValues(JdbcTemplate template, String queryString){
    	ArrayList<String> randomUniqueKeyValues = new ArrayList<String>();
        
    	template.query(queryString, new Object[]{}, new RowMapper<String>() {
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
            	String result = rs.getString(1);
            	for (int i = 1; i < rs.getMetaData().getColumnCount() + 1; i++) {
            		randomUniqueKeyValues.add(String.valueOf(rs.getObject(1)));
            		break;
                  }
                return result;
            }
        });
    	System.out.println("Count of randomUniqueKeyValues = " + randomUniqueKeyValues.size());
    	return randomUniqueKeyValues;
    }

}