package com.ruslan.planefinder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SpringBootApplication
@ComponentScan(
    excludeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = { } // the bean you want to disable
    )
)
public class PlaneFinderApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlaneFinderApplication.class, args);
	}

}
