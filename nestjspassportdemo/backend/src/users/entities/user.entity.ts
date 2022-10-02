import { uuid } from "uuidv4";
import { Entity } from "./entity";

export type CreateUserParams = {
    email: string,
    firstName: string,
    lastName: string,
    password: string,
};

export class User implements Entity {
    static createNewUser(userParams: CreateUserParams): User {
        return new User(
            uuid(),
            userParams.email,
            userParams.firstName,
            userParams.lastName,
            userParams.password
        );
    }

    constructor(
        public readonly id: string,
        public readonly email: string,
        public readonly firstName: string,
        public readonly lastName: string,
        public readonly password: string
    ) {}
    

}