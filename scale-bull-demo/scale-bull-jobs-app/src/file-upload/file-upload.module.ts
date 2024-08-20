import { BullModule } from "@nestjs/bull";
import { Module } from "@nestjs/common";
import { FileUploadProcessor } from "./file-upload.processor";
import { ConfigModule, ConfigService } from "@nestjs/config";
import { FileDataModule } from "src/file-read/file-data.module";


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
            name: 'file-upload-queue',
            limiter: {
                max: 1,
                duration: 10000
            }
        }),
        BullModule.registerQueue({
            name: 'file-data-queue',
        }),
    ],
    providers: [FileUploadProcessor]
})
export class FileUploadModule {}