package com.learn.springboot.server;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class AppServerCliProperties {
  private String[] args = null;
  private Options options = new Options();

  public AppServerCliProperties(String[] args) {
    this.args = args;
    options.addOption("f",  "file",  true, "Here you can specify the property file .");
    options.addOption("t",  "type",  true, "Here you can specify the server instance type .");
  }

  public Properties parseAndRead() {
    Properties props = new Properties();
    InputStream propsFileIs = null;
    try {
      CommandLineParser parser = new DefaultParser();
      CommandLine cmd = parser.parse(options, args);
      String propertiyFileName = cmd.getOptionValue("f");
      String instanceType = cmd.getOptionValue("t");
      if (propertiyFileName!=null && !propertiyFileName.equals("")) {
        propsFileIs = new FileInputStream(cmd.getOptionValue("f"));
        props.load(propsFileIs);
      }
      else {
        String filename = "app.properties";
        if (instanceType!=null && instanceType.equalsIgnoreCase("LOCAL")) {  // Local
          filename = "instanceLOCAL.properties";
        }
        propsFileIs = AppServerCliProperties.class.getClassLoader().getResourceAsStream(filename);
        if(propsFileIs!=null) {
          props.load(propsFileIs);
        }
      }
    } catch (ParseException | IOException e) {
      //log.warn("Failed to parse comand line properties", e);
      e.printStackTrace();
    } finally {
      if (propsFileIs!=null) {
        try {
          propsFileIs.close();
        } catch (IOException e) { e.printStackTrace(); }
      }
    }
    return props;
  }
}
