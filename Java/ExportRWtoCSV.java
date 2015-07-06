package jp.co.microstrategy.com.sdk.test;

import java.io.FileOutputStream;

import com.microstrategy.web.objects.WebIServerSession;
import com.microstrategy.web.objects.WebObjectsException;
import com.microstrategy.web.objects.WebObjectsFactory;
import com.microstrategy.web.objects.rw.EnumRWExecutionModes;
import com.microstrategy.web.objects.rw.RWExportSettings;
import com.microstrategy.web.objects.rw.RWInstance;
import com.microstrategy.webapi.EnumDSSXMLApplicationType;
import com.microstrategy.webapi.EnumDSSXMLStatus;

public class ExportRWtoCSV {
	
	public static void main(String[] args) {
		 

		WebObjectsFactory factory=WebObjectsFactory.getInstance();
		WebIServerSession serverSession=factory.getIServerSession(); 
		   
		serverSession.setServerName("JPN-AHORIUCHI2");
		serverSession.setServerPort(0);
		serverSession.setProjectName("MicroStrategy Tutorial");
		serverSession.setLogin("administrator");
		serverSession.setPassword("");
		serverSession.setApplicationType(EnumDSSXMLApplicationType.DssXmlApplicationCustomApp); 
		   
		RWInstance rwi = null;
		try { 
            rwi = factory.getRWSource().getNewInstance("6A8AC5084649AEB72936CAB04F484DA4");
	        rwi.setExecutionMode(EnumRWExecutionModes.RW_MODE_CSV);

            rwi.setAsync(false);
            int status = rwi.pollStatus();
            
            RWExportSettings expSet = rwi.getExportSettings();
            expSet.setGridKey("K44");
           
            int count = 0;
            while (count < 10) {
            	if (status == EnumDSSXMLStatus.DssXmlStatusResult){
        			FileOutputStream fos = new FileOutputStream("D:/Z_test/document.csv");
        			fos.write(rwi.getExportData());
        			fos.flush();
        			fos.close();
        			System.out.println("File saved");
            		break;
            	}
            	
            	Thread.sleep(100);
            	count++;
         	}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				serverSession.closeSession();
			} catch (WebObjectsException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
