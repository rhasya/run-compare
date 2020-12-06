package com.example.myproject.compare;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CompareRestController {
	
	private static final Logger log = LoggerFactory.getLogger(CompareRestController.class);
	
	@PostMapping("/check")
	public Result check(@RequestBody Code code) {
		// save to temp folder
		try (PrintWriter pw = new PrintWriter("./tmp/Solution.java")) {
			pw.write(code.getCode());
			log.info("save success");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		// compile and run and get result
		StringBuilder sb = new StringBuilder();
		long dur = 0;
		Runtime runtime = Runtime.getRuntime();
		try {
			Process proc = runtime.exec("javac ./tmp/Solution.java");
			BufferedReader br = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
			StringBuilder sb1 = new StringBuilder();
			br.lines().forEach((line) -> sb1.append(line).append('\n'));
			System.out.println(sb1.toString());
			
			long start = System.currentTimeMillis();
			proc = runtime.exec("java -cp tmp Solution");
			dur = System.currentTimeMillis() - start;
			br = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			br.lines().forEach((line) -> sb.append(line).append('\n'));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Result rslt = new Result();
		rslt.setResult(sb.toString());
		rslt.setDuration(dur);
		return rslt;
	}
}
