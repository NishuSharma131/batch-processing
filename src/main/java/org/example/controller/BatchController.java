package org.example.controller;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/batch")// Root path
public class BatchController {
@Autowired
    JobLauncher jobLauncher;
    @Autowired
    Job runJob;

    @PostMapping("/importuserjob")
    public ResponseEntity<String> importDbToCsvJob() {

       System.out.println("BatchController | importDvToCsvJob is called");

        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("startAt", System.currentTimeMillis()).toJobParameters();
        try {
            jobLauncher.run(runJob, jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobRestartException |
                 JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
            System.out.println("BatchController | importDvToCsvJob | error : " + e.getMessage());
            e.printStackTrace();
        }

        return new ResponseEntity<>("Batch Process Completed!!", HttpStatus.OK);
    }
}
