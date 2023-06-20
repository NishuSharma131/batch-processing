package org.example.processor;

import org.example.entity.Project;
import org.example.entity.Resource;
import org.springframework.batch.item.ItemProcessor;


public class ResourceProcessor implements ItemProcessor<Resource, Resource> {

    @Override
    public Resource process(Resource resource) throws Exception {

        return resource;
    }


}
