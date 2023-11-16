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

public class SHA256Lambda implements RequestHandler<Map<String,Object>,String> {

    @Override
    public String handleRequest(Map<String, Object> input, Context context) {
        context.getLogger().log("HMM->Received input: " + input);

        String inputData = input.get("input").toString();

        String hash = "";
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> resultMap = null;
        try {
            resultMap = objectMapper.readValue(inputData, Map.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        context.getLogger().log("The ResultMap: "+resultMap);
        hash = createSHA256Hash(resultMap.get("value"));
        context.getLogger().log("SHA hash of the input: " + hash);
        sendHashToURL(context.getLogger(), resultMap.get("course_uri"),hash, "B00948831","arn:aws:lambda:us-east-1:389834615459:function:SHA-256Hash",resultMap.get("action"),resultMap.get("value"));
        return hash;
    }
    private String createSHA256Hash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(input.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found", e);
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
