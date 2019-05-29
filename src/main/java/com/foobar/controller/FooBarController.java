package com.foobar.controller;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.Date;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.foobar.FooBarResponse;
import com.foobar.KafkaSender;
import com.foobar.bar.domain.Bar;
import com.foobar.bar.domain.BarDTO;
import com.foobar.bar.repo.BarRepository;
import com.foobar.foo.domain.Foo;
import com.foobar.foo.domain.FooDTO;
import com.foobar.foo.repo.FooRepository;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Component
@Api(tags = "Foobar-API")
@RequestMapping("/foobar")
public class FooBarController {

	@Autowired
	private FooRepository fooRepo;

	@Autowired
	private BarRepository barRepo;
	
    @Autowired
    KafkaSender kafkaSender;

	@ApiOperation(value = "Create a new foo", notes = "Create a new foo")
	@RequestMapping(value = "/addfoo", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public void createFoo(@RequestBody FooDTO addFoo) {
		Foo newFoo = new Foo();
		newFoo.setFoo(addFoo.getFoo());
		fooRepo.save(newFoo);
	}

	@ApiOperation(value = "Create a new bar", notes = "Create a new bar")
	@RequestMapping(value = "/addbar", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public void createBar(@RequestBody BarDTO addBar) {

		Bar newBar = new Bar();
		newBar.setBar(addBar.getBar());
		barRepo.save(newBar);

	}

	@ApiOperation(value = "View detail", notes = "View detail")
	@RequestMapping(value = "/viewDetails", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public FooBarResponse viewDetails(@RequestBody long id) {
		Foo foo = fooRepo.findById(id);
		Bar bar = barRepo.findById(id);
		String finalResponse = "db1data: " + foo.getFoo() + ";db2data: " + bar.getBar();

		
		  Document document = new Document(); try {
			PdfWriter.getInstance(document, new FileOutputStream("/home/terminator/Downloads/MultiDBPOC/sampleReport.pdf"));
			document.open(); 
			Image img = Image.getInstance("/home/terminator/Downloads/MultiDBPOC/download.jpeg");
		    //img.setAbsolutePosition(450f, 10f);
		    document.add(img);
		    document.add(new Paragraph("Policy No:" + foo.getId()));
		    document.add(new Paragraph("Customer Name:" + foo.getFoo() + "(from db1)"));
		    document.add(new Paragraph("Insurance Company:" + bar.getBar() + "(from db2)"));
		    document.close(); document.close();

		  
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
		  		 
		
		  kafkaSender.send("topic1", finalResponse + " Date: " + new Date()); 
		  //kafkaSender.send("topic2", finalResponse);
		return new FooBarResponse("Details", "Success", finalResponse);

	}
}
