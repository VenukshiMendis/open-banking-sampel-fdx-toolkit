package com.wso2.openbanking.fdx.identity.dcr.util;

/**
 * FDX Registration Test Constants.
 */
public class RegistrationTestConstants {
    public static String registrationRequestJson = "{\n" +
            "  \"iss\": \"pP2KoaavpOuoE7rvQsZEuE6\",\n" +
            "  \"iat\": 1694075713,\n" +
            "  \"exp\": 2147483646,\n" +
            "  \"jti\": \"37747cd1c10545699f754adf28b73e32\",\n" +
            "  \"aud\": \"https://secure.api.dataholder.com/issuer\",\n" +
            "  \"redirect_uris\": [\n" +
            "    \"https://www.mockcompany.com/redirects/redirect1\"\n" +
            "  ],\n" +
            " \"token_endpoint_auth_signing_alg\": \"PS256\",\n" +
            " \"token_endpoint_auth_method\": \"private_key_jwt\",\n" +
            "  \"grant_types\": [\n" +
            "    \"client_credentials\",\n" +
            "    \"authorization_code\",\n" +
            "    \"refresh_token\",\n" +
            "    \"urn:ietf:params:oauth:grant-type:jwt-bearer\"\n" +
            "  ],\n" +
            "  \"response_types\": [\n" +
            "    \"code id_token\"\n" +
            "  ],\n" +
            "  \"application_type\": \"web\",\n" +
            "  \"id_token_signed_response_alg\": \"PS256\",\n" +
            "  \"id_token_encrypted_response_alg\": \"RSA-OAEP\",\n" +
            "  \"id_token_encrypted_response_enc\": \"A256GCM\",\n" +
            "  \"request_object_signing_alg\": \"PS256\",\n" +
            "  \"scope\": \"ACCOUNT_DETAILED  ACCOUNT_PAYMENTS TRANSACTIONS openid\",\n" +
            "  \"client_name\": \"My Example Client\", \n" +
            "  \"description\": \"Recipient application for specified financial use case\", \n" +
            "  \"logo_uri\": \"https://client.example.org/logo.png\", \n" +
            "  \"client_uri\": \"https://example.net/\", \n" +
            "  \"contacts\": [\"support@example.net\"], \n" +
            "  \"duration_type\": [\"time_bound\",\"one_time\"], \n" +
            "  \"duration_period\": 65, \n" +
            "  \"lookback_period\": 265, \n" +
            "  \"registry_references\": [  \n" +
            "  { \n" +
            "    \"registered_entity_name\": \"Data recipient company legal name\", \n" +
            "    \"registered_entity_id\": \"4HCHXIURY78NNH6JH\", \n" +
            "    \"registry\": \"GLIEF\" \n" +
            "  },\n" +
            "  { \n" +
            "    \"registered_entity_name\": \"Sample company name\", \n" +
            "    \"registered_entity_id\": \"4HCHXYTU78NNH6JH\", \n" +
            "    \"registry\": \"GLIEF\" \n" +
            "  }],\n" +
            "  \"software_statement\":" + RegistrationTestConstants.SSA +
            "}";

