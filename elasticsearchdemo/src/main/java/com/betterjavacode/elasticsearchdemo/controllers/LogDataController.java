package com.betterjavacode.elasticsearchdemo.controllers;

import com.betterjavacode.elasticsearchdemo.models.LogData;
import com.betterjavacode.elasticsearchdemo.services.LogDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/betterjavacode/logdata")
public class LogDataController
{
    @Autowired
    private LogDataService logDataService;

    @GetMapping
    public List<LogData> searchLogDataByHost(@RequestParam("host") String host)
    {
        List<LogData> logDataList = logDataService.getAllLogDataForHost(host);

        return logDataList;
    }

    @GetMapping("/search")
    public List<LogData> searchLogDataByTerm(@RequestParam("term") String term)
    {
        return logDataService.findBySearchTerm(term);
    }

    @PostMapping
    public LogData addLogData(@RequestBody LogData logData)
    {

        return logDataService.createLogDataIndex(logData);
    }

    @PostMapping("/createInBulk")
    public  List<LogData> addLogDataInBulk(@RequestBody List<LogData> logDataList)
    {
        return (List<LogData>) logDataService.createLogDataIndices(logDataList);
    }
}
