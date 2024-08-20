import { Process, Processor } from "@nestjs/bull";
import { Job } from "bull";


@Processor('file-data-queue')
export class FileDataProcessor{

    
    @Process('process-data')
    async processFile(job: Job) {
        const data = job.data;

        console.log('processing data for a single user');
        console.log(data);

        // To-Do add some processing like inserting this data in DB
    }
    
}