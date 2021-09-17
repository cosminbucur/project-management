package com.sda.project.management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProjectManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectManagementApplication.class, args);
		System.out.println(banner());
	}

	private static String banner() {
		return "\n" +
				" █████╗ ██████╗ ██████╗ \n" +
				"██╔══██╗██╔══██╗██╔══██╗\n" +
				"███████║██████╔╝██████╔╝\n" +
				"██╔══██║██╔═══╝ ██╔═══╝ \n" +
				"██║  ██║██║     ██║     \n" +
				"╚═╝  ╚═╝╚═╝     ╚═╝     \n" +
				"is now running.";
	}
}
