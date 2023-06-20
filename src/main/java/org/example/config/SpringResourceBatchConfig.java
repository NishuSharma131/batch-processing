package org.example.config;

import org.example.entity.Project;
import org.example.processor.ProjectProcessor;
import org.example.processor.ResourceProcessor;
import org.example.repository.ProjectRepository;
import org.example.repository.ResourceRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.file.FlatFileHeaderCallback;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Sort;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.function.Function;

@Configuration
@EnableBatchProcessing
public class SpringResourceBatchConfig {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;
    private Resource outputResource = new FileSystemResource("C://data//output.csv");
    @Autowired
    public ResourceRepository resourceRepository;
    private final String[] headers = new String[]{"id", "project_name", "allocated_capital","used_capital"};



    @Bean
    public RepositoryItemReader<Resource> resourceReader(){
        RepositoryItemReader<Resource> repositoryItemReader =new RepositoryItemReader<>();
        repositoryItemReader.setRepository(resourceRepository);
        repositoryItemReader.setMethodName("findAll");
        final HashMap<String, Sort.Direction> sorts = new HashMap<>();
        sorts.put("id", Sort.Direction.ASC);
        repositoryItemReader.setSort(sorts);
        return repositoryItemReader;
    }



    @Bean
    public FlatFileItemWriter<Resource> resourceWriter() {

        FlatFileItemWriter<Resource> resourceWriter = new FlatFileItemWriter<>();

        resourceWriter.setResource(outputResource);
        resourceWriter.setAppendAllowed(true);

        resourceWriter.setLineAggregator(new DelimitedLineAggregator<Resource>() {
            {
                setDelimiter(",");
                setFieldExtractor(new BeanWrapperFieldExtractor<Resource>() {
                    {
                        setNames(new String[] {"id", "resource_name", "designation","allocation"});
                    }
                });
            }
        });
        resourceWriter.setHeaderCallback(new FlatFileHeaderCallback() {
            @Override
            public void writeHeader(Writer writer) throws IOException {
                for(int i=0;i<headers.length;i++){
                    if(i!=headers.length-1)
                        writer.append(headers[i] + ",");
                    else
                        writer.append(headers[i]);
                }
            }
        });

        return  resourceWriter;
    }

    @Bean
    public ResourceProcessor  resourceProcessor() {
        return new ResourceProcessor();
    }







//    @Bean
//    public Job runResourceJob() {
//        return jobBuilderFactory.get("importResourcejob")
//                .flow(step2()).end().build();
//
//    }
   // @Bean
//    public Step step2() {
//        return stepBuilderFactory.get("csv-step").<Resource, Resource>chunk(10)
//                .reader(reader()).processor(resourceProcessor()).writer(projectWriter()).build();
//    }
}
