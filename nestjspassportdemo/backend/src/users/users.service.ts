import { Injectable } from '@nestjs/common';
import { PrismaService } from 'src/common/prisma.service';
import { CreateUserDto } from './dtos/create-user.dto';
import * as bcrypt from 'bcryptjs';
import { UserRepository } from './user.repository';
import { User } from './entities/user.entity';

@Injectable()
export class UsersService {
    constructor(private readonly prismaService: PrismaService, private readonly userRepository: UserRepository) {}
   
    async createUser(user: CreateUserDto) {
        const hashedPassword = await bcrypt.hash(user.password, 12);

        const userToBeCreated = User.createNewUser({            
            firstName: user.firstName,
            lastName: user.lastName,
            email: user.email,
            password: hashedPassword,
        });
        return await this.userRepository.save(userToBeCreated);
    }

   async findById(id: string) {
    return await this.userRepository.findById(id);    
   }

   async getByEmail(email: string) {
    const user = await this.userRepository.findByEmail(email);

    return user;
   }
}
