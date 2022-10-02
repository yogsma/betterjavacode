import { IsNotEmpty, IsString } from "class-validator";

export class UserSignDto {
    @IsString()
    @IsNotEmpty()
    email: string;

    @IsNotEmpty()    
    password: string;
}