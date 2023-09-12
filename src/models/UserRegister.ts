export class UserRegister {
    username: string;
    email: string;
    firstName: string;
    lastName: string;
    password: string;
    phoneNumber: string;
    roles: number[] | undefined;

    constructor(username: string, email: string, firstName: string, lastName: string, password: string, phoneNumber: string, roles: number[] | undefined) {
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.roles = roles;
    }
}