package com.contactsunny.poc.DynamoDBSpringBootPOC.configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;

import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

@Configuration
@EnableDynamoDBRepositories
        (basePackages = "com.contactsunny.poc.DynamoDBSpringBootPOC.repositories")
public class DynamoDBConfig {

    @Value("${amazon.dynamodb.endpoint}")
    private String dynamoDbEndpoint;

    @Value("${amazon.aws.accessKey}")
    private String awsAccessKey;

    @Value("${amazon.aws.secretKey}")
    private String awsSecretKey;
    
    @Value("${amazon.aws.region}")
    private String awsRegion;
    
   
    public AwsClientBuilder.EndpointConfiguration endpointConfiguration() {
        return new AwsClientBuilder.EndpointConfiguration(dynamoDbEndpoint, awsRegion);
    }
 
    @Bean
    public AWSCredentialsProvider awsCredentialsProvider() {
        return new AWSStaticCredentialsProvider(new BasicAWSCredentials(awsAccessKey, awsSecretKey));
    }
 
    @Bean
    public AmazonDynamoDB amazonDynamoDB() {
        return AmazonDynamoDBClientBuilder
                .standard()
                .withEndpointConfiguration(endpointConfiguration())
                .withCredentials(awsCredentialsProvider())
                .build();
    }
    
   
}
