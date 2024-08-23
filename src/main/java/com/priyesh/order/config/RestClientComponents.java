package com.priyesh.order.config;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.ClientHttpRequestFactories;
import org.springframework.boot.web.client.ClientHttpRequestFactorySettings;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import com.priyesh.order.client.InventoryClient;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RestClientComponents
{

	
	  @Value("${inventory.service.url}") 
	  private String inventoryServiceUrl;
	  
	  @Autowired 
	  private RestClient.Builder restClientBuilder;
	  
	  //@Autowired private LoadBalancerClient loadBalancerClient;
	  
	  @Value("${inventory.service.servicename}") 
	  private String inventoryServiceName;
	 
	  public InventoryClient getInventoryClient()
	  {
			/*
			 * ServiceInstance instance = loadBalancerClient.choose(inventoryServiceName);
			 * String inventoryServiceUrl = instance.getUri().toString();
			 * 
			 * log.info("Request to inventory service url :: "+inventoryServiceUrl);
			 */
		 
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
