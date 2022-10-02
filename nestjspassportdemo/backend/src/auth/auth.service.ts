import { HttpException, HttpStatus, Injectable } from '@nestjs/common';
import * as bcrypt from 'bcryptjs';
import { User } from 'src/users/entities/user.entity';
import { UsersService } from 'src/users/users.service';

@Injectable()
export class AuthService {   

    constructor(private readonly userService: UsersService) {}

    async getAuthenticatedUser(email: string, password: string): Promise<User> {
        try {
            const user = await this.userService.getByEmail(email);
            console.log(user);
            await this.validatePassword(password, user.password);            
            return user;
        } catch (e) {
            throw new HttpException('Invalid Credentials', HttpStatus.BAD_REQUEST);
        }
    }

    async validatePassword(password: string, hashedPassword: string) {
        const passwordMatched = await bcrypt.compare(
            password,
            hashedPassword,
        );

        if (!passwordMatched) {
            throw new HttpException('Invalid Credentials', HttpStatus.BAD_REQUEST);
        }
    }
}
