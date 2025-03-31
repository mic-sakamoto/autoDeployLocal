package utils;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.CopyObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;

import db.dao.CommonDao;

public class AwsUtill {

    public static final AmazonS3 getS3client(String env) throws Exception {
        
        if (env.equals("1")) {
        
            String s3AccessKey = "";
            String s3SecretKey = "";
            CommonDao comdao = new CommonDao();
            ArrayList<HashMap<String, Object>> awsConfList = comdao.getAwsConfig();
            if (awsConfList != null) {
                for (HashMap<String, Object> conf : awsConfList) {
                    if ("1".equals(conf.get("Id").toString())) {
                        s3AccessKey = conf.get("KeyValue").toString();
                    }
                    if ("2".equals(conf.get("Id").toString())) {
                        s3SecretKey = conf.get("KeyValue").toString();
                    }
                }
            }
            /*
             * S3クライアント設定
             */
            AWSCredentials credentials = new BasicAWSCredentials(s3AccessKey, s3SecretKey);
            
            return AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials)).withRegion(Regions.AP_NORTHEAST_1).build();
            
        } else {
            
            return AmazonS3ClientBuilder.standard().withRegion(Regions.AP_NORTHEAST_1).build();
            
        }
    }

    public static final AmazonSimpleEmailService getSESclient() {
        return AmazonSimpleEmailServiceClientBuilder.standard().withRegion(Regions.US_EAST_1).build();
    }

    public static final void s3Put(AmazonS3 s3client, String bucketName, String key, File file) throws Exception {

        try {

            PutObjectRequest request = new PutObjectRequest(bucketName, key, file);

            s3client.putObject(request);

        } catch (AmazonServiceException ase) {
            System.out.println("Caught an AmazonServiceException");
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());
            throw new Exception();
        } catch (AmazonClientException ace) {
            System.out.println("Caught an AmazonClientException");
            System.out.println("Error Message: " + ace.getMessage());
            throw new Exception();
        } catch (Exception e) {
            System.out.println("Caught an Exception");
            System.out.println("Error Message:    " + e.getMessage());
            throw e;
        }
    }
    public static final void s3PutPublic(AmazonS3 s3client, String bucketName, String key, File file) throws Exception {

        try {

            PutObjectRequest request = new PutObjectRequest(bucketName, key, file);
            request.setCannedAcl(CannedAccessControlList.PublicRead);
            ObjectMetadata metadata = new ObjectMetadata();
            int index = key.indexOf(".");
            if (index != -1) {
                index += ".".length();
                String kakuchoshi = key.substring(index);
                if(kakuchoshi.equals("pdf")) {
                    metadata.setContentType("application/pdf");
                    request.setMetadata(metadata);
                }
            }
            s3client.putObject(request);

        } catch (AmazonServiceException ase) {
            System.out.println("Caught an AmazonServiceException");
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());
            throw new Exception();
        } catch (AmazonClientException ace) {
            System.out.println("Caught an AmazonClientException");
            System.out.println("Error Message: " + ace.getMessage());
            throw new Exception();
        } catch (Exception e) {
            System.out.println("Caught an Exception");
            System.out.println("Error Message:    " + e.getMessage());
            throw e;
        }
    }

    public static final void s3Put(AmazonS3 s3client, String bucketName, String key, InputStream input, ObjectMetadata metadata) throws Exception {

        try {

            PutObjectRequest request = new PutObjectRequest(bucketName, key, input, metadata);

            s3client.putObject(request);

        } catch (AmazonServiceException ase) {
            System.out.println("Caught an AmazonServiceException");
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());
            throw new Exception();
        } catch (AmazonClientException ace) {
            System.out.println("Caught an AmazonClientException");
            System.out.println("Error Message: " + ace.getMessage());
            throw new Exception();
        } catch (Exception e) {
            System.out.println("Caught an Exception");
            System.out.println("Error Message:    " + e.getMessage());
            throw e;
        }
    }
    public static final void s3PutPublic(AmazonS3 s3client, String bucketName, String key, InputStream input, ObjectMetadata metadata) throws Exception {

        try {

            PutObjectRequest request = new PutObjectRequest(bucketName, key, input, metadata);
            request.setCannedAcl(CannedAccessControlList.PublicRead);

            s3client.putObject(request);

        } catch (AmazonServiceException ase) {
            System.out.println("Caught an AmazonServiceException");
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());
            throw new Exception();
        } catch (AmazonClientException ace) {
            System.out.println("Caught an AmazonClientException");
            System.out.println("Error Message: " + ace.getMessage());
            throw new Exception();
        } catch (Exception e) {
            System.out.println("Caught an Exception");
            System.out.println("Error Message:    " + e.getMessage());
            throw e;
        }
    }

    public static final void s3Delete(AmazonS3 s3client, String bucketName, String key) throws Exception {

        try {

            s3client.deleteObject(bucketName, key);

        } catch (AmazonServiceException ase) {
            System.out.println("Caught an AmazonServiceException");
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());
            throw new Exception();
        } catch (AmazonClientException ace) {
            System.out.println("Caught an AmazonClientException");
            System.out.println("Error Message: " + ace.getMessage());
            throw new Exception();
        } catch (Exception e) {
            System.out.println("Caught an Exception");
            System.out.println("Error Message:    " + e.getMessage());
            throw e;
        }
    }

    public static final void s3Copy(AmazonS3 s3client, String before_bucketName, String before_key, String affer_bucketName, String affer_key) throws Exception {

        try {

            CopyObjectRequest copyObjRequest = new CopyObjectRequest(before_bucketName, before_key, affer_bucketName, affer_key);
            s3client.copyObject(copyObjRequest);

        } catch (AmazonServiceException ase) {
            System.out.println("Caught an AmazonServiceException");
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());
            throw new Exception();
        } catch (AmazonClientException ace) {
            System.out.println("Caught an AmazonClientException");
            System.out.println("Error Message: " + ace.getMessage());
            throw new Exception();
        } catch (Exception e) {
            System.out.println("Caught an Exception");
            System.out.println("Error Message:    " + e.getMessage());
            throw e;
        }

    }

    public static final void s3CopyPublic(AmazonS3 s3client, String before_bucketName, String before_key, String affer_bucketName, String affer_key) throws Exception {

        try {

            CopyObjectRequest copyObjRequest = new CopyObjectRequest(before_bucketName, before_key, affer_bucketName, affer_key);
            copyObjRequest.setCannedAccessControlList(CannedAccessControlList.PublicRead);
            s3client.copyObject(copyObjRequest);

        } catch (AmazonServiceException ase) {
            System.out.println("Caught an AmazonServiceException");
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());
            throw new Exception();
        } catch (AmazonClientException ace) {
            System.out.println("Caught an AmazonClientException");
            System.out.println("Error Message: " + ace.getMessage());
            throw new Exception();
        } catch (Exception e) {
            System.out.println("Caught an Exception");
            System.out.println("Error Message:    " + e.getMessage());
            throw e;
        }

    }


}

