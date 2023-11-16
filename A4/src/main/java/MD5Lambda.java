
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class MD5Lambda implements RequestHandler<Map<String,Object>,String> {
    @Override
    public String handleRequest(Map<String, Object> input, Context context) {
        context.getLogger().log("HMM->Received input: " + input);

        String inputData = input.get("input").toString();

        String md5Hash = "";
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> resultMap = null;
        try {
            resultMap = objectMapper.readValue(inputData, Map.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        context.getLogger().log("The ResultMap->"+resultMap);
        md5Hash = createMD5Hash(resultMap.get("value"));
        context.getLogger().log("MD5 hash of the input: " + md5Hash);
        sendHashToURL(context.getLogger(), resultMap.get("course_uri"),md5Hash, "B00948831","arn:aws:lambda:us-east-1:389834615459:function:MD5Hash",resultMap.get("action"),resultMap.get("value"));
        return md5Hash;
    }

    private String createMD5Hash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(input.getBytes());
            byte[] digest = md.digest();

            StringBuilder hexString = new StringBuilder();
            for (byte b : digest) {
                hexString.append(String.format("%02x", b));
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 algorithm not found", e);
        }
    }
    private void sendHashToURL(LambdaLogger logger, String destination, String hash, String bannerId, String arn, String action, String value) {
        try {
            URL url = new URL(destination);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");

            String jsonPayload = String.format(
                    "{\"banner\":\"%s\",\"result\":\"%s\",\"arn\":\"%s\",\"action\":\"%s\",\"value\":\"%s\"}",
                    bannerId, hash, arn, action, value
            );

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonPayload.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            logger.log("HTTP Response Code: " + responseCode);

            connection.disconnect();
        } catch (Exception e) {
            throw new RuntimeException("Error sending hash to URL", e);
        }
    }

}
