package Controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.StringReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.html.simpleparser.HTMLWorker; // deprecated
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

public class HtmlToPDF {
	// itextpdf-5.4.1.jar http://sourceforge.net/projects/itext/files/iText/
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		String html = request.getParameter("html");
		try {
			Document document = new Document(PageSize.LETTER);
			PdfWriter writer = PdfWriter.getInstance(document,
					new FileOutputStream("testpdf1.pdf"));
			document.open();
			/*
			 * document.addAuthor("Real Gagnon");
			 * document.addCreator("Real's HowTo");
			 * document.addSubject("Thanks for your support");
			 * document.addCreationDate();
			 * document.addTitle("Please read this");
			 */

			HTMLWorker htmlWorker = new HTMLWorker(document);
			/*String str = "<html><head></head><body>"
					+ "<a href='http://www.rgagnon.com/howto.html'><b>Real's HowTo</b></a>"
					+ "<h1>Show your support</h1>"
					+ "<p>It DOES cost a lot to produce this site - in ISP storage and transfer fees, "
					+ "in personal hardware and software costs to set up test environments, and above all,"
					+ "the huge amounts of time it takes for one person to design and write the actual content."
					+ "<p>If you feel that effort has been useful to you, perhaps you will consider giving something back?"
					+ "<p>Donate using PayPal to real@rgagnon.com."
					+ "<p>Contributions via PayPal are accepted in any amount "
					+ "<P><br><table border='1'><tr><td>Java HowTo<tr>"
					+ "<td bgcolor='red'>Javascript HowTo<tr><td>Powerbuilder HowTo</table>"
					+ "</body></html>";*/

	        // step 4
	        XMLWorkerHelper.getInstance().parseXHtml(writer, document,
	                new FileInputStream("summary.jsp")); 
	        //step 5
	         document.close();
	 
	        System.out.println( "PDF Created!" );
			htmlWorker.parse(new StringReader(html));
			document.close();
			System.out.println("Done");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}