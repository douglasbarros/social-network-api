package com.social_network.kata.api.cli;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.social_network.kata.api.application.dto.PostMessageRequestDTO;

public class CLI {

    private static String base = "http://localhost:8080/api/v1";

    public static void main(String[] args) throws Exception {
        System.out.println("Social Network CLI. Commands:");
        System.out.println("post <user> <message>");
        System.out.println("timeline <user>");
        System.out.println("follow <follower> <followee>");
        System.out.println("wall <user>");
        System.out.println("dm <from> <to> <message>");
        System.out.println("inbox <user>");
        System.out.println("chat <userA> <userB>");
        System.out.println("mentions <user>");
        try (Scanner sc = new Scanner(System.in)) {
            while (true) {
                System.out.print("> ");
                String line = sc.nextLine();
                if (line == null || line.isBlank())
                    continue;
                if (line.equalsIgnoreCase("exit") || line.equalsIgnoreCase("quit"))
                    break;
                try {
                    handle(line);
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        }
    }

    private static void handle(String line) throws Exception {
        String[] parts = line.split(" ", 3);
        switch (parts[0]) {
            case "post" -> post(parts[1], parts[2]);
            case "timeline" -> get("/timeline/" + parts[1]);
            case "follow" -> follow(parts[1], parts[2]);
            case "wall" -> get("/wall/" + parts[1]);
            case "dm" -> {
                String[] rest = parts[2].split(" ", 2);
                sendDM(parts[1], rest[0], rest[1]);
            }
            case "inbox" -> get("/dm/" + parts[1]);
            case "chat" -> get("/dm/between/" + parts[1] + "/" + parts[2]);
            case "mentions" -> get("/mentions/" + parts[1]);
            default -> System.out.println("Unknown command");
        }
    }

    private static void post(String user, String message) throws Exception {
        PostMessageRequestDTO content = new PostMessageRequestDTO();
        content.setContent(message);
        request("POST", "/timeline/" + user, new ObjectMapper().writeValueAsString(content));
    }

    private static void follow(String follower, String followee) throws Exception {
        request("POST", "/follow/" + follower + "/to/" + followee, "");
    }

    private static void sendDM(String from, String to, String message) throws Exception {
        PostMessageRequestDTO content = new PostMessageRequestDTO();
        content.setContent(message);
        request("POST", "/dm/" + from + "/to/" + to, new ObjectMapper().writeValueAsString(content));
    }

    private static void get(String path) throws Exception {
        HttpURLConnection conn = (HttpURLConnection) new URL(base + path).openConnection();
        conn.setRequestMethod("GET");
        try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            String s;
            while ((s = br.readLine()) != null)
                System.out.println(s);
        }
        conn.disconnect();
    }

    private static void request(String method, String path, String body) throws Exception {
        HttpURLConnection conn = (HttpURLConnection) new URL(base + path).openConnection();
        conn.setRequestMethod(method);
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        if (body != null && !body.isEmpty()) {
            try (OutputStream os = conn.getOutputStream()) {
                os.write(body.getBytes(StandardCharsets.UTF_8));
            }
        }
        int code = conn.getResponseCode();
        System.out.println(method + " " + path + " -> " + code);
        try (InputStream is = (code >= 200 && code < 300) ? conn.getInputStream() : conn.getErrorStream()) {
            if (is != null) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                    String s;
                    while ((s = br.readLine()) != null)
                        System.out.println(s);
                }
            }
        }
        conn.disconnect();
    }
}
