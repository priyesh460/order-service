package com.priyesh.order.config;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.ClientHttpRequestFactories;
import org.springframework.boot.web.client.ClientHttpRequestFactorySettings;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import com.priyesh.order.client.InventoryClient;

@Configuration
public class RestClientConfig 
{
	
	@Value("${inventory.service.url}") 
	private String inventoryServiceUrl;
	
	@Bean
	@LoadBalanced
	public RestClient.Builder restClientBuilder()
	{
		return RestClient.builder();
	}
	
	@Bean
	public InventoryClient getInventoryClient(@Qualifier("restClientBuilder") RestClient.Builder restClientBuilder)
	  {
		
		RestClient restClient = restClientBuilder
				  .baseUrl(inventoryServiceUrl)
				  .requestFactory(getClientHttpRequestFactory())
				  .build();
		  
		  var restClientAdapter = RestClientAdapter.create(restClient); 
		  var httpServiceProxyFactory =  HttpServiceProxyFactory.builderFor(restClientAdapter).build(); 
		  return httpServiceProxyFactory.createClient(InventoryClient.class);
		 
	  }
	
	  private ClientHttpRequestFactory getClientHttpRequestFactory()
	  {
		  ClientHttpRequestFactorySettings clientHttpRequestFactorySettings = ClientHttpRequestFactorySettings.DEFAULTS
				  .withConnectTimeout(Duration.ofSeconds(3))
				  .withReadTimeout(Duration.ofSeconds(3));
		  
		  return ClientHttpRequestFactories.get(clientHttpRequestFactorySettings); 
	  }
}
