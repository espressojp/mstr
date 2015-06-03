package jp.co.microstrategy.com.test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import com.microstrategy.utils.log.Level;
import com.microstrategy.utils.log.Logger;
import com.microstrategy.utils.log.LoggerConfigurator;
import com.microstrategy.web.app.addons.AbstractAppAddOn;
import com.microstrategy.web.app.beans.PageComponent;
import com.microstrategy.web.beans.RWBean;
import com.microstrategy.web.objects.rw.RWDefinition;
import com.microstrategy.web.objects.rw.RWInstance;
import com.microstrategy.web.objects.rw.RWLayoutSectionDef;
import com.microstrategy.web.objects.rw.RWManipulation;

public class HideLayout extends AbstractAppAddOn {
	
	String docID = "0D49A5E543718CD1C864089450657A32";



	public HideLayout() {
		// TODO Auto-generated constructor stub

	}

	@Override
	public String getAddOnDescription() {
		// TODO Auto-generated method stub
		return null;
	}
	
	   public void preCollectData(PageComponent page) 
	    {
	        try
	        {
	        	
	            RWBean rb = (RWBean)page.getChildByClass(RWBean.class);
	            System.out.println("docID: " + rb.getObjectID());
	            if (!rb.getObjectID().equals(docID)) return; 
	            
	            String user = page.getAppContext().getAppSessionManager().getUserName();
	            
	            int layoutindex;
	            if (user.equalsIgnoreCase("administrator")){
	            	layoutindex = 0;
	            }else if(user.equalsIgnoreCase("atest")){
	            	layoutindex = 1;
	            }else{
	            	return;
	            }
	            

	            
	            RWInstance rwi = rb.getRWInstance();
	            
	            rwi.setAsync(false);
	            rb.collectData();

	  		   RWDefinition def = rwi.getDefinition();    
			   RWManipulation rwMan = rwi.getRWManipulator(); 
			   System.out.println("Layout Count: "+def.getLayoutCount());
			   RWLayoutSectionDef lsd;
			   
			   for (int j=0;j<def.getLayoutCount();j++){
				   lsd = def.getLayout(j);
				   System.out.println("Layout: " + lsd.getName());
				   if (j == layoutindex){
					   rwMan.setCurrentLayout(j, true);
				   }else{
					   lsd.setVisible(false);
				   }
			   }
	        		
	        }
	        catch (Exception ex)
	        {
	        	System.out.println(ex);
	            // do nothing.
	        }
	    }

}

/*
class Log extends LoggerConfigurator 
{
	public static final Logger logger = new Log().createLogger();
	
	public static void Exception(Exception e) 
	{
		StackTraceElement[] sta = e.getStackTrace();
		
		String msg = e.toString();
		for(int i=0; i<sta.length; i++) msg += "\nat " + sta[i];
		
		logger.log(Level.INFO, msg);
	}
}
*/