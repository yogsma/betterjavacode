import { Injectable } from '@nestjs/common';
import { OnEvent } from '@nestjs/event-emitter';
import { EventStatus } from '@prisma/client';
import { PrismaService } from 'src/common/prisma.service';
import { FileUploadedEvent } from 'src/events/file-uploaded.event';

@Injectable()
export class FileUploadedListener {

  constructor(private prismaService: PrismaService){}

  @OnEvent('user.fileUploaded')
  async handleFileUploadedEvent(event: FileUploadedEvent) {
    
    console.log('File has been uploaded');
    console.log(event);
    await this.prismaService.internalEvent.update(
        {
            where: {
                id: event.id,
            },
            data: {
                eventStatus: EventStatus.PROCESSED,
            }
        }
    );
    console.log('File will get processed');

  }
}