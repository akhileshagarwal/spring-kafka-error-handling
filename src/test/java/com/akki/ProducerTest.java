package com.akki;


import com.akki.dto.Thing;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.util.stream.IntStream;

@ExtendWith(SpringExtension.class)
public class ProducerTest {


    RestTemplate restTemplate = new RestTemplate();

    @Test
    void name() {
        var url = "http://localhost:9091/produce";
        //var url = "http://localhost:9091/produce/%s";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/json");
        headers.add("Accept", "text/html");
        IntStream.range(9,14).forEach(id -> {
            Thing thing = new Thing(id);
            HttpEntity<Thing> httpEntity = new HttpEntity<>(thing,headers);
          //  var finalUrl= String.format(url, id%2==0?id:"a");
          //  var finalUrl= String.format(url, /*id%2==0?id:*/id);
       // restTemplate.getForEntity(String.format(url, id), String.class);
            String t = restTemplate.postForObject(url, httpEntity, String.class);
        });


    }
}
