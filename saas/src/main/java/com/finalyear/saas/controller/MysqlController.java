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
import com.finalyear.saas.model.CompileMysql;
import com.finalyear.saas.model.ResponseDataScilab;
import com.finalyear.saas.model.ResponseMysql;
import com.finalyear.saas.util.IOUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
public class MysqlController {
	
	
	@ResponseBody
	@ApiOperation(value = "Adds a File")
	@RequestMapping(path = "/api/mysql", method = RequestMethod.POST,produces={ "application/json" })
	public  ResponseMysql uploadFile(@RequestBody CompileMysql c) throws IOException, AmazonClientException, InterruptedException {
		
		

		String code = c.getCode();
		String uname=c.getUname();
		
		
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
				"cp /home/ubuntu/shell_scripts/shell_mysql.sh /home/ubuntu/Users/"+c.getUname()+"/");


		Process pin = pbin.start();
		BufferedReader brin = new BufferedReader(new InputStreamReader(pin.getInputStream()));
		String linein;
		while ((linein = brin.readLine()) != null) {
			System.out.println(linein);// (line) ;
		}



		ProcessBuilder pbin1 = new ProcessBuilder();
		//pb.command("bash" , "-c" , "cd usercode");

		pbin1.command("bash", "-c",
				"chmod +x /home/ubuntu/Users/"+c.getUname()+"/shell_mysql.sh");

		Process pin1 = pbin1.start();
		BufferedReader brin1 = new BufferedReader(new InputStreamReader(pin1.getInputStream()));
		String linein1;
		while ((linein1 = brin1.readLine()) != null) {
			System.out.println(linein1);// (line) ;
		}



	final String FILE_TO1 = "/home/ubuntu/Users/"+c.getUname()+"/index.sql";
	InputStream fileIS1 = new ByteArrayInputStream(code.getBytes(StandardCharsets.UTF_8));

	File file1 = new File(FILE_TO1);
	OutputStream os1 = null;
	os1 = new FileOutputStream(file1);
	IOUtils.copy(fileIS1, os1);

   System.out.println("shell is running");
	ProcessBuilder pb = new ProcessBuilder();
	// pb.command("bash" , "-c" , "cd usercode");
	pb.command("bash", "-c",
			"./home/ubuntu/Users/" + c.getUname() + "/shell_mysql.sh");
	String temp1="./home/ubuntu/Users/" + c.getUname() + "/shell_mysql.sh " + c.getUname();
	String temp="'cd /home/ubuntu/Users/" + c.getUname() + ";./shell_mysql.sh'";

	Process p = pb.start();
	BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
	String line;
	while ((line = br.readLine()) != null) {
		System.out.println(line);// (line) ;
	}


		
ResponseMysql rs=new ResponseMysql();

String errorlog = "", successlog = "";
line="";

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

bufferedReader.close();
rs.setSuccesslog(successlog);


return rs;

	}
}

