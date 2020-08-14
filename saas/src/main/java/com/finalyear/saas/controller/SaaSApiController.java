package com.finalyear.saas.controller;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.finalyear.saas.model.CheckitInputData;
import com.finalyear.saas.model.CheckitOutputData;
import com.finalyear.saas.model.CompileData;
import com.finalyear.saas.model.ContestInputData;
import com.finalyear.saas.model.ContestOutputData;
import com.finalyear.saas.model.CreateContestData;
import com.finalyear.saas.model.QuestionData;
import com.finalyear.saas.model.QuestionInputData;
import com.finalyear.saas.model.QuestionOutputData;
import com.finalyear.saas.model.ResponseData;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
public class SaaSApiController {
	
	@ApiOperation(value = "Create Contest")
	@RequestMapping(path = "/api/addcontest1", method = RequestMethod.POST)
	public String createContest(@RequestBody CreateContestData c) throws IOException 
	{
		
		
		 File file = new File("/home/ubuntu/contests/"+c.getCname()+"_by_"+c.getUname());
		 
	      //Creating the directory
	      boolean bool = file.mkdir();
	      
	      System.out.println(bool);
	      
	      File myObj = new File("/home/ubuntu/contests/"+c.getCname()+"_by_"+c.getUname()+"/description.txt");
	      myObj.createNewFile();
	      
	      FileWriter myWriter = new FileWriter("/home/ubuntu/contests/"+c.getCname()+"_by_"+c.getUname()+"/description.txt");
	      myWriter.write(c.getCdesc());
	      myWriter.close();
	      
		return "created successfully";
	}
	
	
	
	@ApiOperation(value = "Add Question")
	@RequestMapping(path = "/api/addquestion1", method = RequestMethod.POST)
	public String addquestion(@RequestBody QuestionData c) throws IOException 
	{
		System.out.println(c.getUname()+c.getCname()+c.getQname()+c.getQdesc()+c.getSinput()+c.getSoutput());
		for(int i=0;i<c.getInput().length;i++)
		{
			System.out.println(c.getInput()[i]);
			System.out.println(c.getOutput()[i]);
		}
	
		 File file = new File("/home/ubuntu/contests/"+c.getCname()+"_by_"+c.getUname()+"/"+c.getQname());
		 
	      //Creating the directory
	      boolean bool = file.mkdir();
	      System.out.println(bool);
	      
	      
	      //writing description
	      File myObj = new File("/home/ubuntu/contests/"+c.getCname()+"_by_"+c.getUname()+"/"+c.getQname()+"/description.txt");
	      myObj.createNewFile();
	      
	      FileWriter myWriter = new FileWriter("/home/ubuntu/contests/"+c.getCname()+"_by_"+c.getUname()+"/"+c.getQname()+"/description.txt");
	      myWriter.write(c.getQdesc());
	      myWriter.close();
	      
	      //writing sinput n soutput
	      File myObj1 = new File("/home/ubuntu/contests/"+c.getCname()+"_by_"+c.getUname()+"/"+c.getQname()+"/sinput.txt");
	      myObj1.createNewFile();
	      
	      FileWriter myWriter1 = new FileWriter("/home/ubuntu/contests/"+c.getCname()+"_by_"+c.getUname()+"/"+c.getQname()+"/sinput.txt");
	      myWriter1.write(c.getSinput());
	      myWriter1.close();
	      
	      
	      File myObj2 = new File("/home/ubuntu/contests/"+c.getCname()+"_by_"+c.getUname()+"/"+c.getQname()+"/soutput.txt");
	      myObj2.createNewFile();
	      
	      FileWriter myWriter2 = new FileWriter("/home/ubuntu/contests/"+c.getCname()+"_by_"+c.getUname()+"/"+c.getQname()+"/soutput.txt");
	      myWriter2.write(c.getSoutput());
	      myWriter2.close();
	      
	      
	      
	      File myObj7 = new File("/home/ubuntu/contests/"+c.getCname()+"_by_"+c.getUname()+"/"+c.getQname()+"/marks.txt");
	      myObj7.createNewFile();
	      for(int i=1;i<=c.getInput().length;i++)
	      {
	    	  FileWriter myWriter7 = new FileWriter("/home/ubuntu/contests/"+c.getCname()+"_by_"+c.getUname()+"/"+c.getQname()+"/marks.txt",true);
		      
	    	  myWriter7.append(c.getMarks()[i-1]);
	    	  myWriter7.append('\n');
		      myWriter7.close();
	    	  
	    	  File myObj3 = new File("/home/ubuntu/contests/"+c.getCname()+"_by_"+c.getUname()+"/"+c.getQname()+"/input"+i+".txt");
		      myObj3.createNewFile();
		      
		      FileWriter myWriter3 = new FileWriter("/home/ubuntu/contests/"+c.getCname()+"_by_"+c.getUname()+"/"+c.getQname()+"/input"+i+".txt");
		      myWriter3.write(c.getInput()[i-1]);
		      myWriter3.close();
		      
		      
		      File myObj4 = new File("/home/ubuntu/contests/"+c.getCname()+"_by_"+c.getUname()+"/"+c.getQname()+"/output"+i+".txt");
		      myObj4.createNewFile();
		      
		      FileWriter myWriter4 = new FileWriter("/home/ubuntu/contests/"+c.getCname()+"_by_"+c.getUname()+"/"+c.getQname()+"/output"+i+".txt");
		      myWriter4.write(c.getOutput()[i-1]);
		      myWriter4.close();
	      }
	      
	      
		
		return "added";
	}
	
	
	
