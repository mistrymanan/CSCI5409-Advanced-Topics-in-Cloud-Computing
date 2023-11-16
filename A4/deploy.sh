mvn clean install
aws s3 cp target/A4-1.0-SNAPSHOT.jar  s3://lambda-functions-reservation-service/A4-1.0-SNAPSHOT.jar
aws lambda update-function-code \
        --function-name MD5Hash \
        --s3-bucket lambda-functions-reservation-service  \
        --s3-key A4-1.0-SNAPSHOT.jar \
        --region us-east-1

aws lambda update-function-code \
        --function-name SHA-256Hash \
        --s3-bucket lambda-functions-reservation-service  \
        --s3-key A4-1.0-SNAPSHOT.jar \
        --region us-east-1

aws lambda update-function-code \
        --function-name BLAKE2bHash \
        --s3-bucket lambda-functions-reservation-service  \
        --s3-key A4-1.0-SNAPSHOT.jar \
        --region us-east-1
