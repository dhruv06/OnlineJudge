package com.finalyear.saas.controller;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.ws.rs.Produces;

//import javax.servlet.ServletContext;
//import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;
import com.finalyear.saas.model.CompileData;
import com.finalyear.saas.model.ResponseDataScilab;
import com.finalyear.saas.util.IOUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
public class SaaSApiControllerMatlab {
	
	
	@ResponseBody
	@ApiOperation(value = "Adds a File")
	@RequestMapping(path = "/api/scilab", method = RequestMethod.POST,produces={ "application/json" })
	public  ResponseDataScilab uploadFile(@RequestBody CompileData c) throws IOException, AmazonClientException, InterruptedException {
		
		
		System.out.println(c.getLanguage() + "Language        " + c.getCode() + "code          " + c.getInput()
				+ "input        " + c.getType() + "type");

		String lang = c.getLanguage();
		String code = c.getCode();
		String ip = c.getInput();
		String uname=c.getUname();
		int type = c.getType();
		
		
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

		pbin.command("bash", "-c",
				"cp /home/ubuntu/shell_scripts/shell_scilab.sh /home/ubuntu/Users/"+c.getUname()+"/");


		Process pin = pbin.start();
		BufferedReader brin = new BufferedReader(new InputStreamReader(pin.getInputStream()));
		String linein;
		while ((linein = brin.readLine()) != null) {
			System.out.println(linein);// (line) ;
		}



		ProcessBuilder pbin1 = new ProcessBuilder();
		//pb.command("bash" , "-c" , "cd usercode");

		pbin1.command("bash", "-c",
				"chmod +x /home/ubuntu/Users/"+c.getUname()+"/shell_scilab.sh");

		Process pin1 = pbin1.start();
		BufferedReader brin1 = new BufferedReader(new InputStreamReader(pin1.getInputStream()));
		String linein1;
		while ((linein1 = brin1.readLine()) != null) {
			System.out.println(linein1);// (line) ;
		}




		
		if(type==1)
		{
			code=code+"xs2png(0,'foo.png');xs2png(gcf(),'foo.png');";
		}
if (lang.equals("Scilab")) {

	final String FILE_TO1 = "/home/ubuntu/Users/"+c.getUname()+"/f1.sce";
	InputStream fileIS1 = new ByteArrayInputStream(code.getBytes(StandardCharsets.UTF_8));

	File file1 = new File(FILE_TO1);
	OutputStream os1 = null;
	os1 = new FileOutputStream(file1);
	IOUtils.copy(fileIS1, os1);


	ProcessBuilder pb = new ProcessBuilder();
	// pb.command("bash" , "-c" , "cd usercode");
	pb.command("bash", "-c",
			"sudo docker run -v /home/ubuntu/Users/"+c.getUname()+":/home/code slab /bin/bash -c 'cd home/code;./shell_scilab.sh' ");

	Process p = pb.start();
	BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
	String line;
	while ((line = br.readLine()) != null) {
		System.out.println(line);// (line) ;
	}
}

		String urlst = "";
		if(type==1)
		{
	    String bucketName = "saas12345";
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		
	       String fileObjKeyName = dateFormat.format(date)+".png";
	        String fileName = "foo.png";
	        
	    URL url1 = null;
	        try {
	        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
    			  .withRegion(Regions.US_EAST_1)
    			  .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials("AKIAIVQ7MMJWB3USQPSQ","OBYJr8XVqi+pHb2J5hVrWGLOmML4NHDdUbICTIJH")))
                  .build();
	        
	        
	        TransferManager tm = TransferManagerBuilder.standard()
                    .withS3Client(s3Client)
                    .build();
	        
	        File initialFile = new File("/home/ubuntu/Users/"+c.getUname()+"/"+fileName);
	        InputStream targetStream = new FileInputStream(initialFile);
	        PutObjectRequest request = new PutObjectRequest(bucketName, fileObjKeyName, targetStream,null);
	        ObjectMetadata metadata = new ObjectMetadata();
            
            request.setMetadata(metadata);
         request.withCannedAcl(CannedAccessControlList.PublicRead);
            Upload upload = tm.upload(request);
            
            upload.waitForCompletion();
ProcessBuilder pb = new ProcessBuilder();
	// pb.command("bash" , "-c" , "cd usercode");
	pb.command("bash", "-c",
			"sudo cp ~/noimage.png ~/Users/"+c.getUname()+"/foo.png");

	Process p = pb.start();
	BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
	String line;
	while ((line = br.readLine()) != null) {
		System.out.println(line);// (line) ;
	}
            
           url1= s3Client.getUrl("saas12345",fileObjKeyName);
            
            
         
	        }
	        catch(AmazonServiceException e) {
                // The call was transmitted successfully, but Amazon S3 couldn't process 
                // it, so it returned an error response.
                e.printStackTrace();
            }
            catch(SdkClientException e) {
                // Amazon S3 couldn't be contacted for a response, or the client
                // couldn't parse the response from Amazon S3.
                e.printStackTrace();
            }
	        
	         urlst=url1.toString();
		}
ResponseDataScilab rs=new ResponseDataScilab();

rs.setUrl(urlst);
String errorlog = "", successlog = "", line = "";

File newFile1 = new File("/home/ubuntu/Users/"+c.getUname()+"/errors");
BufferedReader bufferedReader1 = new BufferedReader(new InputStreamReader(new FileInputStream(newFile1)));

while ((line = bufferedReader1.readLine()) != null) {

	errorlog += line + "<br>";
}
bufferedReader1.close();
rs.setErrorlog(errorlog);



File newFile = new File("/home/ubuntu/Users/"+c.getUname()+"/logfile.txt");
BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(newFile)));

while ((line = bufferedReader.readLine()) != null) {

	successlog += line + "<br>";
}
successlog=successlog+errorlog;
bufferedReader.close();
rs.setSuccesslog(successlog);



return rs;

	}
}

