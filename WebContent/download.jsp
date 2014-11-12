<%    
  String filename = "testpdf1.pdf";   
  String filepath = "/Users/vamhan/Downloads/apache-tomcat-7.0.52/webapps/ROOT/Nutrition/WebContent/";   
  response.setContentType("APPLICATION/OCTET-STREAM");   
  response.setHeader("Content-Disposition","attachment; filename=\"" + filename + "\"");   
  
  java.io.FileInputStream fileInputStream=new java.io.FileInputStream(filepath + filename);  
            
  int i;   
  while ((i=fileInputStream.read()) != -1) {  
    out.write(i);   
  }   
  fileInputStream.close();   
%>  