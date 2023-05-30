import { InjectQueue } from '@nestjs/bull';
import { Controller, Get, Post, UploadedFile, UseInterceptors } from '@nestjs/common';
import { FileInterceptor } from '@nestjs/platform-express';
import { diskStorage } from 'multer';
import { extname } from 'path';
import { queuePool } from './bull-board-queue';
import { Queue } from 'bullmq';

@Controller('/api/bullqueuedemo')
export class AppController {
  constructor(
    @InjectQueue('file-upload-queue') private fileQueue: Queue, 
    @InjectQueue('split-file-queue') private splitFileQueue: Queue,
    @InjectQueue('merge-data-queue') private mergeDataQueue: Queue,
    @InjectQueue('transform-file-queue') private transformFileQueue: Queue,) {
    queuePool.add(fileQueue);
    queuePool.add(splitFileQueue);
    queuePool.add(mergeDataQueue);
    queuePool.add(transformFileQueue);
  }

  @Post('/uploadFile')
  @UseInterceptors(FileInterceptor("csv", {
    storage: diskStorage({
      destination: './csv',
      fileName: (req, file, cb) => {
        const randomName = Array(32).fill(null).map(() => (Math.round(Math.random() * cb(null, `${randomName}${extname(file.originalname)}`))))
      }
    })
  }))
  async uploadCsvFile(@UploadedFile() file): Promise<void> {
    const job = await this.fileQueue.add('csvfilejob', {file: file});
    console.log(`created job ${ job.id}`);
  }

  @Post('/uploadALargeFile')
  @UseInterceptors(FileInterceptor("csv", {
    storage: diskStorage({
      destination: './csv',
      fileName: (req, file, cb) => {
        const randomName = Array(32).fill(null).map(() => (Math.round(Math.random() * cb(null, `${randomName}${extname(file.originalname)}`))))
      }
    })
  }))
  async uploadLargeCsvFile(@UploadedFile() file): Promise<void> {
    const job = await this.splitFileQueue.add('split', {file: file});
    console.log(`created job ${ job.id}`);
    await this.splitFileQueue.close();
  }

  @Get('/')
  async getHello(): Promise<string> {
    return "Hello World";
  }
}
