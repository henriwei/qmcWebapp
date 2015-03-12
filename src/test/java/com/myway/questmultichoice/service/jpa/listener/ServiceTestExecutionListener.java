/**
 * Created on Jan 4, 2012
 */
package com.myway.questmultichoice.service.jpa.listener;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;

import org.dbunit.IDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.util.fileloader.XlsDataFileLoader;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;

import com.myway.questmultichoice.service.jpa.annotation.DataSets;

import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlProducer;
import org.xml.sax.InputSource;

/**
 * @author Clarence
 *
 */
public class ServiceTestExecutionListener implements TestExecutionListener {

	private IDatabaseTester databaseTester;
	private IDataSet classDataSet;
	
	public void afterTestClass(TestContext arg0) throws Exception {
		// TODO Auto-generated method stub		
	}

	public void afterTestMethod(TestContext arg0) throws Exception {
		// Clear up testing data if exists
		if (databaseTester != null) {			
			databaseTester.onTearDown();
		}				
	}

	public void beforeTestClass(TestContext testCtx) throws Exception {
		DataSets classDataSetAnnotation = testCtx.getTestClass().getAnnotation(DataSets.class);
		
		classDataSet = retrieveDataSet(classDataSetAnnotation, testCtx);	
	}

	public void beforeTestMethod(TestContext testCtx) throws Exception {

		// Check for existence of DataSets annotation for the method under testing
		DataSets methodDataSetAnnotation = testCtx.getTestMethod().getAnnotation(DataSets.class);
		
		IDataSet methodDataSet = retrieveDataSet(methodDataSetAnnotation, testCtx);
		if(methodDataSet != null){
			setupDataSet(testCtx, methodDataSet);
		} else {
			setupDataSet(testCtx, classDataSet);
		}
	}

	/**
	 * Create the IDataSet based on the annotation. If no DataSets annotation found, return null;
	 * @param dataSetAnnotation
	 * @param testCtx
	 * @return
	 * @throws Exception
	 */
	private IDataSet retrieveDataSet(DataSets dataSetAnnotation, TestContext testCtx) throws Exception{
		IDataSet dataSet = null;
		if ( dataSetAnnotation == null ) {
			return null;
		}
		
		File testDataSetFile = getTestDataSetFile(dataSetAnnotation, testCtx);
		// Populate data set if data set name exists
		dataSet = new FlatXmlDataSet(new FlatXmlProducer(new InputSource(new FileInputStream(testDataSetFile))));
//		XlsDataFileLoader xlsDataFileLoader = (XlsDataFileLoader) testCtx.getApplicationContext().getBean("xlsDataFileLoader");
//		IDataSet dataSet = xlsDataFileLoader.load(dataSetName);
		return dataSet;
	}
	
	/**
	 * Find and Return the File specified by the annotation DataSets with attribut "setUpDataSet". 
	 * The later expects some XML file which contains the dataset to setup before test.
	 * If no file is specified, a file with default name is searched. 
	 * The default file name is the same as the test class with extension ".xml".
	 * The extension ".xml" is required.
	 * The dataset file should be in the src/test/resources in Maven project. 
	 * They will be found in the folder target/test-class after build.
	 * @param dataSetName
	 * @param testCtx
	 * @return
	 * @throws Exception
	 */
	private File getTestDataSetFile(DataSets dataSetAnnotation, TestContext testCtx) throws Exception{
		String dataSetName = dataSetAnnotation.setUpDataSet();
		String dataSetResourceName = "";
		try {
			Class currentTestClass = testCtx.getTestClass();
			URL testDataSetURL;
			dataSetResourceName = currentTestClass.getSimpleName().concat(".xml");
			if ( ! dataSetName.equals("") ) {
				dataSetResourceName = dataSetName;
			}
			testDataSetURL = currentTestClass.getClassLoader().getResource(dataSetResourceName);
			return new File(testDataSetURL.getPath());
		} catch (Exception e){
			throw(new Exception("Unable to find the dataset " + dataSetResourceName));
		}
	}

	/**
	 * Get the IDatabaseTester component from the context and setup the given IDataSet.
	 * @param testCtx
	 * @param dataSet
	 * @throws Exception
	 */
	private void setupDataSet(TestContext testCtx, IDataSet dataSet) throws Exception{
		if(dataSet != null){
			databaseTester = (IDatabaseTester) testCtx.getApplicationContext().getBean("databaseTester");
			databaseTester.setDataSet(dataSet);	
			databaseTester.onSetup();
		}
	}
	
	public void prepareTestInstance(TestContext arg0) throws Exception {
		// TODO Auto-generated method stub
	}	
	
}
