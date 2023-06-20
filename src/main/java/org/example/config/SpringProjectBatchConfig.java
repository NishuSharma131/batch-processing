package org.example.config;

import org.example.entity.Project;
import org.example.processor.ProjectProcessor;
import org.example.processor.ResourceProcessor;
import org.example.repository.ProjectRepository;
import org.example.repository.ResourceRepository;
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
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.data.domain.Sort;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.function.Function;

@Configuration
@EnableBatchProcessing
public class SpringProjectBatchConfig {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;
    private Resource outputResource = new FileSystemResource("C://data//output.csv");
    @Autowired
    public ProjectRepository projectRepository;

    @Autowired
    public ResourceRepository resourceRepository;
    private final String[] headers = new String[]{"id", "project_name", "allocated_capital","used_capital"};

    @Bean
    public RepositoryItemReader<Project> reader(){
        RepositoryItemReader<Project> repositoryItemReader =new RepositoryItemReader<>();
        repositoryItemReader.setRepository(projectRepository);
        repositoryItemReader.setMethodName("findAll");
        final HashMap<String, Sort.Direction> sorts = new HashMap<>();
        sorts.put("id", Sort.Direction.ASC);
        repositoryItemReader.setSort(sorts);
        return repositoryItemReader;
    }



    @Bean
    public FlatFileItemWriter<Project> projectWriter() {

        FlatFileItemWriter<Project> projectWriter = new FlatFileItemWriter<>();

        projectWriter.setResource(outputResource);
        projectWriter.setAppendAllowed(true);

        projectWriter.setLineAggregator(new DelimitedLineAggregator<Project>() {
            {
                setDelimiter(",");
                setFieldExtractor(new BeanWrapperFieldExtractor<Project>() {
                    {
                        setNames(new String[] {"id", "project_name", "allocated_capital","used_capital"});
                    }
                });
            }
        });
        projectWriter.setHeaderCallback(new FlatFileHeaderCallback() {
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

        return  projectWriter;
    }



    @Bean
    public ProjectProcessor processor() {
        return new ProjectProcessor();
    }


    @Bean
    public Job runJob() {
        return jobBuilderFactory.get("importProjectjob")
                .flow(step1()).end().build();

    }


    @Bean
    public Step step1() {
        return stepBuilderFactory.get("csv-step-resource").<Project, Project>chunk(10)
                .reader(reader()).processor(processor()).writer(projectWriter()).build();
    }


}
