import { Job } from "bullmq";
import { basename } from "path";
import * as fs from 'fs';
import * as csvparser from "csv-parser";
import { Processor, WorkerHost } from "@nestjs/bullmq";

@Processor('transform-file-queue')
export class TransformFileProcessor extends WorkerHost{
    
    async process(job: Job): Promise<string> {
        const file  = job.data.file;

        console.log('Validating the file', file);

        return await this.validateAndWriteFile(file, job.id!); 
    }

    async validateAndWriteFile(file: string, jobId: string) {
        console.log('transforming the file');
        const srcName = basename(file);            
        const output = `./output/transformed-${srcName}`;

        const validatedData: string[] = [];
        const errorData: string[] = [];
        const validate = new Promise<{
            errorData: string[],
            validatedData: string[]
        }>(function(resolve, reject){
            fs.createReadStream(file)
            .pipe(csvparser())        
            .on('data', (data) => {                               
                const regExp = /[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$/
                if (data['Name'] === '') {
                    errorData.push(`${data['Name']},${data['Email']},${data['EmployeeId']}`);
                } else if (!regExp.test(data['Email'])) {
                    errorData.push(`${data['Name']},${data['Email']},${data['EmployeeId']}`);
                } else {
                    validatedData.push(`${data['Name']},${data['Email']},${data['EmployeeId']}`);
                }
            })
            .on('end', () => {
                resolve({
                    errorData,
                    validatedData,
                });
            });
        });

        const result: {
            errorData: string[],
            validatedData: string[],
        } = await validate;

        console.log('Any invalid data ', result?.errorData);

        const csvData = validatedData.map((e) => {
            return e.replace(/;/g, ",");
        });
        fs.writeFile(output, csvData.join("\r\n"), (err) => {
            console.log(err || 'done')
        });
        return output;
    }
}