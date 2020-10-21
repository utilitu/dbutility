package com.optum.wpi.integrity.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "common")
public class CommonConfig {

    private final static Logger _LOGGER = LoggerFactory.getLogger(CommonConfig.class);
    private static JdbcTemplate jdbcTemp;
    private static JdbcTemplate tjdbcTemp;

    private String dbhosts;
    private String dbName;
    private String dbUserName;
    private String dbPassword;
    private String tdbhosts;
    private String tdbName;
    private String tdbUserName;
    private String tdbPassword;
    private String dbDriverClass;
    private String subclustername;
    private String defaultFileTemplate;
    private String fileTemplatePath;
    private String select_query1;
    private String select_query2;
    private String select_query3;
    private String select_query4;
    private String select_query5;
    private String select_query6;
    private String select_query7;
    private String select_query8;
    private String select_query9;

    public String getDbHosts() {
        return dbhosts;
    }

    public void setDbHosts(String dbhosts) {
        this.dbhosts = dbhosts;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getDbUserName() {
        return dbUserName;
    }

    public void setDbUserName(String dbUserName) {
        this.dbUserName = dbUserName;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }
    
    public String getTdbHosts() {
        return tdbhosts;
    }

    public void setTdbHosts(String tdbhosts) {
        this.tdbhosts = tdbhosts;
    }
    
    public String getTdbName() {
        return tdbName;
    }

    public void setTdbName(String tdbName) {
        this.tdbName = tdbName;
    }

    public String getTdbUserName() {
        return tdbUserName;
    }

    public void setTdbUserName(String tdbUserName) {
        this.tdbUserName = tdbUserName;
    }

    public String getTdbPassword() {
        return tdbPassword;
    }

    public void setTdbPassword(String tdbPassword) {
        this.tdbPassword = tdbPassword;
    }
    
    public String getDbDriverClass() {
        return dbDriverClass;
    }

    public void setDbDriverClass(String dbDriverClass) {
        this.dbDriverClass = dbDriverClass;
    }

    public String getDefaultFileTemplate() {
        return defaultFileTemplate;
    }

    public void setDefaultFileTemplate(String defaultFileTemplate) {
        this.defaultFileTemplate = defaultFileTemplate;
    }

    public String getSubclustername() {
        return subclustername;
    }

    public void setSubclustername(String subclustername) {
        this.subclustername = subclustername;
    }

    public String getFileTemplatePath() {
        return fileTemplatePath;
    }

    public void setFileTemplatePath(String fileTemplatePath) {
        this.fileTemplatePath = fileTemplatePath;
    }

    public String getSelect_query1() {
        return select_query1;
    }

	public void setSelect_query1(String select_query1) {
        this.select_query1 = select_query1;
    }

    public String getSelect_query2() {
        return select_query2;
    }

	public void setSelect_query2(String select_query2) {
        this.select_query2 = select_query2;
    }

    public String getSelect_query3() {
        return select_query3;
    }

	public void setSelect_query3(String select_query3) {
        this.select_query3 = select_query3;
    }

    public String getSelect_query4() {
        return select_query4;
    }

	public void setSelect_query4(String select_query4) {
        this.select_query4 = select_query4;
    }
	
	public String getSelect_query5() {
        return select_query5;
    }

	public void setSelect_query5(String select_query5) {
        this.select_query5 = select_query5;
    }
	
	public String getSelect_query6() {
        return select_query6;
    }

	public void setSelect_query6(String select_query6) {
        this.select_query6 = select_query6;
    }
	
	public String getSelect_query7() {
        return select_query7;
    }

	public void setSelect_query7(String select_query7) {
        this.select_query7 = select_query7;
    }
	
	public String getSelect_query8() {
        return select_query8;
    }

	public void setSelect_query8(String select_query8) {
        this.select_query8 = select_query8;
    }
	
	public String getSelect_query9() {
        return select_query9;
    }

	public void setSelect_query9(String select_query9) {
        this.select_query9 = select_query9;
    }

	public static JdbcTemplate getJdbcTemplate() {
        return jdbcTemp;
    }
    
	public static JdbcTemplate getTJdbcTemplate() {
        return tjdbcTemp;
    }

    @PostConstruct
    public void displayConfig() {
            DriverManagerDataSource ds = new DriverManagerDataSource();
            ds.setDriverClassName(getDbDriverClass());
            ds.setUsername(getDbUserName());
            ds.setPassword(getDbPassword());
            String host = getDbHosts();
            StringBuffer url = new StringBuffer("jdbc:mysql://").append(host).append(":3306/").append(getDbName()).append("?autoReconnect=true&useSSL=false");
            ds.setUrl(url.toString());
            jdbcTemp = new JdbcTemplate(ds);
        
            DriverManagerDataSource tds = new DriverManagerDataSource();
            tds.setDriverClassName(getDbDriverClass());
            tds.setUsername(getTdbUserName());
            tds.setPassword(getTdbPassword());
            String thost = getTdbHosts();
            StringBuffer turl = new StringBuffer("jdbc:mysql://").append(thost).append(":3306/").append(getTdbName()).append("?autoReconnect=true&useSSL=false");
            tds.setUrl(turl.toString());
            tjdbcTemp = new JdbcTemplate(tds);
    }

}