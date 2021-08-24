package com.betterjavacode.elasticsearchdemo.repositories;

import com.betterjavacode.elasticsearchdemo.models.LogData;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface LogDataRepository extends ElasticsearchRepository<LogData, String>
{
    List<LogData> findByHost(String host);

    List<LogData> findByMessageContaining(String message);
}
