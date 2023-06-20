package org.example.processor;

import org.example.entity.Project;
import org.springframework.batch.item.ItemProcessor;


public class ProjectProcessor implements ItemProcessor<Project, Project> {

    @Override
    public Project process(Project project) throws Exception {

        return project;
    }


}
