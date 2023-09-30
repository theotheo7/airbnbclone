import {ILocation} from "../Location";
import {IUserBasicDetails} from "../User/IUserBasicDetails";

export interface IListingCompleteDetails {
    id: number;
    location: ILocation;
    host: IUserBasicDetails;
    name: string;
    startDate: string;
    endDate: string;
    maxPeople: number;
    price: number;
    extraPeople: number;
    type: string;
    beds: number;
    baths: number;
    meters: number;
    living: boolean;
    party: boolean;
    pets: boolean;
    summary: string;
    wifi: boolean;
    ac: boolean;
    heat: boolean;
    kitchen: boolean;
    tv: boolean;
    parking: boolean;
    elevator: boolean;
    images: string[];
}