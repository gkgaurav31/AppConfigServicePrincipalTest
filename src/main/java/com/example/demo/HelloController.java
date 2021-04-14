package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.azure.data.appconfiguration.ConfigurationClient;
import com.azure.data.appconfiguration.ConfigurationClientBuilder;
import com.azure.data.appconfiguration.models.ConfigurationSetting;
import com.azure.identity.DefaultAzureCredential;
import com.azure.identity.DefaultAzureCredentialBuilder;

@RestController
public class HelloController {
	
	@GetMapping("/")
	public String welcome() {
		return "Use /test";
	}

	@GetMapping("/test")
	public String hello() {

		String endpoint = "https://gaukappconfig.azconfig.io";

		DefaultAzureCredential tokenCredential = new DefaultAzureCredentialBuilder().build();

		final ConfigurationClient client = new ConfigurationClientBuilder()
				.credential(tokenCredential) // AAD authentication
				.endpoint(endpoint)
				.buildClient();

		final String key = "hello";
		final String value = "world-sp";
		
		System.out.println("Beginning of synchronous sample...");

		//ConfigurationSetting setting = client.setConfigurationSetting(key, null, value);
		 ConfigurationSetting setting = client.getConfigurationSetting(key, null, null);
		 
		 System.out.printf(String.format("[GetConfigurationSetting] Key: %s, Value: %s", setting.getKey(), setting.getValue()));
        
		return setting.getValue();
	}

}
