package ecc.testing.awsApi;

import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	BasicAWSCredentials awsCreds = new BasicAWSCredentials("AKIAIXRC23UU4TOFVHPQ", "xSD+ilCfRH0qOuIxFpL1pjtvQJf7wNSGwb5rkwZA");
    	AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
    	                        .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
    	                        .withRegion(Regions.SA_EAST_1)
    	                        .build();
    	s3Client.getRegionName();
    	

    	ListObjectsRequest listObjectsRequest = new ListObjectsRequest()
    	    .withBucketName("e-grabaciones");
    	
    	ObjectListing objectListing;
    	//.withPrefix("m");

    	do {
    	        objectListing = s3Client.listObjects(listObjectsRequest);
    	        for (S3ObjectSummary objectSummary : 
    	            objectListing.getObjectSummaries()) {
    	            System.out.println( " - " + objectSummary.getKey() + "  " +
    	                    "(size = " + objectSummary.getSize() + 
    	                    ")");
    	        }
    	        listObjectsRequest.setMarker(objectListing.getNextMarker());
    	} while (objectListing.isTruncated());
    	
    	

    }
}
	