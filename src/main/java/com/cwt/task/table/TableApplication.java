package com.cwt.task.table;

import com.vaadin.flow.component.page.Push;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.jooq.*;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class TableApplication {

	public static void main(String[] args) {
		SpringApplication.run(TableApplication.class, args);

	}

}
