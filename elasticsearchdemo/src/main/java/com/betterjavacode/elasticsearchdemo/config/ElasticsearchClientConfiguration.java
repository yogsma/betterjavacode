package com.betterjavacode.elasticsearchdemo.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.betterjavacode.elasticsearchdemo.repositories")
@ComponentScan(basePackages = "com.betterjavacode.elasticsearchdemo")
public class ElasticsearchClientConfiguration extends AbstractElasticsearchConfiguration
{

    @Override
    @Bean
    public RestHighLevelClient elasticsearchClient ()
    {
        final ClientConfiguration clientConfiguration =
                ClientConfiguration.builder().connectedTo("localhost:9200").build();

        return RestClients.create(clientConfiguration).rest();
    }
}
