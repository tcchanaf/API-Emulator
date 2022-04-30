package com.ctc.demo.emulator;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:/spring/applicationContext*.xml")
public class Application {

	public static void main(String[] args) throws URISyntaxException {

		String baseDir = System.getProperty("base.dir");
		if (StringUtils.isBlank(baseDir)) {
			URL location = Application.class.getProtectionDomain().getCodeSource().getLocation();
			String protocol = location.getProtocol();
			if ("jar".equalsIgnoreCase(protocol)) {
				ApplicationHome home = new ApplicationHome(Application.class);
				baseDir = home.getDir().getPath();
			} else {
				baseDir = new File(location.toURI()).getParent();
			}

			System.getProperties().put("base.dir", baseDir);
		}

		SpringApplication.run(Application.class, args);
	}

}
