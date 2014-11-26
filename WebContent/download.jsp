<%    
  	String filename = "testpdf1.pdf";
	String filepath = "C:\\";
	/*String OS = System.getProperty("os.name").toLowerCase();
	if (OS.indexOf("win") >= 0) {
		filepath.replace('/', '\\');
	}*/  
	response.setContentType("APPLICATION/OCTET-STREAM");   
	response.setHeader("Content-Disposition","attachment; filename=\"" + filename + "\"");   
	
	//out.print(filepath);
	
	java.io.FileInputStream fileInputStream=new java.io.FileInputStream(filepath + filename);  
	          
	int i;   
	while ((i=fileInputStream.read()) != -1) {  
	  out.write(i);   
	}   
	fileInputStream.close();   
%>  