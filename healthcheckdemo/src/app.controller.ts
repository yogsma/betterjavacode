import { Body, Controller, Get, Post } from '@nestjs/common';
import { AppService } from './app.service';
import { UserService } from './user.service';
import { User as UserModel } from '@prisma/client';

@Controller()
export class AppController {
  constructor(private readonly appService: AppService,
    private readonly userService: UserService) {}


  @Post('user')
  async signUpUser(
    @Body() userData: {first_name: string, last_name? : string, email: string},
  ): Promise<UserModel> {
    return this.userService.createUser(userData);
  }
  @Get()
  getHello(): string {
    return this.appService.getHello();
  }
}