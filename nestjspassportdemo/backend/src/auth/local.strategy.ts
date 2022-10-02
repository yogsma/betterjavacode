import { Injectable, } from "@nestjs/common";
import { PassportStrategy } from '@nestjs/passport';
import { Strategy } from 'passport-local';
import { User } from "src/users/entities/user.entity";
import { AuthService } from "./auth.service";

@Injectable()
export class LocalStrategy extends PassportStrategy(Strategy) {
    constructor(private readonly authService: AuthService) {
        super({
            usernameField: 'email'
        });
    }

    async validate(email: string, password: string): Promise<User> {
        return this.authService.getAuthenticatedUser(email, password);
    }
}