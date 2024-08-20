import { Controller, Get, Post, UploadedFile, UseInterceptors } from '@nestjs/common';
import { AppService } from './app.service';
import { InjectQueue } from '@nestjs/bull';
import { Queue } from 'bull';
import { FileInterceptor } from '@nestjs/platform-express/multer';
import { diskStorage } from 'multer';
import { extname } from 'path';

@Controller('/api/bullscaledemo')
export class AppController {
  constructor(@InjectQueue('file-upload-queue') private fileQueue: Queue, private readonly appService: AppService) {}

  @Get()
  getHello(): string {
    return this.appService.getHello();
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
  async uploadLargeCsvFile(@UploadedFile() file): Promise<void> {
    const job = await this.fileQueue.add('process-file', {file: file});
    console.log(`created job ${ job.id}`);
    await this.fileQueue.close();
  }

}
