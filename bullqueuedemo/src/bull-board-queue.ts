import { BaseAdapter } from '@bull-board/api/dist/src/queueAdapters/base';
import { Injectable } from '@nestjs/common';
import { Queue } from 'bullmq';
import { BullMQAdapter } from '@bull-board/api/bullMQAdapter';


@Injectable()
export class BullBoardQueue {}

export const queuePool: Set<Queue> = new Set<Queue>();

export const getBullBoardQueues = (): BaseAdapter[] => {
    const bullBoardQueues = [...queuePool].reduce((acc: BaseAdapter[], val) => {
        acc.push(new BullMQAdapter(val))
        return acc
    }, []);

    return bullBoardQueues
}