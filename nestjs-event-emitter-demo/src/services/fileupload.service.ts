import { Injectable } from "@nestjs/common";
import { ConfigService } from "@nestjs/config";
import { EventEmitter2 } from "@nestjs/event-emitter";
import { EventStatus, FileEntity, Prisma } from "@prisma/client";
import { S3 } from "aws-sdk";
import { PrismaService } from "src/common/prisma.service";
import { FileUploadedEvent } from "src/events/file-uploaded.event";
import { v4 as uuid } from 'uuid';

@Injectable()
export class FileUploadService {
    constructor(private prismaService: PrismaService,
        private readonly configService: ConfigService,
        private eventEmitter: EventEmitter2){}
    
    async uploadFile(dataBuffer: Buffer, fileName: string): Promise<FileEntity> {
        const s3 = new S3();
        const uploadResult = await s3.upload({
            Bucket: this.configService.get('AWS_BUCKET_NAME'),
            Body: dataBuffer,
            Key: `${uuid()}-${fileName}`,
        }).promise();

        const fileStorageInDB = ({
            fileName: fileName,
            fileUrl: uploadResult.Location,
            key: uploadResult.Key,
        });

        const filestored = await this.prismaService.fileEntity.create({
            data: fileStorageInDB
        });

        const fileUploadedEvent = new FileUploadedEvent();
        fileUploadedEvent.fileEntityId = filestored.id;
        fileUploadedEvent.fileName = filestored.fileName;
        fileUploadedEvent.fileUrl = filestored.fileUrl;

        const internalEventData = ({
            eventName: 'user.fileUploaded',
            eventStatus: EventStatus.PENDING,
            eventPayload: JSON.stringify(fileUploadedEvent),
        });

        const internalEventCreated = await this.prismaService.internalEvent.create({
            data: internalEventData
        });
        fileUploadedEvent.id = internalEventCreated.id;

        if (internalEventCreated) {
            console.log('Publishing an internal event');
            const emitted = this.eventEmitter.emit(
                'user.fileUploaded',
                fileUploadedEvent
            );
            if (emitted) {
                console.log('Event emitted');
            }
        }

        return filestored;
    }
}