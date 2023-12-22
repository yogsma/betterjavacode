import { Module } from '@nestjs/common';
import { AppController } from './app.controller';
import { AppService } from './app.service';
import { FileController } from './controllers/file.controller';
import { PrismaService } from './common/prisma.service';
import { FileUploadService } from './services/fileupload.service';
import { ConfigModule } from '@nestjs/config';
import * as Joi from '@hapi/joi';
import { EventEmitterModule } from '@nestjs/event-emitter';
import { FileUploadedListener } from './listeners/file-uploaded.listener';

@Module({
  imports: [
    ConfigModule.forRoot({
      validationSchema: Joi.object({
        AWS_REGION: Joi.string().required(),
        AWS_ACCESS_KEY_ID: Joi.string().required(),
        AWS_SECRET_ACCESS_KEY: Joi.string().required(),
        AWS_BUCKET_NAME: Joi.string().required(),
      })
    }),
    EventEmitterModule.forRoot()
  ],
  controllers: [AppController, FileController],
  providers: [AppService, PrismaService, FileUploadService, FileUploadedListener],
})
export class AppModule {}
