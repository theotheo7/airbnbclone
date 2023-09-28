import { ILocation } from "./Location";

export interface IListing {
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
}

export class Listing implements IListing {
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

    constructor(
        location: ILocation,
        name: string,
        startDate: string,
        endDate: string,
        maxPeople: number,
        price: number,
        extraPeople: number,
        type: string,
        beds: number,
        baths: number,
        meters: number,
        living: boolean,
        party: boolean,
        pets: boolean,
        summary: string
    ) {
        this.location = location;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.maxPeople = maxPeople;
        this.price = price;
        this.extraPeople = extraPeople;
        this.type = type;
        this.beds = beds;
        this.baths = baths;
        this.meters = meters;
        this.living = living;
        this.party = party;
        this.pets = pets;
        this.summary = summary;
    }
}