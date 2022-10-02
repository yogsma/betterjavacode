import { Body, Controller, Get, HttpStatus, Param, Post, Req, Res, UseGuards } from '@nestjs/common';
import { response } from 'express';
import { CreateUserDto } from './dtos/create-user.dto';
import { LocalAuthGuard } from './local.auth.guard';
import RequestWithUser from './request-user';
import { UsersService } from './users.service';

@Controller('/api/v1/user')
export class UsersController {
  constructor(private readonly usersService: UsersService) {}

  @Post('/signup')
  async create(@Res() response, @Body() createUserDto: CreateUserDto) {    
    const user = await this.usersService.createUser(createUserDto);
    return response.status(HttpStatus.CREATED).json({
      user
    });
  }

  @UseGuards(LocalAuthGuard)
  @Post('/signin')
  async signIn(@Req() request: RequestWithUser) {    
    const user = request.user;    
    return user;

  } 

  @Get('/:id')
  show(@Param('id') id: string) {
    return this.usersService.findById(id);
  }

}
