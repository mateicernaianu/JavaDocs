package ro.teamnet.zth.appl.controller;

import ro.teamnet.zth.api.annotations.MyBodyParam;
import ro.teamnet.zth.api.annotations.MyController;
import ro.teamnet.zth.api.annotations.MyRequestMethod;
import ro.teamnet.zth.api.annotations.MyRequestParam;
import ro.teamnet.zth.appl.domain.Job;
import ro.teamnet.zth.appl.service.JobService;
import ro.teamnet.zth.appl.service.JobServiceImpl;

import java.util.List;

/**
 * Created by user on 7/18/2016.
 */

@MyController(urlPath = "/jobs")
public class JobController {


    private final JobService jobService = new JobServiceImpl();


    @MyRequestMethod(urlPath = "/all")
    public List<Job> getAllJobs()
    {
        return jobService.findAllJobs();
    }

    @MyRequestMethod(urlPath = "/one")
    public Job getOneJobs(@MyRequestParam(name="id") String id){
        return jobService.findOneJob(id);
    }

    @MyRequestMethod(urlPath = "/delete",methodType = "DELETE")
    public void deleteOneJob(@MyRequestParam(name="id") String id){
        jobService.deleteOneJob(id);
    }

    @MyRequestMethod(urlPath = "/create",methodType = "POST")
    public Job insertJob(@MyBodyParam(name = "job")Job job){
        return jobService.insertJob(job);
    }

    @MyRequestMethod(urlPath = "/update",methodType = "PUT")
    public Job updateJob(@MyBodyParam(name = "job")Job job){
        return jobService.updateJob(job);
    }
}
