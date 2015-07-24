package jp.co.microstrategy.com.sdk.test;

import com.microstrategy.web.app.beans.EnumWebStateLevel;
import com.microstrategy.web.beans.BeanFactory;
import com.microstrategy.web.beans.ExpressionBean;
import com.microstrategy.web.beans.ReportBean;
import com.microstrategy.web.objects.WebExpression;
import com.microstrategy.web.objects.WebFolder;
import com.microstrategy.web.objects.WebIServerSession;
import com.microstrategy.web.objects.WebObjectSource;
import com.microstrategy.web.objects.WebObjectsException;
import com.microstrategy.web.objects.WebObjectsFactory;
import com.microstrategy.web.objects.WebOperatorNode;
import com.microstrategy.web.objects.WebReportInstance;
import com.microstrategy.web.objects.WebReportManipulation;
import com.microstrategy.webapi.EnumDSSXMLApplicationType;
import com.microstrategy.webapi.EnumDSSXMLExecutionFlags;
import com.microstrategy.webapi.EnumDSSXMLFunction;
import com.microstrategy.webapi.EnumDSSXMLObjectTypes;
import com.microstrategy.webapi.EnumDSSXMLReportSaveAsFlags;
import com.microstrategy.webapi.EnumDSSXMLResultFlags;

public class ToggleOperatorRpt {

 public static void main(String[] args) {
	 

	String strParentFolderID = "EB32970647D1EAC97CA4FB89BB0F442F";
	String strReportID = "98D34A5044BB2FBBD6FCE6A5A38E74F0";

   WebObjectsFactory factory=WebObjectsFactory.getInstance();
   WebIServerSession serverSession=factory.getIServerSession(); 
   
   serverSession.setServerName("JPN-AHORIUCHI2");
   serverSession.setServerPort(0);
   serverSession.setProjectName("MicroStrategy Tutorial");
   serverSession.setLogin("administrator");
   serverSession.setPassword("");
   serverSession.setApplicationType(EnumDSSXMLApplicationType.DssXmlApplicationCustomApp); 
   
   try { 

	    WebObjectSource wos = factory.getObjectSource();  
	    WebFolder oParentFolder = (WebFolder)wos.getObject(strParentFolderID,EnumDSSXMLObjectTypes.DssXmlTypeFolder);
			  
		        ReportBean rb = (ReportBean) BeanFactory.getInstance().newBean("ReportBean");
		        rb.setSessionInfo(serverSession);
		        rb.setObjectID(strReportID);
		        rb.setDefaultStateLevel(EnumWebStateLevel.TYPICAL_STATE_INFO);
		        
		        if (rb != null) {
		        	WebReportInstance wri = rb.getReportInstance();
		        	WebReportManipulation rptManip = wri.getReportManipulator();	
		        	
		        	ExpressionBean eb = rb.getReportFilterBean();
		        	WebExpression we = eb.getExpression();
		        	eb.setIsEditable(true);
		        	
		        	WebOperatorNode root = (WebOperatorNode) we.getRootNode();
		        	System.out.println("ope:"+ root.getFunction());
		        	root.setFunction(EnumDSSXMLFunction.DssXmlFunctionOr);
		        	System.out.println("ope2:"+ root.getFunction());
		        	System.out.println(we.getXML());
		        	eb.synchronize();

		        	WebReportInstance newInst = null;
		        	rptManip.setExecutionFlags(EnumDSSXMLExecutionFlags.DssXmlExecutionNoAction);
		        	rptManip.setResultFlags(EnumDSSXMLResultFlags.DssXmlResultGrid);
		        	newInst = rptManip.applyChanges();
		        	newInst.setAsync(false);
		        	int status = newInst.pollStatus();
		        	//...
		        	 newInst.setSaveAsFlags(EnumDSSXMLReportSaveAsFlags.DssXmlReportSaveAsOverWrite);
		        	 newInst.saveAs(oParentFolder,"R2");
		          }
	

	} catch (Exception ex) { 
		ex.printStackTrace(); 
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
