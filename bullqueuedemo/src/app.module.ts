import { BullModule } from '@nestjs/bull';
import { Module } from '@nestjs/common';
import { ConfigModule, ConfigService } from '@nestjs/config';
import { AppController } from './app.controller';
import { BullBoardController } from './bull-board-controller';
import { FileUploadProcessor } from './file-upload.processor';
import { PrismaService } from './prisma.service';
import { UserService } from './user.service';

@Module({
  imports: [
    BullModule.forRootAsync({
      imports: [ConfigModule],
      useFactory: async (configService: ConfigService) => ({
        redis: {
          host: configService.get('REDIS_HOST'),
          port: Number(configService.get('REDIS_PORT')),
        },
      }),
      inject: [ConfigService]
    }),
    BullModule.registerQueue({
      name: 'file-upload-queue'
    }), 
  ],
  controllers: [AppController, BullBoardController],
  providers: [UserService, PrismaService, FileUploadProcessor,],
})
export class AppModule {}
