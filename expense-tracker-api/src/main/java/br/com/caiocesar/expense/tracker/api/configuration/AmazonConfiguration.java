package br.com.caiocesar.expense.tracker.api.configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonConfiguration {

    public static final String ACCESS_KEY = "AKIASSO2LADF7BY2CV4O";
    public static final String SECRET_KEY = "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";



    @Bean
    public AmazonS3 amazonS3(){
        return AmazonS3ClientBuilder
                .standard()
                .withRegion(Regions.US_EAST_1)
                .withCredentials(new AWSStaticCredentialsProvider(basicAwsCredentials()))
                .build();
    }

    @Bean
    public BasicAWSCredentials basicAwsCredentials() {
        return new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);
    }
}
