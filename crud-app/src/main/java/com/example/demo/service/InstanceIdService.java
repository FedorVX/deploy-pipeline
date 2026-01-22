package com.example.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;

@Service
public class InstanceIdService {

    private static final Logger log = LoggerFactory.getLogger(InstanceIdService.class);
    private final String instanceId;

    public InstanceIdService() {
        this.instanceId = generateInstanceId();
    }

    public String getInstanceId() {
        return instanceId;
    }

    private static String generateInstanceId() {
        try {
            String hostname = InetAddress.getLocalHost().getHostName();
            log.info("Successfully determined hostname for instance ID: {}", hostname);
            return hostname;
        } catch (UnknownHostException e) {
            String fallbackId = "unknown-" + UUID.randomUUID();
            log.warn("Could not determine hostname. Falling back to generated ID: {}. Error: {}", fallbackId, e.getMessage());
            return fallbackId;
        }
    }
}
