import { Module } from '@nestjs/common';
import { PrismaClient } from '@prisma/client';
import { AppController } from './app.controller';
import { AppService } from './app.service';
import { UserService } from './user.service';
import { HealthModule } from './health/health.module';
import { HttpModule } from '@nestjs/axios';
import { PrismaService } from './prisma.service';

@Module({
  imports: [HealthModule, HttpModule],
  controllers: [AppController],
  providers: [AppService, UserService, PrismaClient, PrismaService,],
})
export class AppModule {}
