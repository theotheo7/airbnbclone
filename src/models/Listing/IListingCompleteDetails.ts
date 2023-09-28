import {ILocation} from "../Location";

export interface IListingCompleteDetails {
    location: ILocation;
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
    images: string[];
}