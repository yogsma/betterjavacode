import { Process, Processor } from "@nestjs/bull";
import { Job } from "bull";
import { UserService } from "./user.service";
const csv = require('csvtojson');

@Processor('file-upload-queue')
export class FileUploadProcessor {

    constructor(private readonly userService: UserService){}

    @Process('csvfilejob')
    async processFile(job: Job) {
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