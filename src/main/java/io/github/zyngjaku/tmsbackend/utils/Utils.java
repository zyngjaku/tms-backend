package io.github.zyngjaku.tmsbackend.utils;

import com.google.gson.*;
import io.github.zyngjaku.tmsbackend.dao.entity.TransshipmentTerminal;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.util.List;
import java.util.Properties;
import java.util.Random;

public class Utils {
    public static final String API_KEY_HERE = "";
    public static final String MAIL_FROM = "";
    public static final String MAIL_PASSWORD = "";
    public static Logger logger = LoggerFactory.getLogger(Utils.class);

    public static Color generateHexColor() {
        Random rand = new Random();

        float r = rand.nextFloat() / 2f + 0.5f;
        float g = rand.nextFloat() / 2f + 0.5f;
        float b = rand.nextFloat() / 2f + 0.5f;

        return new Color(r, g, b);
    }

    public static String generateAvatarUrl(String firstName, String lastName, Color backgroundColor) {
        String result = "https://eu.ui-avatars.com/api/" + "?rounded=true" +
                "&bold=true" +
                "&name=" + firstName + "+" + lastName +
                "&background=" + Integer.toHexString(backgroundColor.getRGB()).substring(2) +
                "&color=ffffff";

        return result;
    }

    public static String generatePassword(int len) {
        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < len; i++) {
            int randomIndex = random.nextInt(chars.length());
            sb.append(chars.charAt(randomIndex));
        }

        return sb.toString();
    }

    public static Long testGET(List<TransshipmentTerminal> transshipmentTerminals) throws IOException, JSONException {
        StringBuilder url = new StringBuilder("https://matrix.route.ls.hereapi.com/routing/7.2/calculatematrix.json?apiKey=").append(API_KEY_HERE)
                .append("&mode=").append("fastest;truck;traffic:disabled");

        for (int i=0; i<transshipmentTerminals.size(); i++) {
            TransshipmentTerminal tran = transshipmentTerminals.get(i);
            if (i == 0) {
                url.append("&start0=");
            } else {
                url.append("&destination").append(i-1).append("=");
            }
            url.append(tran.getLatitude()).append(",").append(tran.getLongitude());
        }

        String response = getRequest(url.toString());

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONObject("response").getJSONArray("matrixEntry");
            return jsonArray.getJSONObject(jsonArray.length() - 1).getJSONObject("summary").getLong("costFactor");
        } catch(Exception e) {
            throw new InvalidDataAccessApiUsageException(e.getMessage());
        }
    }

    private static String getRequest(String requestUrl) throws IOException {
        URL obj = new URL(requestUrl);
        HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
        conn.setRequestMethod("GET");

        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            in.close();

            Utils.logger.info("[Utils] GET request finished successfully with code " + responseCode + "!");
            return response.toString();
        }

        Utils.logger.info("[Utils] An error occurred while sending GET request!\n\nMessage:" + conn.getResponseMessage() + " | Code: " + responseCode);
        throw new InvalidDataAccessApiUsageException("[GET] Message:" + conn.getResponseMessage() + " | Code: " + responseCode);
    }

    public static void sendMail(String to, String subject, String text) {
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(MAIL_FROM, MAIL_PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(text);

            Transport.send(message);

            Utils.logger.info("[Utils] Mail has been sent successfully!");
        } catch (MessagingException e) {
            e.printStackTrace();
            Utils.logger.info("[Utils] An error occurred while sending mail!\n\nMessage:" + e.getMessage());
        }
    }
}
