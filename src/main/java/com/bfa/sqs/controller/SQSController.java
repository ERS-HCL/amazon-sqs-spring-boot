package com.bfa.sqs.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;

@Controller
public class SQSController {
	
	private static final Logger log = LoggerFactory.getLogger(SQSController.class);
	
	@Value("${sqs.url}")
	private String sqsURL;
	
	@RequestMapping(value = "/sendMessageQueue", method = RequestMethod.POST)
    public @ResponseBody void write(@RequestBody String notificationData){
		try {
		
		final AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();
		log.info("===============================================");
		log.info("Getting Started with Amazon SQS Standard Queues");
		log.info("===============================================\n");
        
		log.info("Sending a message to MyQueue.\n");
        sqs.sendMessage(new SendMessageRequest(sqsURL, notificationData));
        log.info("Message Sent.\n");
        
		}catch (final AmazonServiceException ase) {
			log.error("Caught an AmazonServiceException, which means " +
                    "your request made it to Amazon SQS, but was " +
                    "rejected with an error response for some reason.");
			log.error("Error Message:    " + ase.getMessage());
			log.error("HTTP Status Code: " + ase.getStatusCode());
			log.error("AWS Error Code:   " + ase.getErrorCode());
			log.error("Error Type:       " + ase.getErrorType());
			log.error("Request ID:       " + ase.getRequestId());
        } catch (final AmazonClientException ace) {
        	log.error("Caught an AmazonClientException, which means " +
                    "the client encountered a serious internal problem while " +
                    "trying to communicate with Amazon SQS, such as not " +
                    "being able to access the network.");
        	log.error("Error Message: " + ace.getMessage());
        }
    }
}
