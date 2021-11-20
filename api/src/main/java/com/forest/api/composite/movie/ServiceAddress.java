package com.forest.api.composite.movie;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class ServiceAddress {
    private  String  compositeAddress;
    private  String  movieAddress;
    private  String  reviewAddress;

    public ServiceAddress(String serviceAddress, String movieAddress, String reviewAddress) {
    }
}

