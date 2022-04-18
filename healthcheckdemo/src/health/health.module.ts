import { Module } from '@nestjs/common';
import { TerminusModule } from '@nestjs/terminus';
import { PrismaService } from 'src/prisma.service';

import { HealthController } from './health.controller';
import { PrismaOrmHealthIndicator } from './prismaorm.health';

@Module({
  imports: [
    TerminusModule,   
  ],
  controllers: [HealthController],
  providers: [ PrismaOrmHealthIndicator, PrismaService]
})
export class HealthModule {}