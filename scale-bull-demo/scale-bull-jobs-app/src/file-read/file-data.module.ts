import { BullModule } from "@nestjs/bull";
import { Module } from "@nestjs/common";
import { FileDataProcessor } from "./file-data.processor";
import { ConfigModule, ConfigService } from "@nestjs/config";


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
            name: 'file-data-queue',
            limiter: {
                max: 3,
                duration: 10000
            }
        })
    ],
    providers: [FileDataProcessor]
})
export class FileDataModule {}