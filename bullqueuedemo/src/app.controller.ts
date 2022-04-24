import { InjectQueue } from '@nestjs/bull';
import { Controller, Get, Post, UploadedFile, UseInterceptors } from '@nestjs/common';
import { FileInterceptor } from '@nestjs/platform-express';
import { diskStorage } from 'multer';
import { Queue } from 'bull';
import { extname } from 'path';
import { queuePool } from './bull-board-queue';

@Controller('/api/bullqueuedemo')
export class AppController {
  constructor(@InjectQueue('file-upload-queue') private fileQueue: Queue) {
    queuePool.add(fileQueue);
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

  @Get('/')
  async getHello(): Promise<string> {
    return "Hello World";
  }
}
