package com.example.demo.service;

import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;

@Service
public class InstanceIdService {

    private final String instanceId;

    public InstanceIdService() {
        this.instanceId = generateInstanceId();
    }

    public String getInstanceId() {
        return instanceId;
    }

    private static String generateInstanceId() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            // Fallback in case hostname is not available
            return "unknown-" + UUID.randomUUID();
        }
    }
}
