package com.mcmanuel.domain.student;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AwsService {
    @Value("${aws.s3.access-key}")
    private String access_key;
    @Value("${aws.s3.secret-key}")
    private String secret_key;

    @Value("${aws.s3.bucket-name}")
    private String bucket_name;

    public String uploadPhoto(MultipartFile photo){
        try {
            BasicAWSCredentials credentials = new BasicAWSCredentials(access_key,secret_key);

            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withCredentials( new AWSStaticCredentialsProvider(credentials))
                    .withRegion(Regions.AF_SOUTH_1)
                    .build();

            if(!s3Client.doesBucketExistV2(bucket_name)){
                s3Client.createBucket(new CreateBucketRequest(bucket_name, String.valueOf(Regions.AF_SOUTH_1)));
            }

            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType("image/jpeg");

            PutObjectRequest putRequest = new PutObjectRequest(bucket_name,photo.getOriginalFilename(),photo.getInputStream(),objectMetadata);
            s3Client.putObject(putRequest);

            return "https:/"+bucket_name+".s3.amazomaws.com/"+photo.getOriginalFilename();
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
            throw new RuntimeException(ex.getMessage());
        }
    }
}
