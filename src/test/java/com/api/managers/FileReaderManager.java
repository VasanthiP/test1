package com.api.managers;

import com.api.dataproviders.ConfigFileReader;
import com.api.helpers.Generic;

public class FileReaderManager {
	private static FileReaderManager fileReaderManager;
    private static ConfigFileReader configFileReader;
    private static Generic gen;

	
	 private FileReaderManager() {
		
	 }
	 
	 public static FileReaderManager getInstance( ) {
		 return (fileReaderManager == null) ? (fileReaderManager= new FileReaderManager()) : fileReaderManager;
	 }
	 
	 public ConfigFileReader getConfigReader() {
	   return (configFileReader == null) ?(configFileReader= new ConfigFileReader()) : configFileReader;
	 }
	 
	 public Generic getGenericClass() {
		   return (gen == null) ?(gen= new Generic()) : gen;
		 }
	 

}
