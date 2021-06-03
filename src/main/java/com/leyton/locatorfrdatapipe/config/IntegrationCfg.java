package com.leyton.locatorfrdatapipe.config;

import com.leyton.locatorfrdatapipe.transformer.TransformerDtj;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.FileWritingMessageHandler;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class IntegrationCfg {

    private TransformerDtj transformerDtj;
    private static final Path PATH_TO_SNIFF=Paths.get("/","home","fdoughmi","Documents","testOsWalk");
    public IntegrationCfg(TransformerDtj transformerDtj){
        this.transformerDtj = transformerDtj;
    }

    @Bean
    public IntegrationFlow integrationFlow(){
        return IntegrationFlows.from(fileReader(),
                spec -> spec.poller(Pollers.fixedDelay(500)))
                .transform(transformerDtj,"transforme")
                .handle(fileWriter())
                .get();
    }

    @Bean
    public FileWritingMessageHandler  fileWriter() {
        FileWritingMessageHandler handler = new FileWritingMessageHandler(new File("destination"));
        handler.setExpectReply(false);
        return handler;
    }

    @Bean
    public FileReadingMessageSource fileReader() {
        FileReadingMessageSource fileReadingFromSource = new FileReadingMessageSource();
        fileReadingFromSource.setDirectory(new File(PATH_TO_SNIFF.toUri()));
        return fileReadingFromSource;
    }
}
