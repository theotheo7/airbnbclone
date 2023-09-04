export class UserRegister {
    username: string;
    email: string;
    firstName: string;
    lastName: string;
    password: string;
    phoneNumber: string;

    constructor(username: string, email: string, firstName: string, lastName: string, password: string, phoneNumber: string) {
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }
}