    public static final  String SSA = "eyJhbGciOiJQUzI1NiIsImtpZCI6ImgzWkNGMFZyemdYZ25IQ3FiSGJLWHp6ZmpUZyIsInR5" +
            "cCI6IkpXVCJ9.eyJpc3MiOiJPcGVuQmFua2luZyBMdGQiLCJpYXQiOjE2NDc0MDU5NDAsImp0aSI6IjM2YjVkZmUwMjA1YzQwN" +
            "jAiLCJzb2Z0d2FyZV9pZCI6InBQMktvYWF2cE91b0U3cnZRc1pFdUU2IiwiY2xpZW50X25hbWUiOiJNeSBFeGFtcGxlIENsaWV" +
            "udCIsInJlZGlyZWN0X3VyaXMiOlsiaHR0cHM6Ly93d3cubW9ja2NvbXBhbnkuY29tL3JlZGlyZWN0cy9yZWRpcmVjdDEiXSwiZ" +
            "GVzY3JpcHRpb24iOiJSZWNpcGllbnQgYXBwbGljYXRpb24gZm9yIHNwZWNpZmllZCBmaW5hbmNpYWwgdXNlIGNhc2UiLCJsb2d" +
            "vX3VyaSI6Imh0dHBzOi8vY2xpZW50LmV4YW1wbGUub3JnL2xvZ28ucG5nIiwiY2xpZW50X3VyaSI6Imh0dHBzOi8vZXhhbXBsZ" +
            "S5uZXQvIiwiY29udGFjdHMiOlsic3VwcG9ydEBleGFtcGxlLm5ldCJdLCJkdXJhdGlvbl90eXBlIjpbInRpbWVfYm91bmQiLCJ" +
            "vbmVfdGltZSJdLCJkdXJhdGlvbl9wZXJpb2QiOjY1LCJsb29rYmFja19wZXJpb2QiOjI2NSwidG9rZW5fZW5kcG9pbnRfYXV0a" +
            "F9tZXRob2QiOiJwcml2YXRlX3hjZGZrZXlfand0Iiwic2NvcGUiOiJBQ0NPVU5UX0RFVEFJTEVEICBBQ0NPVU5UX1BBWU1FTlR" +
            "TIFRSQU5TQUNUSU9OUyBvcGVuaWQiLCJyZWdpc3RyeV9yZWZlcmVuY2VzIjpbeyJyZWdpc3RlcmVkX2VudGl0eV9uYW1lIjoiR" +
            "GF0YSByZWNpcGllbnQgY29tcGFueSBsZWdhbCBuYW1lIiwicmVnaXN0ZXJlZF9lbnRpdHlfaWQiOiI0SENIWElVUlk3OE5OSDZ" +
            "KSCIsInJlZ2lzdHJ5IjoiR0xJRUYifSx7InJlZ2lzdGVyZWRfZW50aXR5X25hbWUiOiJTYW1wbGUgY29tcGFueSBuYW1lIiwic" +
            "mVnaXN0ZXJlZF9lbnRpdHlfaWQiOiI0SENIWFlUVTc4Tk5INkpIIiwicmVnaXN0cnkiOiJHTElFRiJ9XSwic29mdHdhcmVfcm9" +
            "sZXMiOlsiUElTUCIsIkFJU1AiLCJDQlBJSSJdLCJvcmdfandrc19lbmRwb2ludCI6Imh0dHBzOi8va2V5c3RvcmUub3BlbmJhb" +
            "mtpbmd0ZXN0Lm9yZy51ay8wMDE1ODAwMDAxSFFRclpBQVgvMDAxNTgwMDAwMUhRUXJaQUFYLmp3a3MiLCJvcmdfandrc19yZXZ" +
            "va2VkX2VuZHBvaW50IjoiaHR0cHM6Ly9rZXlzdG9yZS5vcGVuYmFua2luZ3Rlc3Qub3JnLnVrLzAwMTU4MDAwMDFIUVFyWkFBW" +
            "C9yZXZva2VkLzAwMTU4MDAwMDFIUVFyWkFBWC5qd2tzIiwic29mdHdhcmVfandrc19lbmRwb2ludCI6Imh0dHBzOi8va2V5c3R" +
            "vcmUub3BlbmJhbmtpbmd0ZXN0Lm9yZy51ay8wMDE1ODAwMDAxSFFRclpBQVgvb1E0S29hYXZwT3VvRTdydlFzWkVPVi5qd2tzI" +
            "iwic29mdHdhcmVfandrc19yZXZva2VkX2VuZHBvaW50IjoiaHR0cHM6Ly9rZXlzdG9yZS5vcGVuYmFua2luZ3Rlc3Qub3JnLnV" +
            "rLzAwMTU4MDAwMDFIUVFyWkFBWC9yZXZva2VkL29RNEtvYWF2cE91b0U3cnZRc1pFT1YuandrcyJ9.dHtAs7aGlH6_AYI0f-7u" +
            "xQu3-5XTL_HgFOg8czMbh_Zz_DhXs0GU7vGrfaPLvpnbC3kLvCusmfr8nQFtFFm4AzpswHP9qA4YVdXSuZF7VEnZgOtU-pIUV4" +
            "3eqz_ZNJ4_Td17BYnPjHn9Bni7e_UIOdQPiv0GKXlB1cJsEKySWQhc9EI2StGjP1EV7NTmdPDgvjoAXb4CMcjSLr1szb2Lj_Uy" +
            "kZNzHDYxibXnNUXX5YHGf1uuHlun6GysFXWw6wTla3txD25tmZ5SSlOIJJ0Kdb-j3yfp5QH5IAmzexjyXBRu-8onHEgjqK1Mfc" +
            "I-5GmB0lRjWVvkpfAhQ5Zwuy_DPg";

    public static String registryReference = "{ \n" +
            "    \"registered_entity_name\": \"Data recipient company legal name\", \n" +
            "    \"registered_entity_id\": \"4HCHXIURY78NNH6JH\", \n" +
            "    \"registry\": \"GLIEF\" \n" +
            "  }";

}
