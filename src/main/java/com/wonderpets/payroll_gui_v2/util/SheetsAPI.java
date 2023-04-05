package com.wonderpets.payroll_gui_v2.util;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class SheetsAPI {
    private static final String APPLICATION_NAME = "MotorPH Google Sheet API";
    private static final String SERVICE_ACCOUNT_FILE = "/credentials.json";

    public static Sheets getSheetsService() throws IOException {
        Credential credential = authorize();
        return new Sheets.Builder(new NetHttpTransport(), new JacksonFactory(), credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    private static Credential authorize() throws IOException {
        return GoogleCredential.fromStream(Objects.requireNonNull(SheetsAPI.class.getResourceAsStream(SERVICE_ACCOUNT_FILE)))
                .createScoped(Collections.singleton(SheetsScopes.SPREADSHEETS_READONLY
                ));
    }

    public static void main(String[] args) throws IOException {
        Sheets sheetsService = SheetsAPI.getSheetsService();
        String spreadsheetId = "1LaWzN0k-JI1Z9L3UVqIF3K8nthAkourEy7bzfulNQF0";
        String range = "Employee Details!A2:S26";
        ValueRange response = sheetsService.spreadsheets().values().get(spreadsheetId, range).execute();
        List<List<Object>> values = response.getValues();
        if (values == null || values.isEmpty()) {
            System.out.println("No data found.");
        } else {
            System.out.println(values.size());
        }
    }
}
