import java.io.*;
import java.net.*;
import java.util.StringTokenizer;

final class HttpRequest implements Runnable
{
	final static String CRLF = "\r\n";
	Socket socket;
	
	// Constructor
	public HttpRequest(Socket socket) throws Exception 
	{
		this.socket = socket;
	}

	// Implement the run() method of the Runnable interface.
	public void run()
	{
		try {
			processRequest();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void processRequest() throws Exception
	{
		// Get a reference to the socket's input and output streams.
		InputStream is = socket.getInputStream();
		DataOutputStream os = new DataOutputStream(socket.getOutputStream());

		// Set up input stream filters.
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);

		// Get the request line of the HTTP request message.
		String requestLine = br.readLine();
		
		// Extract the filename from the request line.
		StringTokenizer tokens = new StringTokenizer(requestLine);
		String method = tokens.nextToken();
		String fileName = tokens.nextToken();

		fileName = fileName.substring(1, fileName.length());
		
		// Open the requested file.
		FileInputStream fis = null;
		boolean fileExists = true;
		try {
			fis = new FileInputStream(fileName);
		} catch (FileNotFoundException e) {
			fileExists = false;
		}
		
		// Construct the response message.
		String statusLine = null;
		String contentTypeLine = null;
		String entityBody = null;
		if (method.equals("GET"))
		{
			if (fileExists) {
				statusLine = tokens.nextToken() + " 200 OK" + CRLF;
				contentTypeLine = "Content-type: " + 
					contentType(fileName) + CRLF;
			} else {
				statusLine = tokens.nextToken() + " 404 Not Found" + CRLF;
				contentTypeLine = "Content-type: text/html" + CRLF;
				entityBody = "<HTML>" + 
					"<HEAD><TITLE>Not Found</TITLE></HEAD>" +
					"<BODY>Not Found</BODY></HTML>";
			}
		}
		else
		{
			statusLine = tokens.nextToken() + " 501 Not Implemented" + CRLF;
			contentTypeLine = "Content-type: text/html" + CRLF;
			entityBody = "<HTML>" + 
				"<HEAD><TITLE>Not Implemented</TITLE></HEAD>" +
				"<BODY>Not Implemented</BODY></HTML>";
		}
		
		// Send the status line.
		os.writeBytes(statusLine);

		// Send the content type line.
		os.writeBytes(contentTypeLine);

		// Send a blank line to indicate the end of the header lines.
		os.writeBytes(CRLF);
		
		// Send the entity body.
		if (fileExists && method.equals("GET"))	{
			sendBytes(fis, os);
			fis.close();
		} else {
			os.writeBytes(entityBody);
		}
		
		// Close streams and socket.
		os.close();
		br.close();
		socket.close();
	}
	
	private void sendBytes(FileInputStream fis, OutputStream os) throws Exception
	{
	   // Construct a 1K buffer to hold bytes on their way to the socket.
	   byte[] buffer = new byte[1024];
	   int bytes = 0;

	   // Copy requested file into the socket's output stream.
	   while((bytes = fis.read(buffer)) != -1 ) {
	      os.write(buffer, 0, bytes);
	   }
	}
	
	private String contentType(String fileName)
	{
		if (fileName.endsWith(".htm") || fileName.endsWith(".html"))
			return "text/html";
		if (fileName.endsWith(".gif"))
			return "image/gif";
		if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg"))
			return "image/jpeg";
		if (fileName.endsWith(".txt"))
			return "text/plain";
		return "application/octet-stream";
	}
}