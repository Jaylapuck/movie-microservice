package com.forest.utils.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Component
public class ServiceUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceUtil.class);

    private final String port;

    private String serviceAddress = null;

    public ServiceUtil(
            @Value("${server.port}") String port) {
        this.port = port;
    }

    public String getServiceAddress() {
        if (serviceAddress == null){
            serviceAddress = findMyHostName() + "/" + findMyIPAddress() + ":" + port;
        }

        return serviceAddress;
    }

    private String findMyIPAddress() {

        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException unknownHostException) {
            return "unknown ip address";
        }
    }

    private String findMyHostName() {
        try{
            return InetAddress.getLocalHost().getHostName();
        }
        catch (UnknownHostException unknownHostException){
            return  "unknown host name";
        }
    }
}

