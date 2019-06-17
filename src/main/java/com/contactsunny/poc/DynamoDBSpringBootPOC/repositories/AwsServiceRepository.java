package com.contactsunny.poc.DynamoDBSpringBootPOC.repositories;

import com.contactsunny.poc.DynamoDBSpringBootPOC.models.AwsService;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface AwsServiceRepository extends CrudRepository<AwsService, String> {
}