export interface IUserLogin {
    username: string;
    password: string;
}

export class UserLogin implements IUserLogin {
    username: string;
    password: string;

    constructor(username: string, password: string) {
        this.username = username;
        this.password = password;
    }
}