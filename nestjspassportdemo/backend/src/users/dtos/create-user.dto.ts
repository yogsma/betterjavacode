import { IsAlphanumeric, IsNotEmpty, IsString } from "class-validator";


export class CreateUserDto{
    @IsString()
    @IsNotEmpty()
    email: string;

    @IsString()
    @IsNotEmpty()
    firstName: string;

    lastName: string;

    @IsNotEmpty()
    @IsAlphanumeric()
    password: string;
}