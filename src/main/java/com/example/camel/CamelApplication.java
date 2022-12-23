package com.example.camel;

import java.util.Arrays;

import org.apache.camel.Exchange;
import org.apache.camel.Header;
import org.apache.camel.builder.RouteBuilder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CamelApplication extends RouteBuilder{

	public static void main(String[] args) {
		SpringApplication.run(CamelApplication.class, args);
	}

	@Override
	public void configure() throws Exception {
		// from("file:///C:/Users/Amruta/OneDrive/Desktop/ApacheCamel/from?noop=true")
		// .log("${headers}")
		// .log("${body}")
		// .to("file:///C:/Users/Amruta/OneDrive/Desktop/ApacheCamel/to");

		System.out.println("Start+++++++");
		// moving a file with specific name
		// moveSpecificFileWithName("myDoc");

		// moving a file which containce specific content
		// moveSpecificFileWithBody("Hello");

		// processing a file as space is replace with quama
		 fileProcess();

		// processsing a multiple files
			//multiFileProcessor();

		System.out.println("End+++++++");


		
	}
	public void moveSpecificFileWithName(String name){
		from("file:///C:/Users/Amruta/OneDrive/Desktop/ApacheCamel/from?noop=true")
		.filter(header(Exchange.FILE_NAME).startsWith(name))
		.to("file:///C:/Users/Amruta/OneDrive/Desktop/ApacheCamel/to");
	}

	public void moveSpecificFileWithBody(String content){
		from("file:///C:/Users/Amruta/OneDrive/Desktop/ApacheCamel/from?noop=true")
		.filter(body().startsWith(content))
		.to("file:///C:/Users/Amruta/OneDrive/Desktop/ApacheCamel/to");
	}

	public void fileProcess() {
		from("file:///C:/Users/Amruta/OneDrive/Desktop/ApacheCamel/fileProcessing?noop=true").process(p -> {
			String body = p.getIn().getBody(String.class);
			StringBuilder sb = new StringBuilder();
			Arrays.stream(body.split(" ")).forEach(s -> {
				sb.append(s + ",");
			});

			p.getIn().setBody(sb);
		}).to("file:///C:/Users/Amruta/OneDrive/Desktop/ApacheCamel/to?fileName=records.csv");
	}

	// public void multiFileProcessor() {
	// 	from("file:///C:/Users/Amruta/OneDrive/Desktop/ApacheCamel/multiFileProcessing?noop=true").unmarshal().csv().split(body().tokenize(",")).choice()
	// 			.when(body().contains("Closed")).to("file:///C:/Users/Amruta/OneDrive/Desktop/ApacheCamel/to?fileName=close.csv")
	// 			.when(body().contains("Pending")).to("file:///C:/Users/Amruta/OneDrive/Desktop/ApacheCamel/to?fileName=Pending.csv")
	// 			.when(body().contains("Interest")).to("file:///C:/Users/Amruta/OneDrive/Desktop/ApacheCamel/to?fileName=Interest.csv");

	// }

}
