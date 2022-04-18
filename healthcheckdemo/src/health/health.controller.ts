import { Controller, Get, Inject } from '@nestjs/common';
import { DiskHealthIndicator, HealthCheck, HealthCheckService, HttpHealthIndicator, MemoryHealthIndicator, MicroserviceHealthIndicator } from '@nestjs/terminus';
import { PrismaOrmHealthIndicator } from './prismaorm.health';


@Controller('health')
export class HealthController {
    constructor(
        private health: HealthCheckService,
        private http: HttpHealthIndicator,
        @Inject(PrismaOrmHealthIndicator)
        private db: PrismaOrmHealthIndicator,
        private disk: DiskHealthIndicator,
        private memory: MemoryHealthIndicator,        
    ) {}

  @Get()
  @HealthCheck()
  check() {
    return this.health.check([
      () => this.http.pingCheck('basic check', 'http://localhost:3000'),
      () => this.disk.checkStorage('diskStorage', { thresholdPercent: 0.5, path: 'C:\\'}),
      () => this.db.pingCheck('healthcheckdemo'),
      () => this.memory.checkHeap('memory_heap', 300*1024*1024),
      () => this.memory.checkRSS('memory_rss', 300*1024*1024),
      
      // Mongoose for MongoDB check
      // Redis check
    ]);
  }


}