	@ApiOperation(value = "get contest list")
	@RequestMapping(path = "/api/getcontestlist", method = RequestMethod.POST)
	public ContestOutputData getcontestlists(@RequestBody ContestInputData c) 
	{
		ContestOutputData op=new ContestOutputData();
		File file1 = new File("/home/ubuntu/contests");
		String[] names = file1.list();
		String[] question = new String[50];
		int cnt=0;
		for(String name : names)
		{
		    if (new File("/home/ubuntu/contests/" + name).isDirectory())
		    {
		        question[cnt]=name;
		    	cnt++;
		    }
		}
		op.setTitles(question);
		
		op.setCdesc("dummy");
		return op;
	}
	
	
	
	@ApiOperation(value = "get contest details")
	@RequestMapping(path = "/api/getcontestdetail", method = RequestMethod.POST)
	public ContestOutputData getcontestdatails(@RequestBody ContestInputData c) throws IOException
	{
		ContestOutputData op=new ContestOutputData();
		File file = new File("/home/ubuntu/contests/"+c.getCname()+"/description.txt");
		FileInputStream fis = new FileInputStream(file);
		byte[] data = new byte[(int) file.length()];
		fis.read(data);
		fis.close();
		String str = new String(data, "UTF-8");
		
		File file1 = new File("/home/ubuntu/contests/"+c.getCname());
		String[] names = file1.list();
		String[] question = new String[50];
		int cnt=0;
		for(String name : names)
		{
		    if (new File("/home/ubuntu/contests/"+c.getCname()+"/" + name).isDirectory())
		    {
		        question[cnt]=name;
		    	cnt++;
		    }
		}
		op.setTitles(question);
		
		op.setCdesc(str);
		return op;
	}
	
	
	
