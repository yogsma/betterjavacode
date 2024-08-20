import { InjectQueue, Process, Processor } from "@nestjs/bull";
import { Job, Queue } from "bull";

const csv = require('csvtojson');

@Processor('file-upload-queue')
export class FileUploadProcessor{

    constructor(@InjectQueue('file-data-queue') private fileDataQueue: Queue) {}
    
    @Process('process-file')
    async processFile(job: Job) {
        const file = job.data.file;
        const filePath = file.path;
        const userData = await csv().fromFile(filePath);

        await this.fileDataQueue.addBulk(userData.map(user => ({
            name: 'process-data',
            data: user
        })));

        console.log('file uploaded successfully');
    }
    
}