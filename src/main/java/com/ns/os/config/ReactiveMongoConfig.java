package com.ns.os.config;

import com.mongodb.reactivestreams.client.MongoClient;
import com.ns.os.domain.DespatchAdvice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import reactor.core.scheduler.Schedulers;

@Configuration
@Slf4j
public class ReactiveMongoConfig {

    @Value("${spring.data.mongodb.database}")
    private String databaseName;

    @Bean
    public ReactiveMongoTemplate reactiveMongoTemplate(MongoClient mongoClient) {

        var template = new ReactiveMongoTemplate(mongoClient, databaseName);

        template
                .indexOps(DespatchAdvice.class)
                .ensureIndex(new Index().on("identifier", Sort.Direction.ASC).unique())
                .subscribeOn(Schedulers.boundedElastic())
                .subscribe();

        template
                .indexOps(DespatchAdvice.class)
                .ensureIndex(new Index().on("orderReferenceId", Sort.Direction.ASC).background())
                .subscribeOn(Schedulers.boundedElastic())
                .subscribe();


        return template;
    }
}