	@ApiOperation(value = "get question details")
	@RequestMapping(path = "/api/getquestiondetail", method = RequestMethod.POST)
	public QuestionOutputData getquestiondatails(@RequestBody QuestionInputData c) throws IOException
	{
		File file = new File("/home/ubuntu/contests/"+c.getCname()+"/"+c.getQname()+"/description.txt");
		FileInputStream fis = new FileInputStream(file);
		byte[] data = new byte[(int) file.length()];
		fis.read(data);
		fis.close();
		String desc = new String(data, "UTF-8");
		
		
		File file1 = new File("/home/ubuntu/contests/"+c.getCname()+"/"+c.getQname()+"/sinput.txt");
		FileInputStream fis1 = new FileInputStream(file1);
		byte[] data1 = new byte[(int) file1.length()];
		fis1.read(data1);
		fis1.close();
		String sip = new String(data1, "UTF-8");
		
		
		File file2 = new File("/home/ubuntu/contests/"+c.getCname()+"/"+c.getQname()+"/soutput.txt");
		FileInputStream fis2 = new FileInputStream(file2);
		byte[] data2 = new byte[(int) file2.length()];
		fis2.read(data2);
		fis2.close();
		String sop = new String(data2, "UTF-8");
		
		QuestionOutputData qod=new QuestionOutputData();
		qod.setQdesc(desc);
		qod.setSinput(sip);
		qod.setSoutput(sop);
		return qod;
		
	}

	
	@ApiOperation(value = "check test cases")
	@RequestMapping(path = "/api/checkit", method = RequestMethod.POST)
	public CheckitOutputData checkit1(@RequestBody CheckitInputData c) throws IOException
	{
		
		int count=1;
		
		String[] result = new String[50];
		String[] marks=new String[50];
		
		 File markfile = new File("/home/ubuntu/contests/"+c.getCname()+"/"+c.getQname()+"/marks.txt");
		 FileReader mfr = new FileReader(markfile);
		 BufferedReader mr = new BufferedReader(mfr);
		while(true)
		{
		boolean check = new File("/home/ubuntu/contests/"+c.getCname()+"/"+c.getQname()+"/input"+count+".txt").exists();
		
		if(check==false)
		{
			break;
		}
		
		CompileData data=new CompileData();
		data.setUname(c.getUname());
		data.setCode(c.getCode());
		data.setLanguage(c.getLanguage());
		data.setType(0);
		
		File file2 = new File("/home/ubuntu/contests/"+c.getCname()+"/"+c.getQname()+"/input"+count+".txt");
		FileInputStream fis2 = new FileInputStream(file2);
		byte[] data2 = new byte[(int) file2.length()];
		fis2.read(data2);
		fis2.close();
		String sop = new String(data2, "UTF-8");
		data.setInput(sop);
		uploadFile(data);
		

		//compare with true output
		
        File f1 = new File("/home/ubuntu/Users/temp/logfile.txt");// OUTFILE
        File f2 = new File("/home/ubuntu/contests/"+c.getCname()+"/"+c.getQname()+"/output"+count+".txt");

        FileReader fR1 = new FileReader(f1);
        FileReader fR2 = new FileReader(f2);

        BufferedReader reader1 = new BufferedReader(fR1);
        BufferedReader reader2 = new BufferedReader(fR2);

        String line1 = null;
        String line2 = null;
        int flag = 1;
        while (true) {
        	
        	if(flag==0)
        		break;
        	
        	line1 = reader1.readLine();
        	line2 = reader2.readLine();
        	
        	if(line1==null && line2==null)
        	{
        		break;
        	}
        	if(line1==null && line2!=null)
        	{
        		flag=0;
        		break;
        	}
        	if(line1!=null && line2==null)
        	{
        		flag=0;
        		break;
        	}
        	
        	
        	
            if (!line1.equals(line2))
                flag = 0;
        }
        reader1.close();
        reader2.close();

	String currmarks=mr.readLine();		
        if(flag==0)
        {
        	result[count-1]="fail";
        	marks[count-1]="0/"+currmarks;
        }
        else if(flag==1)
        {
        	result[count-1]="pass";
        	marks[count-1]=currmarks+"/"+currmarks;
        }
		
		count++;
		}
		
		CheckitOutputData op=new CheckitOutputData();
		op.setResult(result);
		op.setMarks(marks);
		return op;
		
		
		
	
	}
	
	
	
	
	@ApiOperation(value = "Adds a File")
	@RequestMapping(path = "/api/upload", method = RequestMethod.POST)
	public ResponseData uploadFile(@RequestBody CompileData c) throws IOException {
		System.out.println(c.getLanguage() + "Language        " + c.getCode() + "code          " + c.getInput()
				+ "input        " + c.getType() + "type");

		String lang = c.getLanguage();
		String code = c.getCode();
		String ip = c.getInput();
		int type = c.getType();
if(ip==null)
{
	ip="";
}
File userdir = new File("/home/ubuntu/Users/"+c.getUname());

//Creating the directory
boolean bool = userdir.mkdir();
System.out.println(bool);

final String logfile = "/home/ubuntu/Users/"+c.getUname()+"/logfile.txt";
InputStream logfileIS = new ByteArrayInputStream("dummy".getBytes(StandardCharsets.UTF_8));

File logf = new File(logfile);
OutputStream logfOS = null;
logfOS = new FileOutputStream(logf);
IOUtils.copy(logfileIS, logfOS);


final String errorfile = "/home/ubuntu/Users/"+c.getUname()+"/errors";
InputStream errorsIS = new ByteArrayInputStream("dummy".getBytes(StandardCharsets.UTF_8));

File errorf= new File(errorfile);
OutputStream errorOS = null;
errorOS = new FileOutputStream(errorf);
IOUtils.copy(errorsIS, errorOS);


ProcessBuilder pbin = new ProcessBuilder();
// pb.command("bash" , "-c" , "cd usercode");
if(c.getLanguage().equals("C"))
{
pbin.command("bash", "-c",
		"cp /home/ubuntu/shell_scripts/shell_c.sh /home/ubuntu/Users/"+c.getUname()+"/");
}
else if(c.getLanguage().equals("C++"))
{
pbin.command("bash", "-c",
		"cp /home/ubuntu/shell_scripts/shell_cpp.sh /home/ubuntu/Users/"+c.getUname()+"/");
}
else if(c.getLanguage().equals("Java"))
{
pbin.command("bash", "-c",
		"cp /home/ubuntu/shell_scripts/shell_java.sh /home/ubuntu/Users/"+c.getUname()+"/");
}
else if(c.getLanguage().equals("Python"))
{
pbin.command("bash", "-c",
		"cp /home/ubuntu/shell_scripts/shell_python.sh /home/ubuntu/Users/"+c.getUname()+"/");
}

Process pin = pbin.start();
BufferedReader brin = new BufferedReader(new InputStreamReader(pin.getInputStream()));
String linein;
while ((linein = brin.readLine()) != null) {
	System.out.println(linein);// (line) ;
}



ProcessBuilder pbin1 = new ProcessBuilder();
//pb.command("bash" , "-c" , "cd usercode");
if(c.getLanguage().equals("C"))
{
pbin1.command("bash", "-c",
		"chmod +x /home/ubuntu/Users/"+c.getUname()+"/shell_c.sh");
}
else if(c.getLanguage().equals("C++"))
{
pbin1.command("bash", "-c",
		"chmod +x /home/ubuntu/Users/"+c.getUname()+"/shell_cpp.sh");
}
else if(c.getLanguage().equals("Java"))
{
pbin1.command("bash", "-c",
		"chmod +x /home/ubuntu/Users/"+c.getUname()+"/shell_java.sh");
}
else if(c.getLanguage().equals("Python"))
{
pbin1.command("bash", "-c",
		"chmod +x /home/ubuntu/Users/"+c.getUname()+"/shell_python.sh");
}

Process pin1 = pbin1.start();
BufferedReader brin1 = new BufferedReader(new InputStreamReader(pin1.getInputStream()));
String linein1;
while ((linein1 = brin1.readLine()) != null) {
	System.out.println(linein1);// (line) ;
}




		if (lang.equals("C")) {

			final String FILE_TO1 = "/home/ubuntu/Users/"+c.getUname()+"/myfile.c";
			InputStream fileIS1 = new ByteArrayInputStream(code.getBytes(StandardCharsets.UTF_8));

			File file1 = new File(FILE_TO1);
			OutputStream os1 = null;
			os1 = new FileOutputStream(file1);
			IOUtils.copy(fileIS1, os1);

			final String FILE_TO2 = "/home/ubuntu/Users/"+c.getUname()+"/stdin.txt";
			InputStream fileIS2 = new ByteArrayInputStream(ip.getBytes(StandardCharsets.UTF_8));

			File file2 = new File(FILE_TO2);
			OutputStream os2 = null;
			os2 = new FileOutputStream(file2);
			IOUtils.copy(fileIS2, os2);

			ProcessBuilder pb = new ProcessBuilder();
			// pb.command("bash" , "-c" , "cd usercode");
			pb.command("bash", "-c",
					"sudo docker run -v /home/ubuntu/Users/"+c.getUname()+":/home/code gcc /bin/bash -c 'cd home/code;./shell_c.sh' ");

			Process p = pb.start();
			BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line;
			while ((line = br.readLine()) != null) {
				System.out.println(line);// (line) ;
			}

		} else if (lang.equals("C++")) {

			final String FILE_TO1 = "/home/ubuntu/Users/"+c.getUname()+"/myfile.cpp";
			InputStream fileIS1 = new ByteArrayInputStream(code.getBytes(StandardCharsets.UTF_8));

			File file1 = new File(FILE_TO1);
			OutputStream os1 = null;
			os1 = new FileOutputStream(file1);
			IOUtils.copy(fileIS1, os1);

			final String FILE_TO2 = "/home/ubuntu/Users/"+c.getUname()+"/stdin.txt";
			InputStream fileIS2 = new ByteArrayInputStream(ip.getBytes(StandardCharsets.UTF_8));

			File file2 = new File(FILE_TO2);
			OutputStream os2 = null;
			os2 = new FileOutputStream(file2);
			IOUtils.copy(fileIS2, os2);

			ProcessBuilder pb = new ProcessBuilder();
			// pb.command("bash" , "-c" , "cd usercode");
			pb.command("bash", "-c",
					"sudo docker run -v /home/ubuntu/Users/"+c.getUname()+":/home/code gcc /bin/bash -c 'cd home/code;./shell_cpp.sh' ");

			Process p = pb.start();
			BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line;
			while ((line = br.readLine()) != null) {
				System.out.println(line);// (line) ;
			}

		} else if (lang.equals("Java")) {

			final String FILE_TO1 = "/home/ubuntu/Users/"+c.getUname()+"/Sample.java";
			InputStream fileIS1 = new ByteArrayInputStream(code.getBytes(StandardCharsets.UTF_8));

			File file1 = new File(FILE_TO1);
			OutputStream os1 = null;
			os1 = new FileOutputStream(file1);
			IOUtils.copy(fileIS1, os1);

			final String FILE_TO2 = "/home/ubuntu/Users/"+c.getUname()+"/stdin.txt";
			InputStream fileIS2 = new ByteArrayInputStream(ip.getBytes(StandardCharsets.UTF_8));

			File file2 = new File(FILE_TO2);
			OutputStream os2 = null;
			os2 = new FileOutputStream(file2);
			IOUtils.copy(fileIS2, os2);

			ProcessBuilder pb = new ProcessBuilder();
			// pb.command("bash" , "-c" , "cd usercode");
			pb.command("bash", "-c",
					"sudo docker run -v /home/ubuntu/Users/"+c.getUname()+":/home/code openjdk /bin/bash -c 'cd home/code;./shell_java.sh' ");

			Process p = pb.start();
			BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line;
			while ((line = br.readLine()) != null) {
				System.out.println(line);// (line) ;
			}
		}else if (lang.equals("Python")) {

final String FILE_TO1 = "/home/ubuntu/Users/"+c.getUname()+"/myfile.py";
InputStream fileIS1 = new ByteArrayInputStream(code.getBytes(StandardCharsets.UTF_8));

File file1 = new File(FILE_TO1);
OutputStream os1 = null;
os1 = new FileOutputStream(file1);
IOUtils.copy(fileIS1, os1);

final String FILE_TO2 = "/home/ubuntu/Users/"+c.getUname()+"/stdin.txt";
InputStream fileIS2 = new ByteArrayInputStream(ip.getBytes(StandardCharsets.UTF_8));

File file2 = new File(FILE_TO2);
OutputStream os2 = null;
os2 = new FileOutputStream(file2);
IOUtils.copy(fileIS2, os2);

ProcessBuilder pb = new ProcessBuilder();
// pb.command("bash" , "-c" , "cd usercode");
pb.command("bash", "-c",
"sudo docker run -v /home/ubuntu/Users/"+c.getUname()+":/home/code python /bin/bash -c 'cd home/code;./shell_python.sh' ");

Process p = pb.start();
BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
String line;
while ((line = br.readLine()) != null) {
System.out.println(line);// (line) ;
}
}


		ResponseData responseData = new ResponseData();
		String response = "<div class=\"container\">" + " <div class=\"panel-group\">\n"
				+ "    <div class=\"panel panel-warning\">\n"
				+ "      <div class=\"panel-heading\">Compilation log</div><div class=\"panel-body\">";
		String errorlog = "", successlog = "", line = "";

		File newFile1 = new File("/home/ubuntu/Users/"+c.getUname()+"/errors");
		BufferedReader bufferedReader1 = new BufferedReader(new InputStreamReader(new FileInputStream(newFile1)));

		while ((line = bufferedReader1.readLine()) != null) {

			errorlog += line + "<br>";
		}
		bufferedReader1.close();
		
		
		responseData.setErrorlog(errorlog);
		response += "</div></div>" + "    <div class=\"panel panel-success\">"
				+ "      <div class=\"panel-heading\">Output</div>\n" + "      <div class=\"panel-body\">" + "";
		File newFile = new File("/home/ubuntu/Users/"+c.getUname()+"/logfile.txt");
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(newFile)));

		while ((line = bufferedReader.readLine()) != null) {

			successlog += line + "<br>";
		}
		response += "</div></div></div>";

		bufferedReader.close();
		
		
		responseData.setSuccesslog(successlog);
		return responseData;

	}
}
