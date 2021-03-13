package com.contactsunny.poc.DynamoDBSpringBootPOC;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import com.contactsunny.poc.DynamoDBSpringBootPOC.models.AwsService;
import com.contactsunny.poc.DynamoDBSpringBootPOC.repositories.AwsServiceRepository;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Optional;

@SpringBootApplication
public class App implements CommandLineRunner {

    private DynamoDBMapper dynamoDBMapper;

    @Autowired
    private AmazonDynamoDB amazonDynamoDB;

    @Autowired
    private AwsServiceRepository awsServiceRepository;

    private static final Logger logger = LogManager.getLogger(App.class);

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {

        dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);



        CreateTableRequest tableRequest = dynamoDBMapper
                .generateCreateTableRequest(AwsService.class);

        tableRequest.setProvisionedThroughput(
                new ProvisionedThroughput(1L, 1L));
        

        TableUtils.createTableIfNotExists(amazonDynamoDB, tableRequest);

        AwsService awsService = new AwsService();

        awsService.setServiceName("AWS DynamoDB");
        awsService.setServiceHomePageUrl("https://aws.amazon.com/dynamodb/?nc2=h_m1");

        awsService = awsServiceRepository.save(awsService);

        logger.info("Saved AwsService object: " + new Gson().toJson(awsService));

        String awsServiceId = awsService.getId();

        logger.info("AWS Service ID: " + awsServiceId);

        Optional<AwsService> awsServiceQueried = awsServiceRepository.findById(awsServiceId);

        if (awsServiceQueried.get() != null) {
            logger.info("Queried object: " + new Gson().toJson(awsServiceQueried.get()));
        }

        Iterable<AwsService> awsServices = awsServiceRepository.findAll();

        for (AwsService awsServiceObject : awsServices) {
            logger.info("List object: " + new Gson().toJson(awsServiceObject));
        }
    }
}
