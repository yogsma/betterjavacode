import { UserService } from "./user.service";
import { Job } from "bullmq";
import { Processor, WorkerHost } from "@nestjs/bullmq";

const csv = require('csvtojson');

@Processor('file-upload-queue')
export class FileUploadProcessor extends WorkerHost{

    constructor(private readonly userService: UserService){
        super();
    }
    
    async process(job: Job) {
        const file = job.data.file;
        const filePath = file.path;
        const userData = await csv().fromFile(filePath);

        console.log(userData);

        for(const user of userData) {
            const input = {
                email: user.email,
                first_name: user.first_name,
                last_name: user.last_name,
            };
            const userCreated = await this.userService.createUser(input);
            console.log('User created -', userCreated.id );
        }

    }
    
}