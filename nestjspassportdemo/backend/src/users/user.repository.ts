import { Injectable, NotFoundException } from "@nestjs/common";
import { PrismaService } from "src/common/prisma.service";
import { User } from "./entities/user.entity";


@Injectable()
export class UserRepository {

    constructor(
        private readonly prismaService: PrismaService,        
    ) {}

    async save(user: User): Promise<User> {
        const userData = {
            email: user.email,
            first_name: user.firstName,
            last_name: user.lastName,
            password: user.password
        }
        const userCreated = await this.prismaService.user.create({
            data: userData
        });
        
        return {
            id: userCreated.id,
            firstName: userCreated.first_name,
            lastName: userCreated.last_name,
            email: userCreated.email,
            password: '',
        };
    }

    async findByEmail(email: string): Promise<User> {
        const foundUser = await this.prismaService.user.findFirst({
            where: {
                email
            }
        });

        if (!foundUser) {
            throw new NotFoundException('User not found');
        }
        
        return {
            id: foundUser.id,
            firstName: foundUser.first_name,
            lastName: foundUser.last_name,
            email: foundUser.email,
            password: foundUser.password,
        };
    }

    async findById(id: string): Promise<User> {
        const foundUser = await this.prismaService.user.findUnique({
            where: {
                id
            }
        });

        if (!foundUser) {
            throw new NotFoundException('User not found');
        }

        return  {
            id: foundUser.id,
            firstName: foundUser.first_name,
            lastName: foundUser.last_name,
            email: foundUser.email,
            password: foundUser.password,
        };;
    }
}

