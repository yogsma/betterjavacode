import { Job, Queue } from "bullmq";
import * as fs from 'fs';
import { Processor, WorkerHost } from "@nestjs/bullmq";


@Processor('merge-data-queue')
export class MergeDataProcessor extends WorkerHost {    
        
    async process(job: Job): Promise<void> {        
        const transformedChunks = await job.getChildrenValues();
        const files = Object.values(transformedChunks).sort();

        console.log('Start merging data into a single file', files);

        await this.mergeFiles(
            job.id, 
            files,
            `./output/merged-${job.id}.csv`
        );
    }

    async mergeFiles(
        jobId: string,
        files: string[],
        finalOutputFileName: string,
    ): Promise<void> {

        const data = [];
        files.forEach( (file) => {
            const fileData = fs.readFileSync(file);
            data.push(fileData);
        });
        fs.writeFile(finalOutputFileName, data.join("\r\n"), (err) => {
            console.log(err || 'done')
        });
    }
}