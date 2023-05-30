import { FlowProducer, Job } from "bullmq";
import * as fs from "fs";
import * as csvparser from "csv-parser";
import { Processor, WorkerHost } from "@nestjs/bullmq";


@Processor('split-file-queue')
export class SplitFileProcessor extends WorkerHost{
    
    async process(job: Job) {        
        const file  = job.data.file;        
        const filePath = file.path;
        const chunksOfInputFile = await this.splitInChunks(filePath);
        console.log(chunksOfInputFile);
        await this.addChunksToQueue(chunksOfInputFile);
    }

    async addChunksToQueue(files: string[]) {
        const flowProducer = new FlowProducer();

        try {
            return await flowProducer.add({
                name: 'merge-all-files',
                queueName: 'merge-data-queue',
                children: files.map((file) => ({
                    name: 'transform-file',
                    queueName: 'transform-file-queue',
                    data: { file: file},
                })),            
            });
        } catch (err) {
            console.log (`Error adding flow ${err}`);
        }

    }

    async splitInChunks(file: string): Promise<string[]> {
        let chunkIndex = 1;
        let rowCount = 0;
        const outputFilePath = (index) => `./csv/chunk${index}.csv`;

        let currentChunkRows = [];
        let splitFiles = [];

        const splitData = new Promise<string[]>(function(resolve, reject) {
            fs.createReadStream(file)
            .pipe(csvparser())
            .on('data', (row) => {
                currentChunkRows.push(row);

                if (currentChunkRows.length === 500) {
                    const fileName = outputFilePath(chunkIndex);
                    splitFiles.push(fileName);
                    const writeStream = fs.createWriteStream(fileName);
                    writeStream.write('Name,Email,EmployeeId\n');
                    currentChunkRows.forEach((chunkRow) => writeStream.write(`${chunkRow['Name']}, ${chunkRow['Email']}, ${chunkRow['EmployeeId']}\n`));
                    writeStream.end();

                    chunkIndex++;
                    currentChunkRows = [];
                }
                rowCount++;
            })
            .on('end', () => {
                if (currentChunkRows.length > 0) {
                    const fileName = outputFilePath(chunkIndex);
                    splitFiles.push(fileName);
                    const writeStream = fs.createWriteStream(fileName);
                    writeStream.write('Name,Email,EmployeeId\n');
                    currentChunkRows.forEach((chunkRow) => writeStream.write(`${chunkRow['Name']}, ${chunkRow['Email']}, ${chunkRow['EmployeeId']}\n`));
                    writeStream.end();
                }
                resolve(splitFiles);
            });
        });
        
        await splitData;
        return splitFiles;
    }
}