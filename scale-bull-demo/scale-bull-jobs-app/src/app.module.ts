import { Module } from '@nestjs/common';
import { AppController } from './app.controller';
import { AppService } from './app.service';
import { BullModule } from '@nestjs/bull';
import { ConfigModule, ConfigService } from '@nestjs/config';
import { BullBoardModule } from '@bull-board/nestjs';
import { ExpressAdapter } from '@bull-board/express';
import { BullAdapter } from '@bull-board/api/bullAdapter';
import { FileUploadModule } from './file-upload/file-upload.module';
import { FileDataModule } from './file-read/file-data.module';


@Module({
  imports: [
    ConfigModule.forRoot({
      isGlobal: true,
    }),
    BullModule.forRootAsync({
      imports: [ConfigModule],
      useFactory: async (configService: ConfigService) => ({
        redis: {
          host: process.env.REDIS_HOST || configService.get('REDIS_HOST'),
          port: Number(process.env.REDIS_PORT || configService.get('REDIS_PORT')),
        },
      }),
      inject: [ConfigService]
    }),
    BullModule.registerQueue({
      name: 'file-upload-queue'
    }),
    BullModule.registerQueue({
      name: 'file-data-queue'
    }),
    BullBoardModule.forRoot({
      route: '/v1/queues',
      adapter: ExpressAdapter // Or FastifyAdapter from `@bull-board/fastify`
    }),
    BullBoardModule.forFeature({
      name: 'file-upload-queue',
      adapter: BullAdapter
    }),
    BullBoardModule.forFeature({
      name: 'file-data-queue',
      adapter: BullAdapter
    }),
    FileUploadModule,
    FileDataModule,
  ],
  controllers: [AppController],
  providers: [AppService],
})
export class AppModule {}
