
import { Module } from '@nestjs/common';
import { ConfigModule, ConfigService } from '@nestjs/config';
import { AppController } from './app.controller';
import { BullBoardController } from './bull-board-controller';
import { FileUploadProcessor } from './file-upload.processor';
import { PrismaService } from './prisma.service';
import { UserService } from './user.service';
import { TransformFileProcessor } from './transform-file-processor';
import { SplitFileProcessor } from './split-file.processor';
import { MergeDataProcessor } from './merge-data.processor';
import { BullModule } from '@nestjs/bullmq';

@Module({
  imports: [    
    BullModule.forRootAsync({
      imports: [ConfigModule],
      useFactory: async (configService: ConfigService) => ({
        connection: {
          host: configService.get('REDIS_HOST'),
          port: Number(configService.get('REDIS_PORT')),
        }
      }),
      inject: [ConfigService],
    }),
    BullModule.registerQueue({
      name: 'file-upload-queue'
    },
    {
      name: 'split-file-queue',
    },
    {
      name: 'transform-file-queue',
    },
    {
      name: 'merge-data-queue'
    }),
    BullModule.registerFlowProducer({
      name: 'merge-all-files',
    }),
  ],
  controllers: [AppController, BullBoardController],
  providers: [UserService, PrismaService, FileUploadProcessor, TransformFileProcessor, SplitFileProcessor,MergeDataProcessor],
})
export class AppModule {}
