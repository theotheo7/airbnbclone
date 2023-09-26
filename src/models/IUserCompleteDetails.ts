import {Role} from "./Role";

export interface IUserCompleteDetails {
    username: string;
    email: string;
    firstName: string;
    lastName: string;
    phoneNumber: string;
    roles: Role[];
    image: string;
    hostApproved: boolean;
}