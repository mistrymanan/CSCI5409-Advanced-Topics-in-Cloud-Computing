import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bouncycastle.crypto.digests.Blake2bDigest;
import org.bouncycastle.jcajce.provider.digest.Blake2b;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Blake2HashLambda  implements RequestHandler<Map<String,Object>,String> {
    @Override
    public String handleRequest(Map<String, Object> input, Context context) {
        context.getLogger().log("HMM->Received input: " + input);

        String inputData = input.get("input").toString();

        String hash = "";
//        Map<String, String> resultMap = convertToMap(inputData);
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> resultMap = null;
        try {
            resultMap = objectMapper.readValue(inputData, Map.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        context.getLogger().log("The ResultMap->"+resultMap);
        hash = createBlake2bHash(resultMap.get("value"));
        context.getLogger().log("SHA hash of the input: " + hash);
        sendHashToURL(context.getLogger(), resultMap.get("course_uri"),hash, "B00948831","arn:aws:lambda:us-east-1:389834615459:function:BLAKE2bHash",resultMap.get("action"),resultMap.get("value"));
        return hash;
    }
//    private Map<String,String> convertToMap(String input){
//        String[] keyValuePairs = input.substring(1, input.length() - 1).split(", ");
//
//        Map<String, String> resultMap = new HashMap<>();
//
//        for (String pair : keyValuePairs) {
//            String[] entry = pair.split("=");
//            resultMap.put(entry[0], entry[1]);
//        }
//        return resultMap;
//    }
    private String createBlake2bHash(String input) {
        try {

            Blake2bDigest blake2bDigest = new Blake2bDigest(null, 32, null, null);
            byte[] hash = new byte[blake2bDigest.getDigestSize()];

            byte[] inputBytes = input.getBytes(StandardCharsets.UTF_8);
            blake2bDigest.update(inputBytes, 0, inputBytes.length);
            blake2bDigest.doFinal(hash, 0);

            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error creating Blake2b hash", e);
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
