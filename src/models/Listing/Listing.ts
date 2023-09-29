import { ILocation } from "../Location";

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
    wifi: boolean;
    ac: boolean;
    heat: boolean;
    kitchen: boolean;
    tv: boolean;
    parking: boolean;
    elevator: boolean;
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
    wifi: boolean;
    ac: boolean;
    heat: boolean;
    kitchen: boolean;
    tv: boolean;
    parking: boolean;
    elevator: boolean;

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
        summary: string,
        wifi: boolean,
        ac: boolean,
        heat: boolean,
        kitchen: boolean,
        tv: boolean,
        parking: boolean,
        elevator: boolean
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
        this.wifi = wifi;
        this.ac = ac;
        this.heat = heat;
        this.kitchen = kitchen;
        this.tv = tv;
        this.parking = parking;
        this.elevator = elevator;
    }
}